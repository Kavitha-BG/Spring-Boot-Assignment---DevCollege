package com.devcollege.services.implementation;

import com.devcollege.entities.Course;
import com.devcollege.entities.Enrollment;
import com.devcollege.entities.Student;
import com.devcollege.exceptions.DataNotFoundException;
import com.devcollege.exceptions.NoDataFoundException;
import com.devcollege.exceptions.NotFoundException;
import com.devcollege.payloads.EnrollmentDto;
import com.devcollege.repositories.CourseRepository;
import com.devcollege.repositories.EnrollmentRepository;
import com.devcollege.repositories.StudentRepository;
import com.devcollege.services.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class EnrollmentServiceImplementation implements EnrollmentService {
	@Autowired
	private EnrollmentRepository enrollmentRepository;
	@Autowired
	private CourseRepository courseRepository;
	@Autowired
	private StudentRepository studentRepository;

	@Override
	public String addEnrollmentForCourse(Enrollment enrollment) throws NotFoundException {
		Student enrolledStudent = studentRepository.findById(enrollment.getStudentId()).orElseThrow(()
				-> new NotFoundException("studentId","",enrollment.getStudentId()));

		Course enrolledCourse = courseRepository.findById(enrollment.getCourseId()).orElseThrow(()
				-> new NotFoundException("courseId","",enrollment.getCourseId()));

		if (enrolledCourse.getNoOfSlot() >= 1) {
			enrolledCourse.setNoOfSlot((enrolledCourse.getNoOfSlot() - 1));
		}else {
			return "Course is not available for enrollment, It's full..!!";
		}
		if(enrolledStudent.getWalletAmount()>=enrolledCourse.getCourseFee()) {
			enrolledStudent.setWalletAmount(enrolledStudent.getWalletAmount() - enrolledCourse.getCourseFee());
			enrollment.setCourseStatus("Allocated");

			Calendar calender = Calendar.getInstance();
			calender.setTime(enrollment.getCourseStartDatetime());
			calender.add(Calendar.HOUR_OF_DAY, enrolledCourse.getCourseDuration());
//			Date date = calender.getTime();
//			enrollment.setCourseEndDatetime(date);
			enrollment.setCourseEndDatetime(calender.getTime());

			Enrollment getId = enrollmentRepository.save(enrollment);
			courseRepository.save(enrolledCourse);
			studentRepository.save(enrolledStudent);
			enrollmentRepository.save(enrollment);
		}else {
			float remainingAmount = (enrolledCourse.getCourseFee() - enrolledStudent.getWalletAmount());
			return "Insufficient balance in wallet, Student need " + remainingAmount + " for enrollment";
		}

		List<Enrollment> allEnrollments = enrollmentRepository.getAllEnrollments(enrollment.getStudentId());
		ArrayList<Date> endDate = new ArrayList<Date>();
		ArrayList<Date> startDate = new ArrayList<Date>();

		for (Enrollment enrol: allEnrollments) {
			endDate.add(enrol.getCourseEndDatetime());
			startDate.add(enrol.getCourseStartDatetime());
			System.out.println(startDate);
			System.out.println(endDate);
		}

		for (int i=0;i<endDate.size() && i<startDate.size(); i++){
			if(enrollment.getCourseStartDatetime().before(endDate.get(i))&&
					enrollment.getCourseStartDatetime().after(endDate.get(i))){
				return "You are not able to enroll to this course, because you have already enrolled the course..!!";
			}
		}
		enrollmentRepository.save(enrollment);
		return "Successfully Enrolled for " + enrolledStudent.getStudentName() + " in course " + enrolledCourse.getCourseName()
				+ " for enrollment id : " + enrollment.getEnrolId();
	}

	@Override
	public EnrollmentDto getEnrollmentById(String enrolId) throws NotFoundException {
		Enrollment enrollment = enrollmentRepository.findById(enrolId).orElseThrow(()
				-> new NotFoundException("studentId","",enrolId));

		Student student = studentRepository.findById(enrollment.getStudentId()).orElseThrow();

		Course course = courseRepository.findById(enrollment.getCourseId()).orElseThrow();

		EnrollmentDto enrolledList = new EnrollmentDto();
		enrolledList.setEnrolId(enrollment.getEnrolId());
		enrolledList.setCourseId(enrollment.getCourseId());
		enrolledList.setCourseName(course.getCourseName());
		enrolledList.setCourseStatus(enrollment.getCourseStatus());
		enrolledList.setStudentId(enrollment.getStudentId());
		enrolledList.setStudentName(student.getStudentName());
		enrolledList.setCourseStartDatetime(enrollment.getCourseStartDatetime());
		enrolledList.setCourseEndDatetime(enrollment.getCourseEndDatetime());
		enrolledList.setCourseLink("http://localhost:8080/course/get/" + course.getCourseId());
		enrolledList.setStudentLink("http://localhost:8080/student/get/" + student.getStudentId());

		return enrolledList;
	}

	@Override
	public List<EnrollmentDto> getAllEnrollments() throws NoDataFoundException {
		List<Enrollment> enrollmentList = enrollmentRepository.findAll();
		List<EnrollmentDto> enrollmentDtos = new ArrayList<>();
		for (Enrollment enrol: enrollmentList) {
			EnrollmentDto enrollmentDto= getEnrollmentById(enrol.getEnrolId());
			enrollmentDtos.add(enrollmentDto);
		}
		return enrollmentDtos;
	}

	@Override
	public List<EnrollmentDto> getEnrollmentByStudentId(String studentId) throws NoSuchElementException {
		Student checkStudent = studentRepository.findById(studentId).orElseThrow(()
				-> new NotFoundException("StudentId", "", studentId));

		List<Enrollment> enrollments = enrollmentRepository.getAllEnrollments(studentId);

		List<EnrollmentDto> enrolledList = new ArrayList<>();

		if(enrollments.contains(studentId)) {

			for (Enrollment enrollment : enrollments) {
				EnrollmentDto enrollmentDto = new EnrollmentDto();
				enrollmentDto.setEnrolId(enrollment.getEnrolId());
				enrollmentDto.setCourseId(enrollment.getCourseId());
				Course course = courseRepository.findById(enrollment.getCourseId()).get();
				enrollmentDto.setCourseName(course.getCourseName());
				enrollmentDto.setCourseStatus(enrollment.getCourseStatus());
				enrollmentDto.setStudentId(enrollment.getStudentId());
				enrollmentDto.setStudentName(checkStudent.getStudentName());
				enrollmentDto.setCourseStartDatetime(enrollment.getCourseStartDatetime());
				enrollmentDto.setCourseEndDatetime(enrollment.getCourseEndDatetime());
				enrollmentDto.setCourseLink("http://localhost:8080/course/get/" + course.getCourseId());
				enrollmentDto.setStudentLink("http://localhost:8080/student/get/" + checkStudent.getStudentId());
				enrolledList.add(enrollmentDto);
			}
			return enrolledList;
		} else
			throw new DataNotFoundException("studentId","",checkStudent.getStudentName());

	}

	@Override
	public Map<String,String> changeStatus(Enrollment enrollment,String enrolId) throws NotFoundException {
		Enrollment enrollmentStatus = enrollmentRepository.findById(enrolId).orElseThrow(
				() -> new NotFoundException("enrolId", "", enrolId));

		Student student = studentRepository.findById(enrollmentStatus.getStudentId()).orElseThrow();

		Course course = courseRepository.findById(enrollmentStatus.getCourseId()).orElseThrow();

		if (enrollmentStatus.getCourseStatus().equalsIgnoreCase("Cancelled")) {
			if(enrollment.getCourseStatus().equalsIgnoreCase("InProgress")){
				Map<String,String> detailedMessage = new HashMap<>();
				detailedMessage.put("Failed to Change Status for enrolment Id " , enrolId);
				return detailedMessage;
			} else if (enrollment.getCourseStatus().equalsIgnoreCase("Completed")) {
				Map<String,String> detailedMessage = new HashMap<>();
				detailedMessage.put( "Failed to Change Status for enrolment Id " , enrolId);
				return detailedMessage;
			}else if(enrollment.getCourseStatus().equalsIgnoreCase("Cancelled")){
				Map<String,String> detailedMessage = new HashMap<>();
				detailedMessage.put("Failed to Change Status for enrolment Id " , enrolId);
				return detailedMessage;
			}
		}

		if (enrollmentStatus.getCourseStatus().equalsIgnoreCase("Allocated")) {
			enrollmentStatus.setCourseStatus(enrollment.getCourseStatus());
			if(enrollmentStatus.getCourseStatus().equalsIgnoreCase("Cancelled")) {

				Date courseStartDateTime = enrollmentStatus.getCourseStartDatetime();
				int diffInDays = (int)( (courseStartDateTime.getTime() - System.currentTimeMillis())
						/ (1000 * 60 * 60 * 24) );
				System.out.println(diffInDays);
				System.out.println(System.currentTimeMillis());

				if (diffInDays >= 2) {
					student.setWalletAmount(student.getWalletAmount() + course.getCourseFee());
					course.setNoOfSlot(course.getNoOfSlot()+1);
				} else if (diffInDays >= 1) {
					Float oneDayRefund;
					oneDayRefund = ((course.getCourseFee() * 70) / 100);
					student.setWalletAmount(student.getWalletAmount()  + oneDayRefund);
					course.setNoOfSlot(course.getNoOfSlot()+1);
				} else if (diffInDays <= 1) {
					Float hoursRefund;
					hoursRefund = (course.getCourseFee() / 2);
					student.setWalletAmount(student.getWalletAmount() + hoursRefund);
					course.setNoOfSlot(course.getNoOfSlot() + 1);
				}
			}
		}
		enrollmentStatus.setCourseStatus(enrollment.getCourseStatus());
		enrollmentRepository.save(enrollmentStatus);
		studentRepository.save(student);
		courseRepository.save(course);
		Map<String,String> detailedMessage = new HashMap<>();
		detailedMessage.put("Successfully changed the status for " , enrolId);
		return detailedMessage;
	}

	@Override
	public String checkAvailability(String courseId) throws NotFoundException {

		Course checkCourse = courseRepository.findById(courseId).orElseThrow(()
				-> new NotFoundException("courseId", "", courseId));

		if(checkCourse.getNoOfSlot()<=0){
			return courseId +" " + checkCourse.getCourseName() + " not available for enrollment.";
		}else {
			return courseId +" " + checkCourse.getCourseName() + " available for enrollment.";
		}
	}

	@Override
	public List<Course> courseSuggestion(String studentId) throws NotFoundException {
		Student checkStudent = studentRepository.findById(studentId).orElseThrow(()
				-> new NotFoundException("studentId","",studentId));

		List<Course> courseList = courseRepository.findAll();
		List<Course> selectedCourse = new ArrayList<>();

		for(Course course : courseList){
			if(checkStudent.getHighestQualification().equalsIgnoreCase(course.getCourseTag())) {
				selectedCourse.add(course);
			}
		}
		return selectedCourse;
	}
}
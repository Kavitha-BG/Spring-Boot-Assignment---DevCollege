package com.devcollege.services.implementation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.devcollege.entities.Course;
import com.devcollege.entities.Enrollment;
import com.devcollege.entities.StudentWallet;
import com.devcollege.payloads.EnrollmentDto;
import com.devcollege.repositories.CourseRepository;
import com.devcollege.repositories.EnrollmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.devcollege.entities.Student;
import com.devcollege.repositories.StudentRepository;
import com.devcollege.services.StudentService;
import com.devcollege.exceptions.*;

@Service
public class StudentServiceImplementation implements StudentService {
	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private EnrollmentRepository enrollmentRepository;

	@Autowired
	private CourseRepository courseRepository;

	@Override
	public Student addStudent(Student student) {
		if (student.getStudentName().isEmpty() || student.getStudentContactNo().length() == 0 ||
				student.getHighestQualification().isEmpty() || student.getWalletAmount().isNaN()) {
			throw new NotFoundException("studentId", "", student.getStudentId());
		} else {
			Student savedStudent = studentRepository.save(student);
			return savedStudent;
		}
	}

	@Override
	public Student updateStudentById(Student student, String studentId) {
		Student updateStudent = studentRepository.findById(studentId).orElseThrow(()
				-> new NotFoundException("studentId", "", studentId));

		updateStudent.setStudentName(student.getStudentName());
		updateStudent.setHighestQualification(student.getHighestQualification());
		updateStudent.setStudentContactNo(student.getStudentContactNo());

		Student savedStudent = studentRepository.save(updateStudent);
		return savedStudent;
	}

	@Override
	public Map<String, String> deleteStudent(String studentId) throws NotFoundException {
		Student student = studentRepository.findById(studentId).orElseThrow(()
				-> new NotFoundException("studentId", "", studentId));
		if (studentId == null || studentId.equals("")) {
			Map<String, String> message = new HashMap<String, String>();
			message.put("Failed to deleted student details :", studentId);
			return message;
		}

		List<Enrollment> enrollment = enrollmentRepository.getAllEnrollments(studentId);
		List<Enrollment> list = new ArrayList<>();

		for (Enrollment e : enrollment) {

			if (studentRepository.getCourseStatusByStudentId(studentId).equalsIgnoreCase("Allocated") ||
					studentRepository.getCourseStatusByStudentId(studentId).equalsIgnoreCase("InProgress")) {
				e.setCourseStatus("Cancelled");
				enrollmentRepository.deleteById(e.getEnrolId());
				studentRepository.deleteById(studentId);
				Map<String, String> msg = new HashMap<String, String>();
				msg.put(studentId, "Successfully deleted Student details And amount 50% will be refunded in original payment method within 24 hours.");
				return msg;
			} else if (studentRepository.getCourseStatusByStudentId(studentId).equalsIgnoreCase("Completed") ||
					studentRepository.getCourseStatusByStudentId(studentId).equalsIgnoreCase("Cancelled")) {
				enrollmentRepository.deleteById(e.getEnrolId());
				studentRepository.deleteById(studentId);
				Map<String, String> message = new HashMap<String, String>();
				message.put("Successfully deleted student details :", studentId);
				return message;
			}
//			enrollmentRepository.deleteById(e.getEnrolId());
//			studentRepository.deleteById(student.getStudentId());
		}
		studentRepository.deleteById(studentId);
//		studentRepository.save(student);
		Map<String, String> message = new HashMap<String, String>();
		message.put("Successfully deleted student details :", studentId);
		return message;
	}

	@Override
	public Student getStudentById(String studentId) throws NotFoundException {
		Student student = studentRepository.findById(studentId).orElseThrow(()
				-> new NotFoundException("studentId","", studentId));
		return student;
	}

	@Override
	public List<Student> getAllStudents() throws NoDataFoundException {
		List<Student> studentList = studentRepository.findAll();

		if (!studentList.isEmpty()) {
			return studentRepository.findAll();
		} else {
			throw new NoDataFoundException("errorMessage");
		}
	}

	@Override
	public Float addWalletAmount(String studentId, StudentWallet studentWallet ) {
		Student student = studentRepository.findById(studentId).orElseThrow(()
				-> new NotFoundException("studentId","", studentId));


		if(studentWallet.getAmount() > 0) {
			Float finalAmount = student.getWalletAmount() + studentWallet.getAmount();
			student.setWalletAmount(finalAmount);
			studentRepository.save(student);

			return finalAmount;
		} else {
			throw new RuntimeException("Amount should be positive numeric value");
		}
	}

	@Override
	public Map<String,String> getWalletDetail(String studentId) {

		Student retrieveStudentWallet = studentRepository.findById(studentId).orElseThrow(()
				-> new NotFoundException("studentId","", studentId));

		Map<String,String> details = new HashMap<>();
		details.put("StudentId", studentId);
		details.put("WalletAmount", "" +retrieveStudentWallet.getWalletAmount());

		return details;
	}

}


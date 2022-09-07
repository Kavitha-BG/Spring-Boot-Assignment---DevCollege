package com.devcollege.services.implementation;

import com.devcollege.entities.Course;
import com.devcollege.entities.Enrollment;
import com.devcollege.exceptions.*;
import com.devcollege.repositories.CourseRepository;
import com.devcollege.repositories.EnrollmentRepository;
import com.devcollege.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CourseServiceImplementation implements CourseService {
	@Autowired
	private CourseRepository courseRepository;

	@Autowired
	private EnrollmentRepository enrollmentRepository;

	@Override
	public Course addCourse(Course course) {
		if (course.getCourseName().isEmpty() || course.getCourseDescription().isEmpty() || course.getCourseFee() == 0
				|| course.getCourseDuration() == 0 || course.getNoOfSlot() == 0 || course.getCourseTag().isEmpty()) {
			throw new NoDataFoundException("errorMessage");
		} else {
			Course savedCourse = courseRepository.save(course);
			return savedCourse;
		}
	}

	@Override
	public String updateCourseById(Course course, String courseId) throws NotFoundException {
		Course updateCourse = courseRepository.findById(courseId).orElse(null);
		if (updateCourse != null) {

			updateCourse.setCourseName(course.getCourseName());
			updateCourse.setCourseDescription(course.getCourseDescription());
			updateCourse.setCourseFee(course.getCourseFee());
			updateCourse.setCourseDuration(course.getCourseDuration());
			updateCourse.setNoOfSlot(course.getNoOfSlot());
			updateCourse.setCourseTag(course.getCourseTag());

			courseRepository.save(updateCourse);
			return "Successfully updated course detail for course: " + courseId;
		} else {
			throw new NotFoundException("courseId","", courseId);
		}
	}

	@Override
	public Map<String,String> deleteCourse(String courseId) throws NotFoundException {
		Course course = courseRepository.findById(courseId).orElseThrow(()
				-> new NotFoundException("courseId", "" ,courseId));

		if (courseId == null || courseId.equals("")) {
			Map<String, String> message = new HashMap<String, String>();
			message.put("Failed to deleted student details :", courseId);
			return message;
		}

		List<Enrollment> enrollment = enrollmentRepository.getAllEnrollmentsCourse(courseId);
		List<Enrollment> list = new ArrayList<>();

		for (Enrollment e : enrollment) {

			if (courseRepository.getStatusByStudentId(courseId).equalsIgnoreCase("Allocated") ||
					courseRepository.getStatusByStudentId(courseId).equalsIgnoreCase("InProgress")) {
				Map<String, String> msg = new HashMap<String, String>();
				msg.put("Failed to delete course details", courseId);
				msg.put("It is allocated to some student ", e.getStudentId());
				return msg;
			}else if (courseRepository.getStatusByStudentId(courseId).equalsIgnoreCase("Completed") ||
					courseRepository.getStatusByStudentId(courseId).equalsIgnoreCase("Cancelled")) {
				enrollmentRepository.deleteById(e.getEnrolId());
				courseRepository.deleteById(courseId);
				Map<String, String> message = new HashMap<String, String>();
				message.put("Successfully deleted course details :", courseId);
				return message;
			}
		}
		courseRepository.deleteById(courseId);
		Map<String,String> message = new HashMap<String,String>();
		message.put("Successfully deleted course details :",courseId);
		return message;
	}

	@Override
	public Course getCourseById(String courseId) throws NotFoundException {
		Course course = courseRepository.findById(courseId).orElseThrow(()
				-> new NotFoundException("courseId","", courseId));
		return course;
	}

	@Override
	public List<Course> getAllCourses() throws NoDataFoundException {
		List<Course> courseList = courseRepository.findAll();
		if (!courseList.isEmpty()) {
			return courseRepository.findAll();
		} else {
			throw new NoDataFoundException("errorMessage");
		}
	}

}

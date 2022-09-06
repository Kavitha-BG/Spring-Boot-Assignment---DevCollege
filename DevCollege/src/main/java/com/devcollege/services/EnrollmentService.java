package com.devcollege.services;

import com.devcollege.entities.Course;
import com.devcollege.entities.Enrollment;
import com.devcollege.exceptions.NoDataFoundException;
import com.devcollege.exceptions.NotFoundException;
import com.devcollege.payloads.EnrollmentDto;
import java.util.List;
import java.util.Map;


public interface EnrollmentService {
	
	public String addEnrollmentForCourse(Enrollment enrollment) throws NotFoundException;
	
	public EnrollmentDto getEnrollmentById(String enrolId) throws NotFoundException;
	
	public List<EnrollmentDto> getAllEnrollments() throws NoDataFoundException;

	public  List<EnrollmentDto> getEnrollmentByStudentId(String studentId) throws NotFoundException;

	public Map<String,String> changeStatus(Enrollment enrollment, String enrolId) throws NotFoundException;

	public String checkAvailability(String courseId) throws NotFoundException;

	List<Course> courseSuggestion(String studentId) throws NotFoundException;

}

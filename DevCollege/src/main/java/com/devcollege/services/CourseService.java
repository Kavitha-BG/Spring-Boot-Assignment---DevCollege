package com.devcollege.services;

import com.devcollege.entities.Course;
import com.devcollege.exceptions.NoDataFoundException;
import com.devcollege.exceptions.NotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


public interface CourseService {
	
	public Course addCourse(Course course);

	public String updateCourseById(Course course, String courseId) throws NotFoundException;
	@Transactional
	public Map<String,String> deleteCourse(String courseId) throws NotFoundException;
	
	public Course getCourseById(String courseId) throws NotFoundException;
	
	public List<Course> getAllCourses() throws NoDataFoundException;

}

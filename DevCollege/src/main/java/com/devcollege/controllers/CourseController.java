package com.devcollege.controllers;

import com.devcollege.entities.Course;
import com.devcollege.exceptions.NoDataFoundException;
import com.devcollege.exceptions.NotFoundException;
import com.devcollege.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@Validated
@RequestMapping("/course")
public class CourseController {

	@Autowired
	private CourseService courseService;

	@PostMapping("/addcourse")
	public ResponseEntity<String> addCourse(@Valid @RequestBody Course course) {
		Course savedCourse = courseService.addCourse(course);
		return ResponseEntity.ok("Successfully Added Course details for " + course.getCourseId());
	}
	
	@PutMapping("/updatecourse/{courseId}")
	public ResponseEntity<String> updateCourseById(@Valid @RequestBody Course course, @PathVariable String courseId) throws NotFoundException {
		String updatedCourse = courseService.updateCourseById(course, courseId);
		return ResponseEntity.ok("Successfully Updated Course details for "+ courseId);
	}
	
	@DeleteMapping("/deletecourse/{courseId}")
	public ResponseEntity<Map<String,String>> deleteCourse(@Valid @PathVariable String courseId) throws NotFoundException {
		Map<String,String> deleteCourse = this.courseService.deleteCourse(courseId);
		return new ResponseEntity<>(deleteCourse,HttpStatus.OK);
	}

	@GetMapping("/get/{courseId}")
	public ResponseEntity<Course> getCourseById(@Valid @PathVariable String courseId) throws NotFoundException{
		Course retrieveCourse = courseService.getCourseById(courseId);
		return new ResponseEntity<>(retrieveCourse, HttpStatus.OK);
	}

	@GetMapping("/getAll")
	public ResponseEntity<List<Course>> getAllCourses() throws NoDataFoundException {
		List<Course> courseList = courseService.getAllCourses();
		return new ResponseEntity<List<Course>>(courseList, HttpStatus.OK);
	}

}

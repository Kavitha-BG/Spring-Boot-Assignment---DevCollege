package com.devcollege.controllers;

import com.devcollege.entities.Course;
import com.devcollege.entities.Enrollment;
import com.devcollege.exceptions.NoDataFoundException;
import com.devcollege.exceptions.NotFoundException;
import com.devcollege.payloads.EnrollmentDto;
import com.devcollege.services.EnrollmentService;
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
@RequestMapping("/enrollment")
public class EnrollmentController {
	@Autowired
	private EnrollmentService enrollmentService;

	@PostMapping("/add")
	public ResponseEntity<String> addEnrolmentForCourse(@Valid @RequestBody Enrollment enrollment) throws NotFoundException{
		String savedEnrollment = enrollmentService.addEnrollmentForCourse(enrollment);
		return ResponseEntity.ok(savedEnrollment);
	}

	@GetMapping("/get/{enrolId}")
	public ResponseEntity<EnrollmentDto> getEnrollmentById(@Valid @PathVariable String enrolId) throws NotFoundException {
		EnrollmentDto retrieveEnrollment = enrollmentService.getEnrollmentById(enrolId);
		return new ResponseEntity<EnrollmentDto>(retrieveEnrollment, HttpStatus.OK);
	}

	@GetMapping("/getAll")
	public ResponseEntity<List<EnrollmentDto>> getAllEnrollments() throws NoDataFoundException {
		List<EnrollmentDto> enrollmentList = enrollmentService.getAllEnrollments();
		return new ResponseEntity<List<EnrollmentDto>>(enrollmentList, HttpStatus.OK);
	}

	@GetMapping("/getstudent/{studentId}")
	public ResponseEntity<List<EnrollmentDto>> getEnrollmentByStudentId(@Valid @PathVariable String studentId) throws NotFoundException {
		List<EnrollmentDto> retrieveEnrollment = enrollmentService.getEnrollmentByStudentId(studentId) ;
		return new ResponseEntity<List<EnrollmentDto>>( retrieveEnrollment, HttpStatus.OK);
	}

	@PutMapping("/status/{enrolId}")
	public ResponseEntity<Map<String,String>> changeStatus(@RequestBody Enrollment enrollment, @PathVariable String enrolId) throws NotFoundException {
		Map<String,String> updatedStatus = this.enrollmentService.changeStatus(enrollment, enrolId);
		return new ResponseEntity<Map<String,String>>(updatedStatus,HttpStatus.OK);
	}

	@GetMapping("/availability/{courseId}")
	public ResponseEntity<String> checkAvailability(@PathVariable String courseId) throws NotFoundException {
		String checkCourseAvailability = enrollmentService.checkAvailability(courseId);
		return new ResponseEntity<String>(checkCourseAvailability,HttpStatus.OK);
	}

	@GetMapping("/suggestion/{studentId}")
	public ResponseEntity<List<Course>> courseSuggestion(@Valid @PathVariable String studentId) throws NotFoundException {
		List<Course> suggestCourse = enrollmentService.courseSuggestion(studentId);
		return new ResponseEntity<List<Course>>(suggestCourse, HttpStatus.OK);
	}

}

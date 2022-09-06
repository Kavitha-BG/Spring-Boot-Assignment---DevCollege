package com.devcollege.controllers;

import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import com.devcollege.entities.StudentWallet;
import com.devcollege.exceptions.NoDataFoundException;
import com.devcollege.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.devcollege.entities.Student;
import com.devcollege.services.StudentService;

@RestController
@Validated
@RequestMapping("/student")
public class StudentController {
	@Autowired
	private StudentService studentService;

	@PostMapping("/addstudent")
	public ResponseEntity<String> addStudent(@Valid @RequestBody Student student) {
		Student savedStudent = studentService.addStudent(student);
		return ResponseEntity.ok("Successfully Added Student details for "+ student.getStudentId());
	}
	
	@PutMapping("/updatestudent/{studentId}")
	public ResponseEntity<String> updateStudentById(@Valid @RequestBody Student student, @PathVariable String studentId) {
		Student updatedStudent = studentService.updateStudentById(student, studentId);
		return ResponseEntity.ok("Successfully Updated Student details for "+ studentId);
	}

	@DeleteMapping("/deletestudent/{studentId}")
	public ResponseEntity<Map<String,String>> deleteStudent(@Valid @PathVariable String studentId) throws NotFoundException {
		Map<String,String> deleteStudent = studentService.deleteStudent(studentId);
//		return ResponseEntity.ok("Successfully Deleted Student details for "+ studentId);
		return new ResponseEntity<Map<String,String>>(deleteStudent,HttpStatus.OK);
	}

//	@GetMapping("/get")
//	public ResponseEntity<Student> getStudentById(@Valid @RequestParam(value="studentId", required=false) String studentId) throws StudentNotFoundException {
//		Student retrieveStudent = studentService.getStudentById(studentId);
//		return new ResponseEntity<Student>(retrieveStudent, HttpStatus.OK);
//	}

	@GetMapping("/get/{studentId}")
	public ResponseEntity<Student> getStudentById(@Valid @PathVariable String studentId) throws NotFoundException {
		Student retrieveStudent = studentService.getStudentById(studentId);
		return new ResponseEntity<Student>(retrieveStudent, HttpStatus.OK);
	}

	@GetMapping("/getAll")
	public ResponseEntity<List<Student>> getAllStudents() throws NoDataFoundException {
		List<Student> studentList = studentService.getAllStudents();
		return new ResponseEntity<List<Student>>(studentList, HttpStatus.OK);
	}

	@PostMapping("studentwallet/{studentId}")
	public ResponseEntity<?> addWalletAmount(@PathVariable String studentId , @RequestBody StudentWallet studentWallet) throws NotFoundException{
		Float finalAmount = studentService.addWalletAmount(studentId,studentWallet);
		return ResponseEntity.ok("Successfully added Amount for "+ studentId
				+ " " + "and available balance is " + finalAmount);
	}

	@GetMapping("studentwallet/{studentId}")
	public ResponseEntity<Map<String, String>> getWalletDetail(@PathVariable String studentId) throws NotFoundException{
		Map<String, String> retrieveStudentWallet = studentService.getWalletDetail(studentId);
		return new ResponseEntity<>(retrieveStudentWallet, HttpStatus.ACCEPTED);
	}

}

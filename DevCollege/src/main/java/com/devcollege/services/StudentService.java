package com.devcollege.services;

import java.util.List;
import java.util.Map;
import com.devcollege.entities.Student;
import com.devcollege.entities.StudentWallet;
import com.devcollege.exceptions.NoDataFoundException;
import com.devcollege.exceptions.NotFoundException;
import org.springframework.transaction.annotation.Transactional;

public interface StudentService {
	
	public Student addStudent(Student student);

	public Student updateStudentById(Student student, String studentId);

//	@Transactional
	public Map<String,String> deleteStudent(String studentId) throws NotFoundException;
	
	public Student getStudentById(String studentId) throws NotFoundException;
	
	public List<Student> getAllStudents() throws NoDataFoundException;

	public Float addWalletAmount(String studentId,StudentWallet studentWallet);

	public Map<String,String> getWalletDetail(java.lang.String studentId);

}
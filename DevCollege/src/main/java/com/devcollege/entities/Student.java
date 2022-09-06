package com.devcollege.entities;

import javax.persistence.*;
import javax.validation.constraints.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import com.devcollege.sequencestylegenerator.SequenceIdGenerator;
import java.util.List;

@Entity
@Table(name="students")
public class Student {

	@Id	
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "student_seq")
	@GenericGenerator(
			name = "student_seq", 
			strategy = "com.devcollege.sequencestylegenerator.SequenceIdGenerator",
			parameters = {
				@Parameter(name = SequenceIdGenerator.INCREMENT_PARAM, value = "1"),
	            @Parameter(name = SequenceIdGenerator.VALUE_PREFIX_PARAMETER, value = "STD"),
	            @Parameter(name = SequenceIdGenerator.NUMBER_FORMAT_PARAMETER, value = "%04d")
			} )	
	@Column(name="student_id", nullable=false, length=16)
	private String studentId;

	@Column(name="student_name",nullable=false,length=50)
	@NotBlank(message = "Student name should not be null")
	@Pattern(regexp = "[A-Za-z ]*", message = " Student Name should be valid")
	private String studentName;
	
	@Column(name="highest_qualification",nullable=false)
	@Pattern(regexp = "^[A-Za-z]+[A-Za-z ]*$", message = " HighestQualification should be valid")
	@NotBlank(message="Highest Qualification should be B.E, B.Tech, Diploma, M.E, M.Tech., M.Phil., MS, BBA, BCom, BSc, MSc, BCA, MCA, LLB, MBBS, MBA")
	private String highestQualification;
	
	@Column(name="student_contact_no",nullable=false,length=10)
	@Size(min = 10, max = 10, message = "Contact number must be 10-digit numeric string")
	@NotBlank
	@Pattern(regexp = "(^$|[0-9]{10})", message = "Contact no should be 10 digits")
	private String studentContactNo;
	
	@Column(name="wallet_amount",nullable=false,length=10)
	@NotNull
//	@Positive(message= "Wallet Amount must be numeric or decimal positive value")
	@Min(value=1, message = "WalletAmount should be Rs.1")
	@Max(value = 50000, message = "Wallet Amount should be lesser than Rs.50000")
	private Float walletAmount;
	
	public Student() {
		super();
	}

	public Student(String studentId, Float walletAmount) {
		this.studentId = studentId;
		this.walletAmount = walletAmount;
	}

	public Student(String studentId, @NotBlank String studentName, @NotBlank String highestQualification, @NotEmpty String studentContactNo,
				   Float walletAmount) {
		this.studentId = studentId;
		this.studentName = studentName;
		this.highestQualification = highestQualification;
		this.studentContactNo = studentContactNo;
		this.walletAmount = walletAmount;
	}

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getHighestQualification() {
		return highestQualification;
	}

	public void setHighestQualification(String highestQualification) {
		this.highestQualification = highestQualification;
	}

	public String getStudentContactNo() {
		return studentContactNo;
	}

	public void setStudentContactNo(String studentContactNo) {
		this.studentContactNo = studentContactNo;
	}

	public Float getWalletAmount() {
		return walletAmount;
	}

	public void setWalletAmount(Float walletAmount) {
		this.walletAmount = walletAmount;
	}
	

	@Override
	public String toString() {
		return "Student [studentId=" + studentId + ", studentName=" + studentName + ", highestQualification="
				+ highestQualification + ", studentContactNo=" + studentContactNo + ", walletAmount=" + walletAmount
				+ "]";
	}

//	@OneToMany(targetEntity = Enrollment.class, fetch = FetchType.EAGER)
//	@JoinColumn(
//			name = "studentId",
//			referencedColumnName = "student_Id"
//	)
//	@JsonBackReference
//	private List<Enrollment> enrollmentList;

}

























//	@OneToMany(mappedBy = "students", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//	private List<Enrollment> enrollmentList;

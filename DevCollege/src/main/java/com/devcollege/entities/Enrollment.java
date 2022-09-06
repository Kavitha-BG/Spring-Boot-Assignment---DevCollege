package com.devcollege.entities;

import com.devcollege.sequencestylegenerator.SequenceIdGenerator;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Entity
@Table(name="enrollments")
public class Enrollment {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "enrollment_seq")
	@GenericGenerator(
			name = "enrollment_seq",
			strategy = "com.devcollege.sequencestylegenerator.SequenceIdGenerator",
			parameters = {
					@Parameter(name = SequenceIdGenerator.INCREMENT_PARAM, value = "1"),
					@Parameter(name = SequenceIdGenerator.VALUE_PREFIX_PARAMETER, value = "EN"),
					@Parameter(name = SequenceIdGenerator.NUMBER_FORMAT_PARAMETER, value = "%04d")
			} )
	@Column(name="enrol_id", updatable = false, nullable=false)
	private String enrolId;

	private String courseId;

	private String studentId;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name="course_start_datetime",nullable=false)
	@FutureOrPresent(message = "Course Start Date Time should be present or future date")
	@NotNull(message = "Course Start Datetime  should not be null")
	private Date courseStartDatetime;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name="course_end_datetime",nullable=false)
	@Future(message = "Course End Date Time should be future date")
	private Date courseEndDatetime;

	@Column(name="course_status",nullable=false)
	@Pattern(regexp = "^[A-Za-z]+[A-Za-z ]*$", message = " Course Status should be valid")
	private String courseStatus;

	public Enrollment() {

	}

	@Override
	public String toString() {
		return "Enrollment{" +
				"enrolId='" + enrolId + '\'' +
				", courseId='" + courseId + '\'' +
				", studentId='" + studentId + '\'' +
				", courseStartDatetime=" + courseStartDatetime +
				", courseEndDatetime=" + courseEndDatetime +
				", courseStatus='" + courseStatus + '\'' +
				'}';
	}

	public String getEnrolId() {
		return enrolId;
	}

	public void setEnrolId(String enrolId) {
		this.enrolId = enrolId;
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public Date getCourseStartDatetime() {
		return courseStartDatetime;
	}

	public void setCourseStartDatetime(Date courseStartDatetime) {
		this.courseStartDatetime = courseStartDatetime;
	}

	public Date getCourseEndDatetime() {
		return courseEndDatetime;
	}

	public void setCourseEndDatetime(Date courseEndDatetime) {
		this.courseEndDatetime = courseEndDatetime;
	}

	public String getCourseStatus() {
		return courseStatus;
	}

	public void setCourseStatus(String courseStatus) {
		this.courseStatus = courseStatus;
	}

	public Enrollment(String enrolId, String courseId, String studentId, Date courseStartDatetime, Date courseEndDatetime, String courseStatus, String courseLink, String studentLink) {
		this.enrolId = enrolId;
		this.courseId = courseId;
		this.studentId = studentId;
		this.courseStartDatetime = courseStartDatetime;
		this.courseEndDatetime = courseEndDatetime;
		this.courseStatus = courseStatus;
	}

}



























//	@ManyToOne(fetch = FetchType.LAZY, optional = false)
//	@JoinColumn(name = "student_id", nullable = false)
//	private Student students;

//	@ManyToOne(fetch = FetchType.LAZY, optional = false)
//	@JoinColumn(name = "course_id", nullable = false)
//	private Course courses;

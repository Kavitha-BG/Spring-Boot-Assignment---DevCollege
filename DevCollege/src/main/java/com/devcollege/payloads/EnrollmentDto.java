package com.devcollege.payloads;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;

public class EnrollmentDto {

    private String enrolId;
    private String courseId;
    private String courseName;
    private String studentId;
    private String studentName;
    @JsonFormat(pattern = "YYYY/MM/DD HH:mm:ss")
    private Date courseStartDatetime;
    @JsonFormat(pattern = "YYYY/MM/DD HH:mm:ss")
    private Date courseEndDatetime;
    private String courseStatus;
    private String courseLink ;
    private String studentLink;

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
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

    public void setStudentId(String studentId) { this.studentId = studentId; }

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

    public String getCourseLink() {
        return courseLink;
    }

    public void setCourseLink(String courseLink) {
        this.courseLink = courseLink;
    }

    public String getStudentLink() {
        return studentLink;
    }

    public void setStudentLink(String studentLink) {
        this.studentLink = studentLink;
    }

    public EnrollmentDto() {
    }

    public EnrollmentDto(String enrolId, String courseId, String courseName, String studentId, String studentName, Date courseStartDatetime,
                         Date courseEndDatetime, String courseStatus, String courseLink, String studentLink) {
        this.enrolId = enrolId;
        this.courseId = courseId;
        this.courseName = courseName;
        this.studentId = studentId;
        this.studentName = studentName;
        this.courseStartDatetime = courseStartDatetime;
        this.courseEndDatetime = courseEndDatetime;
        this.courseStatus = courseStatus;
        this.courseLink = courseLink;
        this.studentLink = studentLink;
    }

}

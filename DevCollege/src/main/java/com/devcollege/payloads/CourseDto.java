package com.devcollege.payloads;

public class CourseDto {

    private String courseName;
    private String courseDescription;
    private int noOfSlot;
    private float courseFee;
    private int courseDuration;
    private String courseTag;

    public CourseDto(String courseName, String courseDescription,
                     int noOfSlot, float courseFee, int courseDuration, String courseTag) {
        this.courseName = courseName;
        this.courseDescription = courseDescription;
        this.noOfSlot = noOfSlot;
        this.courseFee = courseFee;
        this.courseDuration = courseDuration;
        this.courseTag = courseTag;
    }

    public CourseDto() {
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseDescription() {
        return courseDescription;
    }

    public void setCourseDescription(String courseDescription) {
        this.courseDescription = courseDescription;
    }

    public int getNoOfSlot() {
        return noOfSlot;
    }

    public void setNoOfSlot(int noOfSlot) {
        this.noOfSlot = noOfSlot;
    }

    public float getCourseFee() {
        return courseFee;
    }

    public void setCourseFee(float courseFee) {
        this.courseFee = courseFee;
    }

    public int getCourseDuration() {
        return courseDuration;
    }

    public void setCourseDuration(int courseDuration) {
        this.courseDuration = courseDuration;
    }

    public String getCourseTag() {
        return courseTag;
    }

    public void setCourseTag(String courseTag) {
        this.courseTag = courseTag;
    }
}

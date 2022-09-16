package com.devcollege.repositories;

import com.devcollege.entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, String> {
    @Query(nativeQuery= true,value="SELECT course_status FROM enrollments where course_id=:courseId")
    public String getStatusByStudentId(@Param("courseId") String courseId);

}

package com.devcollege.repositories;

import com.devcollege.entities.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, String> {
    @Query(nativeQuery = true,value = "SELECT * FROM enrollments where student_id=:studentId")
    public List<Enrollment> getAllEnrollments(@Param("studentId") String studentId);
    @Query(nativeQuery = true,value = "SELECT * FROM enrollments where course_id=:courseId")
    public List<Enrollment> getAllEnrollmentsCourse(@Param("courseId") String courseId);
}

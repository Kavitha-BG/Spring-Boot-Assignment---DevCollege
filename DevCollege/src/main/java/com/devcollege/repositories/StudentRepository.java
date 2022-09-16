package com.devcollege.repositories;

import com.devcollege.entities.StudentWallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.devcollege.entities.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, String> {
    @Query(nativeQuery= true,value="SELECT course_status FROM enrollments where student_id=:studentId")
    public String getCourseStatusByStudentId(@Param("studentId") String studentId);
}

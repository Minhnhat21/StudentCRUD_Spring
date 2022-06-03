package com.example.demo.student;

import com.example.demo.modal.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    //@Query(value = "SELECT s FROM Student s WHERE (:name is NULL  or s.name LIKE %:name%)")
    @Query("SELECT s FROM Student s WHERE s.name LIKE %:name%")
    List<Student> findStudentByName(String name);
}

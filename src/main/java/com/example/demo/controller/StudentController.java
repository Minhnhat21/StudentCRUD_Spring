package com.example.demo.controller;

import com.example.demo.service.StudentService;
import com.example.demo.modal.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/students")
public class StudentController {
    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping()
    public HttpEntity<List<Student>> getStudents() {
        return new HttpEntity<>(studentService.getStudents());
    }

    @PostMapping
    public HttpEntity<Student> registerStudent(@RequestBody Student student) {
        return new HttpEntity<>(studentService.addNewStudent(student));
    }

    @PutMapping("/{id}")
    public HttpEntity<Student> updateStudent(@PathVariable("id") Long id, @RequestBody Student student) {
        return new HttpEntity<>(studentService.updateStudentById(id,student));
    }

    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable Long id) {
        studentService.deleteStudentById(id);
    }
}

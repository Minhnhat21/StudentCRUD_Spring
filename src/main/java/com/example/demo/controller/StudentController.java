package com.example.demo.controller;

import com.example.demo.response.APIResponse;
import com.example.demo.service.StudentService;
import com.example.demo.modal.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.rmi.StubNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @GetMapping("/{id}")
    public HttpEntity<Student> getStudent(@PathVariable("id") Long id) {
        return new HttpEntity<>(studentService.getStudentById(id));
    }

    @PostMapping
//    public APIResponse<?> registerStudent(@Valid @RequestBody Student student, BindingResult bindingResult) {
//        if(bindingResult.hasErrors())
//        {
//            return APIResponse.errorStatus(bindingResult.getFieldError().getDefaultMessage(), HttpStatus.BAD_REQUEST);
//        }
//        return  APIResponse.okStatus(studentService.addNewStudent(student));
//    }
    public HttpEntity<Student> registerStudent(@Valid @RequestBody Student student) {
        return new HttpEntity<>(studentService.addNewStudent(student));
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    @PutMapping("/{id}")
    public HttpEntity<Student> updateStudent(@PathVariable("id") Long id, @RequestBody Student student) {
        return new HttpEntity<>(studentService.updateStudentById(id,student));
    }

    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable Long id) {
        studentService.deleteStudentById(id);
    }

    @DeleteMapping("/delete")
    public void deleteAllStudent() {
        studentService.deleteAllStudent();
    }

    @GetMapping("/q")
    public HttpEntity<List<Student>> searchStudentByName(@Param("name") String name) {
        return new HttpEntity<>(studentService.searchStudentByName(name));
    }
}

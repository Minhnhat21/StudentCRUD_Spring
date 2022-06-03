package com.example.demo.service;

import com.example.demo.modal.Student;
import com.example.demo.student.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    @Autowired
    private final StudentRepository studentRepository;
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    public Student getStudentById(Long id) {
        return studentRepository.findById(id).get();
    }

    public Student addNewStudent(Student student) {
        return studentRepository.save(student);
    }

    public Student updateStudentById(Long id, Student student) {
        Student updateStudent = studentRepository.findById(id).get();
        updateStudent.setName(student.getName());
        updateStudent.setEmail(student.getEmail());
        updateStudent.setDob(student.getDob());
        return studentRepository.save(updateStudent);
    }

    public void deleteStudentById(Long id) {
        studentRepository.deleteById(id);
    }

    public void deleteAllStudent() {
        studentRepository.deleteAll();
    }

    public List<Student> searchStudentByName(String name) {
        if(!studentRepository.findStudentByName(name).isEmpty())
            return studentRepository.findStudentByName(name);
        return studentRepository.findAll();
    }
}

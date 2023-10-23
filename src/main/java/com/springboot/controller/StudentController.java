package com.springboot.controller;

import com.springboot.dto.StudentRequestDTO;
import com.springboot.entity.Student;
import com.springboot.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class StudentController {
    private StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/students")
    public List<Student> getStudents() {
        return studentService.getStudents();
    }

    @PostMapping("/student")
    public ResponseEntity<Student> saveStudent(@RequestBody @Valid StudentRequestDTO studentRequestDTO) {
        return new ResponseEntity<>(studentService.saveStudent(studentRequestDTO), HttpStatus.CREATED);
    }
}

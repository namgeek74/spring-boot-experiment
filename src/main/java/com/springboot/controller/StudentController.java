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
    public ResponseEntity<List<Student>> getStudents() {
        return ResponseEntity.ok().body(studentService.getStudents());
    }

    @PostMapping("/student")
    public ResponseEntity<Student> saveStudent(@RequestBody @Valid StudentRequestDTO studentRequestDTO) {
        return new ResponseEntity<>(studentService.saveStudent(studentRequestDTO), HttpStatus.CREATED);
    }

    @GetMapping("/feature-branch-1")
    public String featureBranch1() {
        return "feature branch 1";
    }

    @GetMapping("/feature-branch-1-2")
    public String featureBranch12() {
        return "feature branch 1 - 2";
    }

    @GetMapping("/feature-mater-1")
    public String featureMaster1() {
        return "feature master 1";
    }

    @GetMapping("/feature-mater-2")
    public String featureMaster2() {
        return "feature master 2";
    }

    @GetMapping("/feature-branch-2-rebase")
    public String featureMaster2Rebase() {
        return "feature master 2 rebase";
    }
}

package com.springboot.service;

import com.springboot.dto.StudentRequestDTO;
import com.springboot.entity.Student;
import com.springboot.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    private StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    public Student saveStudent(StudentRequestDTO studentRequestDTO) {
        Student student = Student.builder()
                .name(studentRequestDTO.getName())
                .email(studentRequestDTO.getEmail())
                .mobile(studentRequestDTO.getMobile())
                .age(studentRequestDTO.getAge())
                .build();
        return studentRepository.save(student);
    }
}

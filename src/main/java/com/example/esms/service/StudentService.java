package com.example.esms.service;


import com.example.esms.repository.StudentRepository;
import com.example.esms.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public void save(Student student) {
        studentRepository.save(student);
    }

    public void saveAll(List<Student> students) {
        studentRepository.saveAll(students);
    }
}

package com.example.esms.service;

import com.example.esms.entity.courseStudent.CourseStudent;
import com.example.esms.repository.CourseStudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseStudentService {
    @Autowired
    private CourseStudentRepository courseStudentRepository;

    public void save(CourseStudent courseStudent) {
        courseStudentRepository.save(courseStudent);
    }

    public void saveAll(List<CourseStudent> courseStudents) {
        courseStudentRepository.saveAll(courseStudents);
    }
}

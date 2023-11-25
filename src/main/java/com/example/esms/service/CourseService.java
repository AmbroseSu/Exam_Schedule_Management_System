package com.example.esms.service;



import com.example.esms.entity.Course;
import com.example.esms.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;

    public void save(Course course) {
        courseRepository.save(course);
    }

    public void saveAll(List<Course> courses) {
        courseRepository.saveAll(courses);
    }
}

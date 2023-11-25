package com.example.esms.service;


import com.example.esms.entity.courseStudent.CourseStudent;
import com.example.esms.entity.Student;
import com.example.esms.repository.CourseStudentFullRepository;
import com.example.esms.repository.StudentFullRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentFullService<T> {

    @Autowired
    private StudentFullRepository studentFullRepository;
    @Autowired
    private CourseStudentFullRepository courseStudentFullRepository;
    public void save(T obj) throws Exception {
        String nameClass = obj.getClass().getName();
        if (nameClass.equals("Student")) {
            studentFullRepository.save((Student) obj);
        } else if (nameClass.equals("CourseStudent")) {
            courseStudentFullRepository.save((CourseStudent) obj);
        } else throw new Exception("Does not know object");
    }
    public void saveAll(List<T> listObj) throws Exception {
        String[] nameClass = listObj.get(0).getClass().getName().split("\\.");
        if (nameClass[nameClass.length-1].equals("Student")) {
            studentFullRepository.saveAll((List<Student>) listObj);
        } else if (nameClass[nameClass.length-1].equals("CourseStudent")) {
            courseStudentFullRepository.saveAll((List<CourseStudent>)listObj);
        } else throw new Exception("Does not know object");
    }



}

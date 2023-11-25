package com.example.esms.service;


import com.example.esms.entity.Lecturer;
import com.example.esms.repository.LecturerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LecturerService {

    @Autowired
    private LecturerRepository lecturerRepository;

    public void save(Lecturer lecturer) {
        lecturerRepository.save(lecturer);
    }

    public void saveAll(List<Lecturer> lecturers) {
        lecturerRepository.saveAll(lecturers);
    }
}

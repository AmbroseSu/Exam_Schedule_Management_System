package com.example.esms.repository;


import com.example.esms.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentFullRepository extends JpaRepository<Student, String> {
}
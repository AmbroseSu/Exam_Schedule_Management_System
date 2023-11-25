package com.example.esms.entity;

import com.example.esms.entity.courseStudent.CourseStudent;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Entity
@Table(name = "Student")
@Data
@Getter
@Setter
public class Student  {

//    @Id
//    @Column(name ="student_id")
//    private String studentId;
//    @Basic
//
//    @Column(name ="student_name")
//    private String studentName;
//    @Basic
//
//    @Column(name ="student_email")
//    private String studentEmail;
    //private int studentTerm;

    // Getters, setters, constructors, etc.
    @Id
    @Column(name ="id")
    private String studentId;
    @Basic

    @Column(name ="name")
    private String studentName;
    @Basic

    @Column(name ="email")
    private String studentEmail;

//    @OneToMany(mappedBy = "studentByStudentId", fetch = FetchType.EAGER)
//    private List<CourseStudent> courseStudentsbyId;


    //private int studentTerm;

    // Getters, setters, constructors, etc.

}

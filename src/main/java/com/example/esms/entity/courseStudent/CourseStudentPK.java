package com.example.esms.entity.courseStudent;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
@Getter
@Setter
@EqualsAndHashCode
public class CourseStudentPK implements Serializable {
    @Column(name ="Course_id")
    @Id
    private String courseId;
    @Column(name = "Student_ID")
    @Id

    private String studentId;
}

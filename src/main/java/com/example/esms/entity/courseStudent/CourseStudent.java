package com.example.esms.entity.courseStudent;

import com.example.esms.entity.Course;
import com.example.esms.entity.Student;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

@Entity
@Table(name = "Course_student")
@IdClass(CourseStudentPK.class)
@Getter
@Setter
@EqualsAndHashCode
public class CourseStudent  {
    @Id
    @Column(name = "Course_id")
    private String courseId;
    @Id
    @Column(name = "Student_ID")
    private String studentId;
    @ManyToOne
    @JoinColumn(name = "Course_id", referencedColumnName = "id", nullable = false,insertable = false,updatable = false)
    private Course courseByCourseId;
    @ManyToOne
    @JoinColumn(name = "Student_ID", referencedColumnName = "id", nullable = false,insertable = false,updatable = false)
    private Student studentByStudentId;


}

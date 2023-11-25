package com.example.esms.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "exam_schedule")
@Getter
@Setter
public class ExamScheduleStudent {
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private String id;
    @Column(name = "Room_id")
    private String roomID;
    @Column(name = "slot_id")
    private String slotID;
    @Column(name = "lecture_id")
    private String lecturerID;
    @Column(name = "course_id")
    private String courseID;
    @Column(name = "student_id")
    private String studentID;
}

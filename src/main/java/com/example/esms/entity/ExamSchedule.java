package com.example.esms.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Exam_schedule")
@Data
@Getter
@Setter
public class ExamSchedule {
    @Id
    @Column(name = "id")
    private String examScheduleId;
    @Column(name = "student_id")
    private String studentId;
    @Column(name = "course_id")
    private String courseId;
    @Column(name = "lecture_id")
    private String lectureId;
    @Column(name = "Room_id")
    private String roomId;
    @Column(name = "slot_id")
    private String slotId;
}

package com.example.esms.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Lecture")
@Getter
@Setter
public class Lecturer {
    @Id
    @Column(name = "id")
    private String lecturerId;
    @Column(name = "Name")
    private String lecturerName;
    @Column(name = "Email")
    private String lecturerEmail;
    @Column(name = "Phone")
    private String lecturerPhone;
}

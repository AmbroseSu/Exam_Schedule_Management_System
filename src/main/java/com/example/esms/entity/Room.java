package com.example.esms.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "classroom")
@Data
public class Room {
    @Id
    @Column(name = "room")
    private String roomId;
}

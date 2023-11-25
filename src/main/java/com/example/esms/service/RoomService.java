package com.example.esms.service;



import com.example.esms.repository.RoomRepository;
import com.example.esms.entity.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {
    @Autowired
    private RoomRepository roomRepository;

    public void save(Room room) {
        roomRepository.save(room);
    }

    public void saveAll(List<Room> rooms) {
        roomRepository.saveAll(rooms);
    }
}

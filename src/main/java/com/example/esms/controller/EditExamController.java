package com.example.esms.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@RestController
@RequestMapping("")
public class EditExamController {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public EditExamController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("/editexam")
    public ResponseEntity<Object> deleteExamSchedule(String room,String slotId, String courseId) {
        try {
            Map<String,Object> slot = jdbcTemplate.queryForMap("select Exam_slot.Date from Exam_slot where id =?",slotId);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            // Parse the date string into a LocalDate object using the defined format
            LocalDate insertedDate = LocalDate.parse((CharSequence) slot.get("Date"), formatter);

            // Get today's date
            LocalDate today = LocalDate.now();

            // Compare the dates
            if (insertedDate.isEqual(today) || insertedDate.isBefore(today) || today.isAfter(insertedDate.minusDays(8))) {
                //return ResponseEntity.status(HttpStatus.OK).body("Insert date false");
                return ResponseEntity.ok("Too late to delete");
            }
            jdbcTemplate.update("delete from Exam_schedule where Room_id = ? and slot_id =? and course_id = ?",room, slotId,courseId);
            return ResponseEntity.ok("Success");
        }
        catch (Exception e){
            return ResponseEntity.ok(e.getMessage());
        }
    }
}

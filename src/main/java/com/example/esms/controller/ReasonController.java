package com.example.esms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("")
public class ReasonController {
    private JdbcTemplate jdbcTemplate;
    @Autowired
    public ReasonController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate  = jdbcTemplate;
    }

    @GetMapping("/reason")
    public ResponseEntity<List<Map<String,Object>>> getReason(){
        try {
            List<Map<String, Object>> listReason = jdbcTemplate.queryForList("select l.Email,es.Date,es.Time,r.reason from Reason r inner join Lecture l on l.id = r.lecturerId inner join Exam_slot es on es.id = r.slotId");
            return ResponseEntity.ok(listReason);
        }
        catch (Exception e){
            throw e;
        }
    }
}

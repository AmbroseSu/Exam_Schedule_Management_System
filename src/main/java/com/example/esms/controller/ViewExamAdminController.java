package com.example.esms.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("")
public class ViewExamAdminController {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ViewExamAdminController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("/viewexamadmin")//not use
    public ResponseEntity<Object> getStudentProfileData(/*String email*/) {
        /*OAuth2User user = token.getPrincipal();
        String email = user.getAttribute("email");*/
        // Thực hiện truy vấn để lấy profile của student
//        Map<String, Object> lecturerProfile = jdbcTemplate.queryForMap(
//                "SELECT * FROM Lecture WHERE email = ?", email);
//        String stuID = (String) lecturerProfile.get("id");
        List<Map<String, Object>> viewExamSchedule = jdbcTemplate.queryForList(
                /*"select * from Exam_schedule exch inner join Exam_slot exsl on exch.slot_id = exsl.id"*/
                "select * from Exam_slot");

        if (!viewExamSchedule.isEmpty()) {
            return ResponseEntity.ok(viewExamSchedule);
        } else {
            // Trường hợp không tìm thấy profile hoặc địa chỉ email không phù hợp, trả về lỗi 404
            return ResponseEntity.notFound().build();
        }

    }
    @GetMapping("/viewexamimater")
    public ResponseEntity<Object> getStudentProfileData1(/*String email*/) {
        /*OAuth2User user = token.getPrincipal();
        String email = user.getAttribute("email");*/
        // Thực hiện truy vấn để lấy profile của student
//        Map<String, Object> lecturerProfile = jdbcTemplate.queryForMap(
//                "SELECT * FROM Lecture WHERE email = ?", email);
//        String stuID = (String) lecturerProfile.get("id");
        List<Map<String, Object>> viewExamMinater = jdbcTemplate.queryForList(
                /*"select * from Exam_schedule exch inner join Exam_slot exsl on exch.slot_id = exsl.id"*/
                "SELECT distinct es.slot_id,es.course_id,el.Date,el.Time,es.Room_id,es.lecture_id FROM [Exam_schedule] es inner join Exam_slot el on el.id = es.slot_id order by el.Date");

        if (!viewExamMinater.isEmpty()) {
            return ResponseEntity.ok(viewExamMinater);
        } else {
            // Trường hợp không tìm thấy profile hoặc địa chỉ email không phù hợp, trả về lỗi 404
            return ResponseEntity.notFound().build();
        }
    }
}

package com.example.esms.controller;


import com.example.esms.entity.Lecturer;
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
public class ViewExamStudentController {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ViewExamStudentController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("/viewexamstudent")
    public ResponseEntity<Object> getStudentProfileData(String email) {
        /*OAuth2User user = token.getPrincipal();
        String email = user.getAttribute("email");*/
        // Thực hiện truy vấn để lấy profile của student
        Map<String, Object> studentProfile = jdbcTemplate.queryForMap(
                "SELECT * FROM Student WHERE email = ?", email);
        String stuID = (String) studentProfile.get("id");
        List<Map<String, Object>> viewExamSchedule = jdbcTemplate.queryForList(
                "select * from Exam_schedule exch inner join Exam_slot exsl on exch.slot_id = exsl.id where student_id = ?",stuID);

        if (!viewExamSchedule.isEmpty()) {
            return ResponseEntity.ok(viewExamSchedule);
        } else {
            // Trường hợp không tìm thấy profile hoặc địa chỉ email không phù hợp, trả về lỗi 404
            return ResponseEntity.notFound().build();
        }
    }
}

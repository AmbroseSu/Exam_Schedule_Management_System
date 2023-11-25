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
public class CheckCourseController {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CheckCourseController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("/checkcourseexists")
    public String getCourse() {

        /*OAuth2User user = token.getPrincipal();
        String email = user.getAttribute("email");*/
        // Thực hiện truy vấn để lấy profile của student
        List<Map<String, Object>> courses = jdbcTemplate.queryForList("SELECT * FROM Course");
        if (!courses.isEmpty()) {
            return "Have Course";
        } else {
            // Trường hợp không tìm thấy profile hoặc địa chỉ email không phù hợp, trả về lỗi 404
            return "Haven't Course";
        }
    }
}

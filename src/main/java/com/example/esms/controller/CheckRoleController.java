package com.example.esms.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("")
public class CheckRoleController {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CheckRoleController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @GetMapping("/checkrole")
    public String checkRole(String email) {
        // Thực hiện truy vấn để lấy email từ cơ sở dữ liệu
        List<Map<String, Object>> studentList = jdbcTemplate.queryForList("SELECT student_email FROM student");
        List<Map<String, Object>> lecturerList = jdbcTemplate.queryForList("SELECT lecturer_email FROM lecturer");

        boolean isStudent = emailMatchesStudent(email, studentList);
        boolean isLecturer = emailMatchesLecturer(email, lecturerList);

        if (isStudent) {
            // Email khớp với danh sách sinh viên, chuyển hướng đến /profileStudent

            // Lưu token vào phiên nếu người dùng là học sinh

            return "redirect:http://localhost:3001/profile";
        } else if (isLecturer) {
            // Email khớp với danh sách giảng viên, chuyển hướng đến /profileLecturer
            return "profileLecturer";
        } else {
            // Email không khớp với cả hai danh sách, chuyển hướng đến /error
            return "notHaveRole";
        }
    }

    private boolean emailMatchesStudent(String email, List<Map<String, Object>> emailList) {
        for (Map<String, Object> emailRow : emailList) {
            String dbEmail = (String) emailRow.get("student_email");
            if (email.equals(dbEmail)) {
                return true;
            }
        }
        return false;
    }

    private boolean emailMatchesLecturer(String email, List<Map<String, Object>> emailList) {
        for (Map<String, Object> emailRow : emailList) {
            String dbEmail = (String) emailRow.get("lecturer_email");
            if (email.equals(dbEmail)) {
                return true;
            }
        }
        return false;
    }
}


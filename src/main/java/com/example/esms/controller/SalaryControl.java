package com.example.esms.controller;

import lombok.Getter;
import lombok.Setter;
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
public class SalaryControl {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public SalaryControl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Getter
    @Setter

    class tmp{
        float salary;
        float hour;
    }

    @GetMapping("/salary")
    public ResponseEntity<Object> getViewExamLecturerChooseData(String email) {
        List<Map<String, Object>> viewExamSchedule = jdbcTemplate.queryForList(
                "select distinct slot_id  from Lecture  inner join Exam_schedule Es on Lecture.id = Es.lecture_id where Lecture.Email = ?", email);

        tmp result = new tmp();

        result.setHour(viewExamSchedule.size()*2.25f);

        result.setSalary(result.getHour()*100000);
        if (!viewExamSchedule.isEmpty()) {
            return ResponseEntity.ok(result);
        } else {
            // Trường hợp không tìm thấy profile hoặc địa chỉ email không phù hợp, trả về lỗi 404
            return ResponseEntity.notFound().build();
        }
    }
}

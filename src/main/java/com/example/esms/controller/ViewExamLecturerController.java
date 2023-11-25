package com.example.esms.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("")
public class ViewExamLecturerController {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ViewExamLecturerController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("/viewexamlecturerchoose")
    public ResponseEntity<Object> getViewExamLecturerChooseData(String email) {
        /*OAuth2User user = token.getPrincipal();
        String email = user.getAttribute("email");*/
        // Thực hiện truy vấn để lấy profile của student
//        Map<String, Object> lecturerProfile = jdbcTemplate.queryForMap(
//                "SELECT * FROM Lecture WHERE email = ?", email);
//        String stuID = (String) lecturerProfile.get("id");
        List<Map<String, Object>> viewExamSchedule = jdbcTemplate.queryForList(
                "select distinct exch.course_id,exch.Room_id,exsl.Date,exsl.Time,exsl.Hour,exch.slot_id from Exam_schedule exch inner join Exam_slot exsl on exch.slot_id = exsl.id inner join Lecture le on exch.lecture_id = le.id where le.Email = ?",email);
        /*double hour = 0;
        for (Map<String, Object> ex :
                viewExamSchedule) {
            hour+=Double.parseDouble((String) ex.get("Hour"));
        }
        Map<String,Object> salary = new HashMap<>();
        salary.put("salary",hour*100000.0);
        viewExamSchedule.add(salary);*/
        if (!viewExamSchedule.isEmpty()) {
            return ResponseEntity.ok(viewExamSchedule);
        } else {
            // Trường hợp không tìm thấy profile hoặc địa chỉ email không phù hợp, trả về lỗi 404
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/viewexamlecturer")
    public ResponseEntity<Object> getViewExamLecturerData(String email) {
        /*OAuth2User user = token.getPrincipal();
        String email = user.getAttribute("email");*/
        // Thực hiện truy vấn để lấy profile của student
//        Map<String, Object> lecturerProfile = jdbcTemplate.queryForMap(
//                "SELECT * FROM Lecture WHERE email = ?", email);
//        String stuID = (String) lecturerProfile.get("id");
        List<Map<String, Object>> viewExamSchedule = jdbcTemplate.queryForList(
                "SELECT distinct es.slot_id,el.Date,el.Time,el.Hour FROM [Exam_schedule] es inner join Exam_slot el on el.id = es.slot_id where es.lecture_id is null order by el.Date");
        List<Map<String, Object>> tmp = new ArrayList<>();
        for (Map<String, Object> vE:
        viewExamSchedule) {
            List<Map<String, String>> slotLecture = jdbcTemplate.query("select es.Room_id from Exam_schedule es, Lecture l where es.lecture_id = l.id and l.Email =? and es.slot_id =? group by Room_id", new ResultSetExtractor<List<Map<String, String>>>() {
                @Override
                public List<Map<String, String>> extractData(ResultSet rs) throws SQLException, DataAccessException {
                    List<Map<String, String>> listRet = new ArrayList<>();
                    while (rs.next()) {
                        Map<String, String> mapRet = new HashMap<>();
                        mapRet.put("Room_id", rs.getString("Room_id"));

                        listRet.add(mapRet);
                    }
                    return listRet;
                }
            }, email, vE.get("slot_id"));
            assert slotLecture != null;
            if (slotLecture.size()==0) {
                tmp.add(vE);
            }
        }

        if (!tmp.isEmpty()) {
            return ResponseEntity.ok(tmp);
        } else {
            // Trường hợp không tìm thấy profile hoặc địa chỉ email không phù hợp, trả về lỗi 404
            return ResponseEntity.notFound().build();
        }
    }


}

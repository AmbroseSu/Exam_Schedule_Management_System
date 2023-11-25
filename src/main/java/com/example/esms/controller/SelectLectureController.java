package com.example.esms.controller;


import com.example.esms.entity.ExamSlot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("")
public class SelectLectureController {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public SelectLectureController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("/selectLecture")
    public ResponseEntity<Object> setLectureToExamSchedule(String emailLecture,String slotId) {
        try {
        /*OAuth2User user = token.getPrincipal();
        String email = user.getAttribute("email");*/
            // Thực hiện truy vấn để lấy profile của student
            Map<String, Object> lecture = jdbcTemplate.queryForMap("SELECT * from Lecture WHERE Email = ?", emailLecture);
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
            },emailLecture,slotId);
            if(slotLecture.size()>0) throw new Exception("All ready select this slot");
            List<Map<String, String>> examSlotArr = jdbcTemplate.query("Select id,slot_id,Room_id from Exam_schedule es where lecture_id is null and slot_id = ? ", new ResultSetExtractor<List<Map<String, String>>>() {
                @Override
                public List<Map<String, String>> extractData(ResultSet rs) throws SQLException, DataAccessException {
                    List<Map<String, String>> listRet = new ArrayList<>();
                    while (rs.next()) {
                        Map<String, String> mapRet = new HashMap<>();
                        mapRet.put("id", rs.getString("id"));
                        mapRet.put("slot_id", rs.getString("slot_id"));
                        mapRet.put("Room_id", rs.getString("Room_id"));

                        listRet.add(mapRet);
                    }
                    return listRet;
                }
            },slotId);

            if(examSlotArr!= null) {
                jdbcTemplate.update("update Exam_schedule set lecture_id=? where slot_id=? and Room_id =?", lecture.get("id"), examSlotArr.get(0).get("slot_id"),examSlotArr.get(0).get("Room_id"));
                return ResponseEntity.ok("Success");
            }else {
                return ResponseEntity.ok("FullSlot");
            }
        }
        catch (Exception e){
            return ResponseEntity.ok(e.getMessage());

        }
    }

    @GetMapping("/deleteLecture")
    public ResponseEntity<Object> deleteLectureToExamSchedule(String emailLecture,String slotId,String reason ){

        try {
            Map<String,Object> slot = jdbcTemplate.queryForMap("select Exam_slot.Date from Exam_slot where id =?",slotId);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            // Parse the date string into a LocalDate object using the defined format
            LocalDate insertedDate = LocalDate.parse((CharSequence) slot.get("Date"), formatter);

            // Get today's date
            LocalDate today = LocalDate.now();

            // Compare the dates
            if (insertedDate.isEqual(today) || insertedDate.isBefore(today) || today.isAfter(insertedDate.minusDays(2))) {
                //return ResponseEntity.status(HttpStatus.OK).body("Insert date false");
                return ResponseEntity.ok("Too late to delete");
            }
            Map<String, Object> lecture = jdbcTemplate.queryForMap("SELECT * from Lecture WHERE Email = ?", emailLecture);
            jdbcTemplate.update("insert into Reason(reason, lecturerId, slotId) values (?,?,?)",reason,lecture.get("id"),slotId);
            jdbcTemplate.update("update Exam_schedule set lecture_id = null where lecture_id = ? and slot_id =? ", lecture.get("id"), slotId);
            return ResponseEntity.ok("Success");
        }
        catch (Exception e){
            return ResponseEntity.ok(e.getMessage());
        }
    }


    }
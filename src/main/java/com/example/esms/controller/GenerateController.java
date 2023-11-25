package com.example.esms.controller;

import com.example.esms.entity.Course;
import com.example.esms.entity.ExamSchedule;
import com.example.esms.entity.ExamSlot;
import com.example.esms.entity.Student;
import com.example.esms.entity.courseStudent.CourseStudent;
import com.example.esms.func.Generate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
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
public class GenerateController {
    private final JdbcTemplate jdbcTemplate;
    private Generate generate;

    @Autowired
    public GenerateController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @GetMapping ("/generate")
    public /*ResponseEntity<String>*/ String generate(String dateStart) {


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        // Parse the date string into a LocalDate object using the defined format
        LocalDate insertedDate = LocalDate.parse(dateStart, formatter);

        // Get today's date
        LocalDate today = LocalDate.now();

        // Compare the dates
        if (insertedDate.isEqual(today) || insertedDate.isBefore(today) || today.isAfter(insertedDate.minusDays(4))) {
            //return ResponseEntity.status(HttpStatus.OK).body("Insert date false");
            return ("Insert date false");
        }
        jdbcTemplate.update("delete from Exam_schedule");
        jdbcTemplate.update("delete from Exam_slot");



        generate = new Generate();
        try {
            List<Map<String, String>> courseStudentArr = jdbcTemplate.query("Select Course_id,Student_ID from Course_student", new ResultSetExtractor<List>() {
                @Override
                public List extractData(ResultSet rs) throws SQLException, DataAccessException {
                    List<Map<String, String>> lisRet = new ArrayList<>();
                    while (rs.next()) {
                        Map<String, String> map = new HashMap<>();
                        map.put("Course_id", rs.getString("Course_id"));
                        map.put("Student_id", rs.getString("Student_ID"));
                        lisRet.add(map);
                    }
                    return lisRet;
                }
            });
            List<Map<String, String>> roomArr = jdbcTemplate.query("Select Room from Classroom", new ResultSetExtractor<List>() {
                @Override
                public List extractData(ResultSet rs) throws SQLException, DataAccessException {
                    List<Map<String, String>> lisRet = new ArrayList<>();
                    while (rs.next()) {
                        Map<String, String> map = new HashMap<>();
                        map.put("Room", rs.getString("Room"));
                        lisRet.add(map);
                    }
                    return lisRet;
                }
            });
            assert courseStudentArr != null;

            int totalSlot = courseStudentArr.size() / (roomArr.size()*25);
            if (totalSlot % (roomArr.size() * 25) != 0||totalSlot==0) {
                    totalSlot++;
                }
            ArrayList<ExamSlot> slots = generate.generateSlot(totalSlot, dateStart);
            for (ExamSlot slot :
                    slots) {
                jdbcTemplate.update("insert into Exam_slot(id, Date, Time, Hour) values (?,?,?,?)",slot.getExamSlotId(),slot.getDate(),slot.getTime(),slot.getHour());
            }
            List<Map<String, String>> slotArr = jdbcTemplate.query("Select id from Exam_slot", new ResultSetExtractor<List>() {
                @Override
                public List extractData(ResultSet rs) throws SQLException, DataAccessException {
                    List<Map<String, String>> lisRet = new ArrayList<>();
                    while (rs.next()) {
                        Map<String, String> map = new HashMap<>();
                        map.put("id", rs.getString("id"));
                        lisRet.add(map);
                    }
                    return lisRet;
                }
            });
            List<Map<String, String>> studentArr = jdbcTemplate.query("Select id from Student", new ResultSetExtractor<List>() {
                @Override
                public List extractData(ResultSet rs) throws SQLException, DataAccessException {
                    List<Map<String, String>> lisRet = new ArrayList<>();
                    while (rs.next()) {
                        Map<String, String> map = new HashMap<>();
                        map.put("id", rs.getString("id"));
                        lisRet.add(map);
                    }
                    return lisRet;
                }
            });

            List<Map<String, String>> courseArr = jdbcTemplate.query("Select id from Course", new ResultSetExtractor<List>() {
                @Override
                public List extractData(ResultSet rs) throws SQLException, DataAccessException {
                    List<Map<String, String>> lisRet = new ArrayList<>();
                    while (rs.next()) {
                        Map<String, String> map = new HashMap<>();
                        map.put("id", rs.getString("id"));
                        lisRet.add(map);
                    }
                    return lisRet;
                }
            });
            int indexSlot =0 ;
            int indexStudent =0; //vị trí sinh viên trong MỘT MÔN
            int indexCourse =0; //vị trí môn học
            int totalCourse =courseArr.size();
            int indexExamSche =0;
            Map<String,List<Student>> courseStudentMap = new HashMap<>();
            for (Map<String, String> course :
                    courseArr) {
                List<Student> studentList = new ArrayList<>();
                for (Map<String,String> courseStudent:
                        courseStudentArr) {
                    if(courseStudent.get("Course_id").equals(course.get("id"))){
                        Student  student = new Student();
                        student.setStudentId(courseStudent.get("Student_id"));
                        studentList.add(student);
                    }
                }
                courseStudentMap.put(course.get("id"),studentList);
            }
            int totalStudent =courseStudentMap.get(courseArr.get(indexCourse).get("id")).size();
            int indexChair =0; //vị trí ghế trong MỘT PHÒNG
            int indexRoom =0; //vị trí phong tron MỘT SLOT
            int totalChair = 25;
            int totalRoom = roomArr.size();
            ArrayList<ExamSchedule> examScheduleArrayList = new ArrayList<>();

            List<Student> arrStudent = new ArrayList<>(courseStudentMap.get(courseArr.get(indexCourse).get("id")));
            do {
                indexExamSche++;
                ExamSchedule examSchedule = new ExamSchedule();
                examSchedule.setExamScheduleId(String.valueOf(indexExamSche));
                try {
                    examSchedule.setStudentId(arrStudent.get(indexStudent).getStudentId());
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                indexStudent++;
                examSchedule.setCourseId(courseArr.get(indexCourse).get("id"));
                examSchedule.setRoomId(roomArr.get(indexRoom).get("Room"));
                assert slotArr != null;
                examSchedule.setSlotId(slotArr.get(indexSlot).get("id"));
                examScheduleArrayList.add(examSchedule);
                indexChair++;

                if (indexChair == totalChair) {
                    indexRoom++;
                    indexChair = 0;
                }

                if (indexStudent == totalStudent) {
                    indexRoom++;
                    indexCourse++;
                    indexStudent = 0;
                    if(indexCourse== totalCourse) break;
                    totalStudent = courseStudentMap.get(courseArr.get(indexCourse).get("id")).size();
                    arrStudent = new ArrayList<>(courseStudentMap.get(courseArr.get(indexCourse).get("id")));

                }
                if (indexRoom == totalRoom) {
                    indexSlot++;
                    indexRoom = 0;
                }

            } while (!(indexSlot == totalSlot-1 && indexStudent == totalStudent-1 && indexCourse == totalCourse-1));
            for (ExamSchedule schedule :
                    examScheduleArrayList) {
                jdbcTemplate.update("insert into Exam_schedule(id, Room_id, slot_id, lecture_id, course_id, student_id) values (?,?,?,null,?,?)",schedule.getExamScheduleId(),schedule.getRoomId(),schedule.getSlotId(),schedule.getCourseId(),schedule.getStudentId());
            }
            //return ResponseEntity.status(HttpStatus.OK).body("Success");
            return "Success";
        }
        catch (Exception e){
            throw e;
        }
    }
}

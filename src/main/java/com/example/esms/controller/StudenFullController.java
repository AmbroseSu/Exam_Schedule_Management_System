package com.example.esms.controller;

import com.example.esms.entity.courseStudent.CourseStudent;
import com.example.esms.entity.Student;
import com.example.esms.service.StudentFullService;
import lombok.Getter;
import lombok.Setter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller

public class StudenFullController {
    @Autowired
    private StudentFullService studentFullService;

    /*@Autowired
    private CourseStudentService courseStudentService;*/

    @GetMapping("/upFullStudent")
    public String uploadPage() {
        return "uploadFullStudent"; // This assumes you have an uploadStudent.html in the templates folder
    }

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public StudenFullController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
//
    @PostMapping("/uploadFullStudent")
    public ResponseEntity<String> uploadFile(/*@RequestParam("file")*/ MultipartFile file) {

        jdbcTemplate.update("delete from Exam_schedule");
        jdbcTemplate.update("delete from Exam_slot");
        jdbcTemplate.update("delete from Course_student");
        jdbcTemplate.update("delete from Student");


        try {
            if(jdbcTemplate.queryForList("select * from Course").isEmpty()){
                return ResponseEntity.ok("Course null!Please up load course first!!!");
            }
            Tmp tmp = parseExcelFile(file);
            List<Student> students = tmp.getStudentList();
            List<CourseStudent> courseStudents = tmp.getCourseStudentList();
            studentFullService.saveAll(students);
            studentFullService.saveAll(courseStudents);
            /*List<CourseStudent> courseStudents = parseExcelFileCourseStudent(file);
            courseStudentService.saveAll(courseStudents);*/
            return ResponseEntity.status(HttpStatus.OK).body("uploaded successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload students: " + e.getMessage());
        }
    }
//@PostMapping("/uploadFullStudent")
//public ResponseEntity<String> uploadFile( MultipartFile file) {
//    try {
//        Tmp tmp = parseExcelFile(file);
//        List<Student> students = tmp.getStudentList();
//        List<CourseStudent> courseStudents = tmp.getCourseStudentList();
//        studentFullService.saveAll(students);
//        studentFullService.saveAll(courseStudents);
//            /*List<CourseStudent> courseStudents = parseExcelFileCourseStudent(file);
//            courseStudentService.saveAll(courseStudents);*/
//        return ResponseEntity.status(HttpStatus.OK).body("uploaded successfully!");
//    } catch (Exception e) {
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload students: " + e.getMessage());
//    }
//}
    @Getter
    @Setter
    class Tmp{
        List<Student> studentList;
        List<CourseStudent> courseStudentList;
    }
    private Tmp parseExcelFile(MultipartFile file) throws IOException {
        Tmp tmp = new Tmp();
        List<Student> studentList = new ArrayList<>();
        List<CourseStudent> courseStudentList = new ArrayList<>();
        XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
        XSSFSheet worksheet = workbook.getSheetAt(0);

        for (int index = 0; index < worksheet.getPhysicalNumberOfRows(); index++) {
            if (index > 0) {
                XSSFRow row = worksheet.getRow(index);
                Student student = new Student();
                if(getCellValueAsString(row.getCell(0))==null||getCellValueAsString(row.getCell(0)).equals("")) break;
                student.setStudentId(getCellValueAsString(row.getCell(0)));
                student.setStudentName(getCellValueAsString(row.getCell(1)));
                student.setStudentEmail(getCellValueAsString(row.getCell(2)));
                //student.setStudentTerm(Integer.parseInt(getCellValueAsString(row.getCell(3))));
                String[] courseString = getCellValueAsString(row.getCell(3)).split(",");
                for (String course :
                        courseString) {
                    CourseStudent courseStudent = new CourseStudent();
                    courseStudent.setCourseId(course);
                    courseStudent.setStudentId(student.getStudentId());
                    courseStudentList.add(courseStudent);
                }
                studentList.add(student);
            }
        }

        workbook.close();
        tmp.setStudentList(studentList);
        tmp.setCourseStudentList(courseStudentList);
        return tmp;
    }

    /*private List<CourseStudent> parseExcelFileCourseStudent(MultipartFile file) throws IOException {
        List<CourseStudent> rooms = new ArrayList<>();
        XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
        XSSFSheet worksheet = workbook.getSheetAt(0);

        for (int index = 0; index < worksheet.getPhysicalNumberOfRows(); index++) {
            if (index > 0) {
                XSSFRow row = worksheet.getRow(index);
                CourseStudent room = new CourseStudent();

                room.setStudentId(getCellValueAsString(row.getCell(0)));
                room.setCourseId(getCellValueAsString(row.getCell(3)));

                rooms.add(room);
            }
        }

        workbook.close();
        return rooms;
    }*/


    private String getCellValueAsString(XSSFCell cell) {
        if (cell == null) {
            return "";
        }
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return Integer.toString((int) cell.getNumericCellValue());
            case BOOLEAN:
                return Boolean.toString(cell.getBooleanCellValue());
            default:
                return "";
        }
    }
}

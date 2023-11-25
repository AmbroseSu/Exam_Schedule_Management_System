package com.example.esms.controller;



import com.example.esms.entity.Course;
import com.example.esms.service.CourseService;
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
public class CourseController {
    @Autowired
    private CourseService courseService;

    @GetMapping("/upCourse")
    public String uploadPage() {
        return "uploadCourse"; // This assumes you have an uploadCourse.html in the templates folder
    }

    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public CourseController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostMapping("/uploadCourse")
    public /*@ResponseBody*/ ResponseEntity<String> uploadFile(/*@RequestParam("file")*/ MultipartFile file) {

        jdbcTemplate.update("delete from Exam_schedule");
        jdbcTemplate.update("delete from Exam_slot");
        jdbcTemplate.update("delete from Course_student");
        jdbcTemplate.update("delete from Course");

        try {
            List<Course> courses = parseExcelFile(file);
            courseService.saveAll(courses);
            return ResponseEntity.status(HttpStatus.OK).body("Course uploaded successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload students: " + e.getMessage());
        }
    }

    private List<Course> parseExcelFile(MultipartFile file) throws IOException {
        List<Course> courses = new ArrayList<>();
        XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
        XSSFSheet worksheet = workbook.getSheetAt(0);

        for (int index = 0; index < worksheet.getPhysicalNumberOfRows(); index++) {
            if (index > 0) {
                XSSFRow row = worksheet.getRow(index);
                Course course = new Course();

                course.setCourseId(getCellValueAsString(row.getCell(0)));
                course.setCourseName(getCellValueAsString(row.getCell(1)));
                course.setCourseHour(Float.parseFloat(getCellValueAsString(row.getCell(2))));
                //student.setStudentTerm(Integer.parseInt(getCellValueAsString(row.getCell(3))));

                courses.add(course);
            }
        }

        workbook.close();
        return courses;
    }

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


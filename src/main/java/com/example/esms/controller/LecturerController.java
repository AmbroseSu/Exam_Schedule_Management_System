package com.example.esms.controller;


import com.example.esms.entity.Lecturer;
import com.example.esms.service.LecturerService;
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
public class LecturerController {
    @Autowired
    private LecturerService lecturerService;

    @GetMapping("/upLecturer")
    public String uploadPage() {
        return "uploadLecturer"; // This assumes you have an uploadStudent.html in the templates folder
    }

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public LecturerController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostMapping("/uploadLecturer")
    public /*@ResponseBody*/ ResponseEntity<String> uploadFile(/*@RequestParam("file")*/ MultipartFile file) {

        jdbcTemplate.update("delete from Exam_schedule");
        jdbcTemplate.update("delete from Exam_slot");
        jdbcTemplate.update("delete from Lecture");

        try {
            if(jdbcTemplate.queryForList("select * from Course").isEmpty()){
                return ResponseEntity.ok("Course null!Please up load course first!!!");
            }
            List<Lecturer> lecturers = parseExcelFile(file);
            lecturerService.saveAll(lecturers);
            return ResponseEntity.status(HttpStatus.OK).body("Lecturer uploaded successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload lecturer: " + e.getMessage());
        }
    }

    private List<Lecturer> parseExcelFile(MultipartFile file) throws IOException {
        List<Lecturer> lecturers = new ArrayList<>();
        XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
        XSSFSheet worksheet = workbook.getSheetAt(0);

        for (int index = 0; index < worksheet.getPhysicalNumberOfRows(); index++) {
            if (index > 0) {
                XSSFRow row = worksheet.getRow(index);
                Lecturer lecturer = new Lecturer();

                lecturer.setLecturerId(getCellValueAsString(row.getCell(0)));
                lecturer.setLecturerName(getCellValueAsString(row.getCell(1)));
                lecturer.setLecturerEmail(getCellValueAsString(row.getCell(2)));
                lecturer.setLecturerPhone(getCellValueAsString(row.getCell(3)));

                lecturers.add(lecturer);
            }
        }

        workbook.close();
        return lecturers;
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

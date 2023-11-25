package com.example.esms.controller;

import com.example.esms.entity.courseStudent.CourseStudent;
import com.example.esms.service.CourseStudentService;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
@Controller
public class CourseStudentController {
    @Autowired
    private CourseStudentService courseStudentService;

    @GetMapping("/upCourseStudent")
    public String uploadPage() {
        return "uploadCourseStudent"; // This assumes you have an uploadStudent.html in the templates folder
    }

    @PostMapping("/uploadCourseStudent")
    public @ResponseBody ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            List<CourseStudent> courseStudents = parseExcelFile(file);
            courseStudentService.saveAll(courseStudents);
            return ResponseEntity.status(HttpStatus.OK).body("Room uploaded successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload room: " + e.getMessage());
        }
    }

    private List<CourseStudent> parseExcelFile(MultipartFile file) throws IOException {
        List<CourseStudent> rooms = new ArrayList<>();
        XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
        XSSFSheet worksheet = workbook.getSheetAt(0);

        for (int index = 0; index < worksheet.getPhysicalNumberOfRows(); index++) {
            if (index > 0) {
                XSSFRow row = worksheet.getRow(index);
                CourseStudent room = new CourseStudent();

                room.setStudentId(getCellValueAsString(row.getCell(1)));
                room.setCourseId(getCellValueAsString(row.getCell(0)));

                rooms.add(room);
            }
        }

        workbook.close();
        return rooms;
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

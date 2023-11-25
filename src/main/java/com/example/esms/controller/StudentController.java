package com.example.esms.controller;


import com.example.esms.service.StudentService;
import com.example.esms.entity.Student;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
public class StudentController {

    @Autowired
    private StudentService studentService;

    /*@Autowired
    private CourseStudentService courseStudentService;*/

    @GetMapping("/upStudent")
    public String uploadPage() {
        return "uploadStudent"; // This assumes you have an uploadStudent.html in the templates folder
    }

    @PostMapping("/uploadStudent")
    public @ResponseBody ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            List<Student> students = parseExcelFile(file);
            studentService.saveAll(students);
            /*List<CourseStudent> courseStudents = parseExcelFileCourseStudent(file);
            courseStudentService.saveAll(courseStudents);*/
            return ResponseEntity.status(HttpStatus.OK).body("uploaded successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload students: " + e.getMessage());
        }
    }

    private List<Student> parseExcelFile(MultipartFile file) throws IOException {
        List<Student> students = new ArrayList<>();
        XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
        XSSFSheet worksheet = workbook.getSheetAt(0);

        for (int index = 0; index < worksheet.getPhysicalNumberOfRows(); index++) {
            if (index > 0) {
                XSSFRow row = worksheet.getRow(index);
                Student student = new Student();

                student.setStudentId(getCellValueAsString(row.getCell(0)));
                student.setStudentName(getCellValueAsString(row.getCell(1)));
                student.setStudentEmail(getCellValueAsString(row.getCell(2)));
                //student.setStudentTerm(Integer.parseInt(getCellValueAsString(row.getCell(3))));

                students.add(student);
            }
        }

        workbook.close();
        return students;
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

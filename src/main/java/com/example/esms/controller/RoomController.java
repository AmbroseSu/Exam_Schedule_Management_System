package com.example.esms.controller;


import com.example.esms.entity.Room;
import com.example.esms.service.RoomService;
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
public class RoomController {
    @Autowired
    private RoomService roomService;

    @GetMapping("/upRoom")
    public String uploadPage() {
        return "uploadRoom"; // This assumes you have an uploadStudent.html in the templates folder
    }

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public RoomController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostMapping("/uploadRoom")
    public /*@ResponseBody*/ ResponseEntity<String> uploadFile(/*@RequestParam("file")*/ MultipartFile file) {

        jdbcTemplate.update("delete from Exam_schedule");
        jdbcTemplate.update("delete from Exam_slot");
        jdbcTemplate.update("delete from Classroom");

        try {
            if(jdbcTemplate.queryForList("select * from Course").isEmpty()){
                return ResponseEntity.ok("Course null!Please up load course first!!!");
            }
            List<Room> rooms = parseExcelFile(file);
            roomService.saveAll(rooms);
            return ResponseEntity.status(HttpStatus.OK).body("Room uploaded successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload room: " + e.getMessage());
        }
    }

    private List<Room> parseExcelFile(MultipartFile file) throws IOException {
        List<Room> rooms = new ArrayList<>();
        XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
        XSSFSheet worksheet = workbook.getSheetAt(0);

        for (int index = 0; index < worksheet.getPhysicalNumberOfRows(); index++) {
            if (index > 0) {
                XSSFRow row = worksheet.getRow(index);
                Room room = new Room();

                room.setRoomId(getCellValueAsString(row.getCell(0)));


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

package com.example.esms.func;

import lombok.Getter;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class exportData {
    public static final int COLUMN_INDEX_ID         = 0;
    public static final int COLUMN_INDEX_MSSV      = 1;
    public static final int COLUMN_INDEX_STUDENTNAME      = 2;
    public static final int COLUMN_INDEX_LECTURERNAME   = 4;
    public static final int COLUMN_INDEX_LECTURERID   = 3;
    private static CellStyle cellStyleFormatNumber = null;
    private String path;
    public exportData(String date,String path,JdbcTemplate jdbcTemplate){
        this.date = date;
        this.path = path;
        this.jdbcTemplate = jdbcTemplate;
    }
    private String date;
    private JdbcTemplate jdbcTemplate;
    private final String sqlSelectAll = "select MSSV = s.id,studentName = s.name,lecturerId = l.id,lecturerName = l.Name from Exam_schedule es " +
            "inner join Exam_slot slot on es.slot_id = slot.id " +
            "inner join Student s on s.id = es.student_id " +
            "left join Lecture l on es.lecture_id = l.id " +
            "where slot.Date = ? and slot.Time = ? and es.Room_id = ?";
    private final String sqlSelectRoom = "select distinct es.Room_id from Exam_schedule es " +
            "inner join Exam_slot slot on es.slot_id = slot.id " +
            "where slot.Date = ? and slot.Time = ?" +
            "order by es.Room_id";
    private final String sqlSelectTime = "select distinct slot.Time from Exam_schedule es " +
            "inner join Exam_slot slot on es.slot_id = slot.id " +
            "where slot.Date = ? order by slot.Time";
    private List<Map<String, Object>> getRoom(String time){
        List<Map<String, Object>> dataRoom =null;
        try {
            dataRoom = jdbcTemplate.queryForList(
                    sqlSelectRoom, date,time);
        }
        catch (Exception e){
            return null;
        }
         return dataRoom;
    }

    private List<Map<String, Object>> getTime(){
        List<Map<String, Object>> dataTime = null;
        try {
            dataTime = jdbcTemplate.queryForList(sqlSelectTime,date);
        }
        catch (Exception e){
            return null;
        }
        return dataTime;
    }

    private List<Map<String, Object>> getData(String room,String time){
        List<Map<String,Object>> data = null;
        try{
            data = jdbcTemplate.queryForList(sqlSelectAll,date,time,room);
        }
        catch (Exception e){
            return null;
        }
        return data;
    }

    private void createOutputFile(Workbook workbook, String dirExcelPath,String excelFilePath) throws IOException {
        File theDir = new File(dirExcelPath);
        if (!theDir.exists()){
            theDir.mkdirs();
        }

        try (OutputStream os = new FileOutputStream(dirExcelPath+"\\"+excelFilePath)) {
            workbook.write(os);
        }
    }
    private void createWorkBook(String time,String dirExcelPat) throws IOException {
        String path = dirExcelPat;
        String excelPath = switch (time) {
            case "7h-9h15" -> "slot1.xlsx";
            case "9h30-11h45" -> "slot2.xlsx";
            case "12h30-14h45" -> "slot3.xlsx";
            case "15h-17h15" -> "slot4.xlsx";
            default -> null;
        };
        path = path+excelPath;
        Workbook workbook = new XSSFWorkbook();
        List<Map<String,Object>> listRoom = getRoom(time);
        assert listRoom != null;
        for (Map<String, Object> room:
        listRoom){
            addSheet(workbook,(String) room.get("Room_id"),time);
        }
        createOutputFile(workbook,dirExcelPat,excelPath);
    }

    private void addSheet(Workbook workbook,String room,String time){
        Sheet sheet = workbook.createSheet(room);
        int rowIndex = 0;
        writeHeader(sheet, rowIndex);
        List<Map<String,Object>> datasRaw = getData(room,time);
        List<dataTMP> dataTMPList = new ArrayList<>();
        for(Map<String, Object> data : datasRaw){
            dataTMPList.add(new dataTMP(data));
        }
        int stt =0;
        for(dataTMP data : dataTMPList){
            rowIndex++;
            stt++;
            Row row = sheet.createRow(rowIndex);
            writeBook(data, row,stt);
        }
        autosizeColumn(sheet,COLUMN_INDEX_LECTURERNAME);
    }
    private static void writeHeader(Sheet sheet, int rowIndex) {
        // create CellStyle
        CellStyle cellStyle = createStyleForHeader(sheet);

        // Create row
        Row row = sheet.createRow(rowIndex);

        // Create cells
        Cell cell = row.createCell(COLUMN_INDEX_ID);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("STT");

        cell = row.createCell(COLUMN_INDEX_MSSV);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("MSSV");

        cell = row.createCell(COLUMN_INDEX_STUDENTNAME);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Student name");

        cell = row.createCell(COLUMN_INDEX_LECTURERNAME);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Lecturer name");
        cell = row.createCell(COLUMN_INDEX_LECTURERID);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Lecturer Id");

    }
    private static CellStyle createStyleForHeader(Sheet sheet) {
        // Create font
        Font font = sheet.getWorkbook().createFont();
        font.setFontName("Times New Roman");
        CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        cellStyle.setFont(font);
        return cellStyle;
    }

    private static void writeBook(dataTMP data, Row row,int stt) {
        if (cellStyleFormatNumber == null) {
            short format = (short)BuiltinFormats.getBuiltinFormat("#,##0");
            Workbook workbook = row.getSheet().getWorkbook();
            cellStyleFormatNumber = workbook.createCellStyle();
            cellStyleFormatNumber.setDataFormat(format);
        }

        Cell cell = row.createCell(COLUMN_INDEX_ID);
        cell.setCellValue(stt);

        cell = row.createCell(COLUMN_INDEX_MSSV);
        cell.setCellValue(data.getMSSV());

        cell = row.createCell(COLUMN_INDEX_STUDENTNAME);
        cell.setCellValue(data.getStudentName());

        cell = row.createCell(COLUMN_INDEX_LECTURERID);
        cell.setCellValue(data.getLecturerId());

        cell = row.createCell(COLUMN_INDEX_LECTURERNAME);
        cell.setCellValue(data.getLecturerName());


    }
    @Getter
    private class dataTMP{

        public dataTMP(Map<String, Object> data){
            studentName = (String) data.get("studentName");
            MSSV = (String) data.get("MSSV");
            lecturerId = (String) data.get("lecturerId");
            lecturerName = (String) data.get("lecturerName");
        }
        private String MSSV;
        private String studentName;
        private String lecturerName;
        private String lecturerId;
    }
    public void export(String dirExcelPath) throws IOException {
        String path = dirExcelPath+"\\"+date.replaceAll("/","");
        List<Map<String,Object>> listTime = getTime();
        assert listTime != null;
        for(Map<String,Object> time : listTime){
            createWorkBook((String) time.get("Time"),path);
        }
        ZipDirectory zipDirectory = new ZipDirectory();
        zipDirectory.zipDirectory(new File(path),new File(path+".zip"));
        FileUtils.deleteDirectory(new File(path));
    }

    private static void autosizeColumn(Sheet sheet, int lastColumn) {
        for (int columnIndex = 0; columnIndex < lastColumn; columnIndex++) {
            sheet.autoSizeColumn(columnIndex);
        }
    }
}

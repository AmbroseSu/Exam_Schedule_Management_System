package com.example.esms.controller;

import com.example.esms.func.MediaTypeUtils;
import com.example.esms.func.exportData;
import jakarta.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.*;

@Controller
@RequestMapping("")

public class DownloadController {
    private JdbcTemplate jdbcTemplate;
    @Autowired
    public ServletContext servletContext;
    @Autowired
    public DownloadController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @RequestMapping("/download")
//    @RequestParam(defaultValue)
    public ResponseEntity<InputStreamResource> download(String date) throws IOException {
        String fileName = date.replaceAll("_","/");
        exportData exportDataFu = new exportData(fileName,"Data",jdbcTemplate);
        exportDataFu.export("Data");
        MediaType mediaType = MediaTypeUtils.getMediaTypeForFileName(this.servletContext,fileName);
        File file = new File("Data\\"+fileName.replaceAll("/","")+".zip");
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        return ResponseEntity.ok()
                .header(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS,HttpHeaders.CONTENT_DISPOSITION)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getName())
                .contentType(mediaType)
                .contentLength(file.length()) //
                .body(resource);
    }
}

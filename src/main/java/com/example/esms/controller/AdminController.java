package com.example.esms.controller;



import com.example.esms.dto.LoginDTO;
import com.example.esms.response.LoginResponse;
import com.example.esms.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/login")
public class AdminController {
    @Autowired
    private AdminService adminService;
    @PostMapping(path = "/admin")
    public ResponseEntity<?> loginEmployee(@RequestBody LoginDTO loginDTO)
    {
        LoginResponse loginResponse = adminService.loginAdmin(loginDTO);
        return ResponseEntity.ok(loginResponse);
    }
}

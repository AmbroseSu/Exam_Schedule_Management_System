package com.example.esms.service;


import com.example.esms.dto.LoginDTO;
import com.example.esms.response.LoginResponse;

public interface AdminService {
    LoginResponse loginAdmin(LoginDTO loginDTO);
}

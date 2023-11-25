package com.example.esms.service.impl;


import com.example.esms.dto.LoginDTO;
import com.example.esms.entity.Admin;
import com.example.esms.repository.AdminRepository;
import com.example.esms.response.LoginResponse;
import com.example.esms.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public LoginResponse loginAdmin(LoginDTO loginDTO) {
        String msg = "";
        Admin employee1 = adminRepository.findByUsername(loginDTO.getUsername());
        if (employee1 != null) {
            String password = loginDTO.getPassword();
            String encodedPassword = employee1.getPassword();
            Boolean isPwdRight = passwordEncoder.matches(password, encodedPassword);
            if (isPwdRight) {
                Optional<Admin> employee = adminRepository.findOneByUsernameAndPassword(loginDTO.getUsername(), encodedPassword);
                if (employee.isPresent()) {
                    return new LoginResponse("Login Success", true);
                } else {
                    return new LoginResponse("Login Failed", false);
                }
            } else {
                return new LoginResponse("Password Not Match", false);
            }
        }else {
            return new LoginResponse("Username not exits", false);
        }
    }
}

package com.example.esms.repository;


import com.example.esms.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin,Integer> {

    Optional<Admin> findOneByUsernameAndPassword(String username, String password);
    Admin findByUsername(String email);
}

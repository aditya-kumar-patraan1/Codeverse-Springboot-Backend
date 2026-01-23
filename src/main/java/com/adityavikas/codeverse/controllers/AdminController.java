package com.adityavikas.codeverse.controllers;

import com.adityavikas.codeverse.entity.User;
import com.adityavikas.codeverse.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    // only one admin can create other admin not user is permitted to call this you can try it by yourself
    @PostMapping("/create-admin")
    public ResponseEntity<?> createAdmin(@RequestBody User admin){
        try{
            admin.setRoles(List.of("ADMIN","USER"));   //giving both provisions
            boolean isAdminCreated = userService.saveUserWithBcryptPassword(admin);
            if(isAdminCreated){
                return new ResponseEntity<>("Admin created successfully...", HttpStatus.OK);
            }
            return new ResponseEntity<>("Admin not created", HttpStatus.BAD_GATEWAY);
        }
        catch(Exception e){
            return new ResponseEntity<>("Admin not created", HttpStatus.BAD_GATEWAY);
        }
    }
}

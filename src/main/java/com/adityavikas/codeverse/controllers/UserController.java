package com.adityavikas.codeverse.controllers;

import com.adityavikas.codeverse.entity.User;
import com.adityavikas.codeverse.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@Component
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/isWorking")
    public String isWorking(){
        return "Yes It is working";
    }

}

package com.adityavikas.codeverse.controllers;

import com.adityavikas.codeverse.entity.User;
import com.adityavikas.codeverse.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserService userService;

    @GetMapping("/health-check")
    public String checkHealh(){
        return "OK";
    }

    @PostMapping("/register")
    public ResponseEntity<?> saveUser(@RequestBody User user){
        try{
            boolean isSaved = userService.saveUserWithBcryptPassword(user);
            if(isSaved) {
                return new ResponseEntity<>("User Registered successfully...", HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>("User not Registered", HttpStatus.BAD_REQUEST);
            }
        }
        catch(Exception e){
            return new ResponseEntity<>("Error : User not Registered", HttpStatus.BAD_REQUEST);
        }
    }

}

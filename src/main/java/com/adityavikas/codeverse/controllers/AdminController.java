package com.adityavikas.codeverse.controllers;

import com.adityavikas.codeverse.dto.AdminDTO;
import com.adityavikas.codeverse.dto.AllUserAPIResponseDTO;
import com.adityavikas.codeverse.entity.Contest;
import com.adityavikas.codeverse.entity.User;
import com.adityavikas.codeverse.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@RestController
@RequestMapping("/admin")
@Tag(name="All Admin API's",description = "this is the admin controller where only admin can do the neccessary change to our codeverse system application not used is allowed.")
public class AdminController {

    @Autowired
    private UserService userService;

    @Operation(summary = "this is used to create admin (Note: only a admin can create other admin)")
    // only one admin can create other admin not user is permitted to call this you can try it by yourself
    @PostMapping("/create-admin")
    public ResponseEntity<?> createAdmin(@RequestBody AdminDTO adminDTO){
        try{
            User admin = new User();
            admin.setEmail(adminDTO.getEmail());
            admin.setUsername(adminDTO.getUsername());
            admin.setPassword(adminDTO.getPassword());
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

    @GetMapping("/fetchUsers")
    public ResponseEntity<?> fetchAllUsers(){
        try{
            List<User> allUsers = userService.getAllUsers();
            List<AllUserAPIResponseDTO> response = new ArrayList<>();
            for(User user : allUsers){
                LocalDateTime dt = user.getCreated_at();
                String month = dt.getDayOfMonth() + " " + dt.getMonth().toString() + ", " + dt.getYear();
                boolean isAdmin = user.getRoles().stream().anyMatch(u -> "ADMIN".equalsIgnoreCase(u));
                response.add(new AllUserAPIResponseDTO(user.getUsername(), user.getEmail(), isAdmin,month));
            }
            if(allUsers.isEmpty()){
                return new ResponseEntity<>(null,HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(response,HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/create-contest")
    public ResponseEntity<?> createContest(@RequestBody Contest contest){

    }

}

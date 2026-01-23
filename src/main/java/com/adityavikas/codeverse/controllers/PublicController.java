package com.adityavikas.codeverse.controllers;

import com.adityavikas.codeverse.config.JwtConfig;
import com.adityavikas.codeverse.entity.User;
import com.adityavikas.codeverse.services.UserDetailsServiceImpl;
import com.adityavikas.codeverse.services.UserService;
import com.adityavikas.codeverse.utils.JwtUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/public")
@Tag(name = "All Public API's",description = "This is the public controller used to check health of API connection,Registering and login user")
public class PublicController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtUtils jwtUtils;

    @Operation(summary = "To check API health")
    @GetMapping("/health-check")
    public String checkHealh(){
        return "OK";
    }

    @Operation(summary = "to register user to codeverse")
    @PostMapping("/register")
    public ResponseEntity<?> saveUser(@RequestBody User user){
        try{
            user.setRoles(List.of("USER"));
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

    @Operation(summary = "to login user to codeverse")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user){
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword())
            );
            UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
            String jwt = jwtUtils.generateToken(userDetails.getUsername());
            return new ResponseEntity<>(jwt,HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>("Username or password not correct",HttpStatus.BAD_REQUEST);
        }
    }



}

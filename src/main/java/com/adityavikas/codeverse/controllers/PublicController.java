package com.adityavikas.codeverse.controllers;

import com.adityavikas.codeverse.config.JwtConfig;
import com.adityavikas.codeverse.entity.User;
import com.adityavikas.codeverse.services.UserDetailsServiceImpl;
import com.adityavikas.codeverse.services.UserService;
import com.adityavikas.codeverse.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtUtils jwtUtils;

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

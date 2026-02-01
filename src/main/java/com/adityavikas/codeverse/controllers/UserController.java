package com.adityavikas.codeverse.controllers;

import com.adityavikas.codeverse.dto.APIResponseDTO;
import com.adityavikas.codeverse.entity.User;
import com.adityavikas.codeverse.repository.UserRepository;
import com.adityavikas.codeverse.services.UserService;
import com.adityavikas.codeverse.utils.JwtUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@Component
@RestController
@Tag(name = "All User API's",description = "This is the user controller used to execute all user functionalities after authenticating through" +
        "the JWT token getting by successful login")
@RequestMapping("/user")
public class UserController {


    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Operation(summary = "to check working based on authentication token")
    @GetMapping("/isWorking")
    public ResponseEntity<?> isWorking(){
        return ResponseEntity.ok("It is working !");
    }

    @Operation(summary = "this is used to retrieve the current user details using jwt token")
    @GetMapping("/currentUser")
    public ResponseEntity<?> getCurrentUser(HttpServletRequest httpServletRequest){
        try{
            String authorizationHeader = httpServletRequest.getHeader("authorization");
            User user = null;

            if(authorizationHeader!=null && authorizationHeader.startsWith("Bearer ")){
                String jwt = authorizationHeader.substring(7);
                String username = jwtUtils.extractUsername(jwt);
                user = userRepository.findByUsername(username);
            }else{
                throw new Exception("Invalid Jwt token");
            }

            APIResponseDTO apiResponse = new APIResponseDTO(user.getUsername(),user.getEmail(),user.getRoles());

            return new ResponseEntity<>(apiResponse,HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>("jwt token is incorrect",HttpStatus.BAD_REQUEST);
        }
    }


}

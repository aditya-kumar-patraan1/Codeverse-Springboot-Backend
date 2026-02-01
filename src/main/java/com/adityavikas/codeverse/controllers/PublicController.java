package com.adityavikas.codeverse.controllers;
import com.adityavikas.codeverse.dto.LoginUserDTO;
import com.adityavikas.codeverse.dto.UserDTO;
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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public ResponseEntity<?> checkHealth(){
        return ResponseEntity.ok(List.of("Hey !","It's","Working"));
    }

    @Operation(summary = "to register user to codeverse")
    @PostMapping("/register")
    public ResponseEntity<?> saveUser(@RequestBody LoginUserDTO userDTO){
        Map<String,Integer> returnStatus = new HashMap<>();
        returnStatus.put("status",0);
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setEmail(userDTO.getEmail());
        try{
            user.setRoles(List.of("USER"));
            boolean isSaved = userService.saveUserWithBcryptPassword(user);

            if(isSaved) {
                returnStatus.put("status",1);
                return new ResponseEntity<>(returnStatus, HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>(returnStatus, HttpStatus.BAD_REQUEST);
            }
        }
        catch(Exception e){
            return new ResponseEntity<>(returnStatus, HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "to login user to codeverse")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDTO userDTO){
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        Map<String,Object> returnResponse = new HashMap<>();
        returnResponse.put("jwtToken","");
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword())
            );
            UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
            String jwt = jwtUtils.generateToken(userDetails.getUsername());
            returnResponse.put("jwtToken",jwt);
            returnResponse.put("status",1);
            return new ResponseEntity<>(returnResponse,HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(returnResponse,HttpStatus.BAD_REQUEST);
        }
    }


}

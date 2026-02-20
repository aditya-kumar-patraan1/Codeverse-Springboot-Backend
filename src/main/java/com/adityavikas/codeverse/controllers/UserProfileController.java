package com.adityavikas.codeverse.controllers;

import com.adityavikas.codeverse.entity.User;
import com.adityavikas.codeverse.entity.UserProfile;
import com.adityavikas.codeverse.middleware.Middlewares;
import com.adityavikas.codeverse.services.UserProfileService;
import com.adityavikas.codeverse.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Tag(name = "All user Profile API's",description = "This controller is used to get all userProfile API's")
@RestController
@RequestMapping("/userProfile")
public class UserProfileController {

    @Autowired
    private UserProfileService userProfileService;

    @Autowired
    private Middlewares middlewares;

    @Autowired
    private UserService userService;

    @Operation(summary = "This API is used to retrieve the User Profile")
    @GetMapping("/get")
    public ResponseEntity<?> getUserProfile(HttpServletRequest request){
        Map<String,Object> returnResponse = new HashMap<>();
        returnResponse.put("status",0);
        returnResponse.put("data",null);
        try{
            String authorizationHeader = request.getHeader("Authorization");
            String username = middlewares.getUserByJwt(authorizationHeader).getUsername();
            UserProfile userProfile = userProfileService.getUserProfile(username);
            if(userProfile!=null){
                returnResponse.put("data",userProfile);
                returnResponse.put("status",1);
                return new ResponseEntity<>(returnResponse, HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>(returnResponse, HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    @PostMapping("/update")
    @Operation(summary = "This API endpoint is used to update the user profile")
    public ResponseEntity<?> updateUserProfile(HttpServletRequest request,@RequestBody UserProfile userProfile){
        Map<String,Object> returnResponse = new HashMap<>();
        returnResponse.put("status",0);
        try{
            String userId = middlewares.getUserIdByJwt(request.getHeader("Authorization"));
            String username = userService.getUserById(userId).getUsername();
            UserProfile oldUser = userProfileService.getUserProfile(username);
            boolean isUpdatedUserProfile = userProfileService.updateUserProfile(oldUser,userProfile);
            // update the username also in registration details in codeverse_users
            boolean isUpdatedUser = userService.updateUser(userId, userProfile.getUsername());
            if(isUpdatedUser){
                returnResponse.put("status",1);
                return new ResponseEntity<>(returnResponse,HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>(returnResponse,HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}

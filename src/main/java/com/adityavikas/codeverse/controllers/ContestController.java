package com.adityavikas.codeverse.controllers;

import com.adityavikas.codeverse.entity.Contest;
import com.adityavikas.codeverse.entity.User;
import com.adityavikas.codeverse.repository.ContestRepository;
import com.adityavikas.codeverse.services.ContestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/contest")
@Tag(name="Contest API Controller (accessed right to admin only)",description = "This contains all the endpoints to handle add,delete,edit options")
public class ContestController {

    @Autowired
    private ContestRepository contestRepository;

    @Autowired
    private ContestService contestService;

    @Operation(summary = "This API endpoint is used to register into contest by users")
    @PostMapping("/register/{contestName}")
    public ResponseEntity<?> registerContest(HttpServletRequest request,@PathVariable String contestName){
        try{
            String authorizationHeader = request.getHeader("authorization");
            User user = contestService.registerInContest(contestName, authorizationHeader);
            return new ResponseEntity<>(user,HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



}

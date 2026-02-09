package com.adityavikas.codeverse.controllers;

import com.adityavikas.codeverse.entity.Contest;
import com.adityavikas.codeverse.repository.ContestRepository;
import com.adityavikas.codeverse.services.ContestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/contest")
@Tag(name="Contest API Controller (accessed right to admin only)",description = "This contains all the endpoints to handle add,delete,edit options")
public class ContestController {

    @Autowired
    private ContestService contestService;


    @Operation(summary = "This endpoint is used to add contest")
    @PostMapping("/createContest")
    public ResponseEntity<?> addNewContest(@RequestBody Contest contest){
        Map<String,Integer> response = new HashMap<>();
        response.put("status",0);
        try{
            boolean isAdded = contestService.addContest(contest);
            if(isAdded){
                response.put("status",1);
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}

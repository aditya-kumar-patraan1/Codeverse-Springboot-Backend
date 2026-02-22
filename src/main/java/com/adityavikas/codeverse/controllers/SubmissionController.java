package com.adityavikas.codeverse.controllers;

import com.adityavikas.codeverse.entity.Submission;
import com.adityavikas.codeverse.services.SubmissionService;
import io.swagger.v3.oas.annotations.Operation;
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
@RequestMapping("/submission")
public class SubmissionController {

    @Autowired
    private SubmissionService submissionService;

    @PostMapping("/create")
    @Operation(summary = "This API endpoint is used to create & Store the Submission")
    public ResponseEntity<?> createSubmission(@RequestBody Submission submission){
        boolean isSubmissionStored = submissionService.createSubmission(submission);
        Map<String,Integer> returnResponse = new HashMap<>();
        returnResponse.put("status",0);
        if(isSubmissionStored){
            returnResponse.put("status",1);
            return new ResponseEntity<>(returnResponse, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(returnResponse,HttpStatus.BAD_REQUEST);
        }
    }



}

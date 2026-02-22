package com.adityavikas.codeverse.controllers;

import com.adityavikas.codeverse.entity.Submission;
import com.adityavikas.codeverse.services.SubmissionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/submission")
@Tag(name="Submission Controller",description = "This Controller is used to handle all operations related to submissions like creation retrievals etc.")
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

    @Operation(summary = "This API endpoint is used to fetch all the submission of user for all the problems")
    @GetMapping("/getAll/{username}")
    public ResponseEntity<?> fetchAllSubmissions(@PathVariable String username){
        List<Submission> allSubmissions = submissionService.getAllSubmissionOfUser(username);
        if(!allSubmissions.isEmpty()){
            return new ResponseEntity<>(allSubmissions,HttpStatus.OK);
        }
        return new ResponseEntity<>(null,HttpStatus.NO_CONTENT);
    }



}

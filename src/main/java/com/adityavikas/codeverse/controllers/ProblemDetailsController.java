package com.adityavikas.codeverse.controllers;

import com.adityavikas.codeverse.entity.ProblemDetails;
import com.adityavikas.codeverse.services.ProblemDetailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/problemDetails")
@Tag(name="Problem Details Controller",description = "This is the Problem Details API Controller accessed by Admin only to create problem detail,access,update or delete it")
public class ProblemDetailsController {

    @Autowired
    private ProblemDetailService problemDetailService;

    @PostMapping("/add/{problemId}")
    @Operation(summary = "This API Endpoint is used to add the Details about the Problem")
    public ResponseEntity<?> problemDetailsAdded(@RequestBody ProblemDetails problemDetails, @PathVariable ObjectId problemId){
        problemDetails.setProblemId(problemId);
        boolean isSaved = problemDetailService.problemDetailsAdded(problemDetails);
        Map<String,Integer> returnResponse = new HashMap<>();
        returnResponse.put("status",0);
        if(isSaved){
            returnResponse.put("status",1);
            return new ResponseEntity<>(returnResponse,HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(returnResponse, HttpStatus.BAD_REQUEST);
        }
    }

}

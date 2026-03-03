package com.adityavikas.codeverse.controllers;

import com.adityavikas.codeverse.entity.Testcase;
import com.adityavikas.codeverse.services.TestcaseService;
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
@RequestMapping("/testcase")
@Tag(name="Testcase Controller",description = "This controller is accessed by admin only to access,create,add,update or delete the testcase")
public class TestcaseController {

    @Autowired
    private TestcaseService testcaseService;

    @PostMapping("/add/{problemId}")
    @Operation(summary = "This API endpoint is used to add the testcase by Admin")
    public ResponseEntity<?> addTestcase(@RequestBody Testcase testcase, @PathVariable String problemId){
        Map<String,Integer> returnResponse = new HashMap<>();
        boolean isAdded = testcaseService.addTestcase(testcase,problemId);
        returnResponse.put("status",0);
        if(isAdded){
            returnResponse.put("status",1);
            return new ResponseEntity<>(returnResponse, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(returnResponse,HttpStatus.BAD_REQUEST);
        }
    }
}

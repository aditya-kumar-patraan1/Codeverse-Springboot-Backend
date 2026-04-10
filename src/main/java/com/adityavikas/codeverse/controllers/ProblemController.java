package com.adityavikas.codeverse.controllers;

import com.adityavikas.codeverse.dto.ProblemDTO;
import com.adityavikas.codeverse.dto.TestcaseDTO;
import com.adityavikas.codeverse.entity.Problem;
import com.adityavikas.codeverse.entity.ProblemDetails;
import com.adityavikas.codeverse.entity.Testcase;
import com.adityavikas.codeverse.services.ProblemDetailService;
import com.adityavikas.codeverse.services.ProblemService;
import com.adityavikas.codeverse.services.TestcaseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Stream;

@RestController
@RequestMapping("/problem")
@Tag(name="All Problem's API",description = "This is the API controller used to add delete or update the problem and it is accessible by admin only")
public class ProblemController {

    @Autowired
    private ProblemService problemService;

    @Operation(summary = "This is used to add problem by admin")
    @PostMapping("/add")
    public ResponseEntity<?> addProblem(@RequestBody ProblemDTO problemDTO){
        Map<String,Integer> returnResponse = new HashMap<>();
        returnResponse.put("status",0);
        try{
            boolean isProblemAdded = problemService.addEntireProblem(problemDTO);
            if(isProblemAdded){
                returnResponse.put("status",1);
                return new ResponseEntity<>(returnResponse,HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>(returnResponse  , HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(returnResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "This API Endpoint is used to delete the entire problem(including the testcase,solution,editorial,description/details)")
    @DeleteMapping("/deleteEntireProblem/{problemId}")
    public ResponseEntity<?> deleteProblem(@PathVariable String problemId){
        boolean isDeleted = problemService.deleteCompleteProblem(problemId);
        Map<String,Integer> returnResponse = new HashMap<>();
        returnResponse.put("status",0);
        if(isDeleted){
            returnResponse.put("status",1);
            return new ResponseEntity<>(returnResponse,HttpStatus.OK);
        }
        return new ResponseEntity<>(returnResponse,HttpStatus.BAD_REQUEST);
    }

}

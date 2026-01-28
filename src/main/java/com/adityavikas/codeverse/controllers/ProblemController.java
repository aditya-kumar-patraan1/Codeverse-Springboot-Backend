package com.adityavikas.codeverse.controllers;

import com.adityavikas.codeverse.entity.Problem;
import com.adityavikas.codeverse.services.ProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/problem")
public class ProblemController {

    @Autowired
    private ProblemService problemService;

    @PostMapping("/add")
    public ResponseEntity<?> addProblem(@RequestBody Problem problem){
        try{
            Boolean isSaved = problemService.saveProblem(problem);
            if(isSaved){
                return new ResponseEntity<>("Problem added",HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>("Problem not added", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Problem not added", HttpStatus.BAD_REQUEST);
        }
    }

}

package com.adityavikas.codeverse.controllers;

import com.adityavikas.codeverse.dto.ExecuteRequest;
import com.adityavikas.codeverse.utils.CodeExecutionUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/CodeExecution")
@Tag(name = "Jdoodle Code Run Controller",description = "This is the Code Execution API Controller that is used to handle all code related tasks for code-verse Platform")
public class CodeExecutionController {

    @Autowired
    private CodeExecutionUtils codeExecutionUtils;

    @PostMapping("/runCode")
    @Operation(summary = "This is the run code API Endpoint used to run the code")
    public ResponseEntity<?> runUserCode(@RequestBody ExecuteRequest executeRequest){
        return ResponseEntity.ok(codeExecutionUtils.runJdoodleCode(executeRequest));
    }

}

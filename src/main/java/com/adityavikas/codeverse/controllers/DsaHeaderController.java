package com.adityavikas.codeverse.controllers;

import com.adityavikas.codeverse.entity.DsaHeader;
import com.adityavikas.codeverse.services.DsaHeaderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@Tag(name="DSA Header Controller",description = "This Controller is used to handle all the Operations related to the DSA Header")
@RequestMapping("/dsaHeader")
public class DsaHeaderController {

    private static final Logger logger = LoggerFactory.getLogger(DsaHeaderController.class);

    private final DsaHeaderService dsaHeaderService;

    public DsaHeaderController(DsaHeaderService dsaHeaderService){
        this.dsaHeaderService = dsaHeaderService;
    }

    @Operation(summary = "This is the API Endpoint used to add the DSA Header")
    @PostMapping("/add")
    public ResponseEntity<?> addDSAHeader(@RequestBody DsaHeader dsaHeader){
        Map<String,Integer> returnResponse = new HashMap<>();
        returnResponse.put("status",0);
        boolean isDSAHeaderSaved = dsaHeaderService.addDsaHeader(dsaHeader);
        if(isDSAHeaderSaved){
            returnResponse.put("status",1);
            return new ResponseEntity<>(returnResponse, HttpStatus.OK);
        }
        return new ResponseEntity<>(returnResponse, HttpStatus.OK);

    }

}

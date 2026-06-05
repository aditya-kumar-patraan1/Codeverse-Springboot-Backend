package com.adityavikas.codeverse.controllers;

import com.adityavikas.codeverse.entity.DsaHeader;
import com.adityavikas.codeverse.entity.DsaTemplate;
import com.adityavikas.codeverse.entity.DsaTitle;
import com.adityavikas.codeverse.repository.DsaTitleRepositoryImpl;
import com.adityavikas.codeverse.services.DsaHeaderService;
import com.adityavikas.codeverse.services.DsaTemplateService;
import com.adityavikas.codeverse.services.DsaTitleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Tag(name="DSA Header Controller",description = "This Controller is used to handle all the Operations related to the DSA Header")
@RequestMapping("/dsaHeader")
public class DsaHeaderController {

    private static final Logger logger = LoggerFactory.getLogger(DsaHeaderController.class);

    private final DsaHeaderService dsaHeaderService;
    private final DsaTitleRepositoryImpl dsaTitleRepositoryImpl;
    private final DsaTemplateService dsaTemplateService;

    public DsaHeaderController(DsaHeaderService dsaHeaderService,DsaTitleRepositoryImpl dsaTitleRepositoryImpl,DsaTemplateService dsaTemplateService){
        this.dsaHeaderService = dsaHeaderService;
        this.dsaTitleRepositoryImpl = dsaTitleRepositoryImpl;
        this.dsaTemplateService = dsaTemplateService;
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

    @DeleteMapping("/delete/{categoryId}")
    public ResponseEntity<?> deleteDSAHeader(@PathVariable String categoryId){
        Map<String,Integer> returnResponse = new HashMap<>();

        returnResponse.put("status",0);

        boolean isCategoryDeleted = dsaHeaderService.deleteEntireDSACategory(categoryId);

        if(isCategoryDeleted){
            returnResponse.put("status",1);
            return ResponseEntity.ok(returnResponse);
        }

        return new ResponseEntity<>(returnResponse,HttpStatus.NOT_FOUND);


    }

}

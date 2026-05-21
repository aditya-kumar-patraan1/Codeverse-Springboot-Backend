package com.adityavikas.codeverse.controllers;

import com.adityavikas.codeverse.entity.DsaTitle;
import com.adityavikas.codeverse.services.DsaTitleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/DsaTitle")
@Tag(name = "DSA Title Controller",description = "This Controller is used to handle all the Operations related to DSA Title of Codeverse Website")
public class DsaTitleController {

    @Autowired
    private DsaTitleService dsaTitleService;

    @PostMapping("/addTitle/{categoryId}")
    @Operation(summary = "This endpoint is used to add the DSA Title by EDITOR of Codeverse")

    public ResponseEntity<?> addDsaTitle(@PathVariable String categoryId, @RequestBody DsaTitle dsaTitle){
        dsaTitle.setCategoryId(categoryId);
        boolean isTemplateAdded = dsaTitleService.addDsaTitle(dsaTitle);
        Map<String,Integer> returnResponse = new HashMap<>();
        returnResponse.put("status",0);

        if(isTemplateAdded){
            returnResponse.put("status",1);
            return new ResponseEntity<>(returnResponse,HttpStatus.OK);
        }

        return new ResponseEntity<>(returnResponse,HttpStatus.OK);
    }

}

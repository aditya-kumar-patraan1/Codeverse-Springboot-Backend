package com.adityavikas.codeverse.controllers;

import com.adityavikas.codeverse.entity.DsaTitle;
import com.adityavikas.codeverse.services.DsaTitleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@RequestMapping("/DsaTitle")
@Tag(name = "This Controller is used to handle all the Operations related to DSA Title of Codeverse Website")
public class DsaTitleController {

    @Autowired
    private DsaTitleService dsaTitleService;

    @PostMapping("/addTitle")
    @Operation(summary = "This endpoint is used to add the DSA Title by EDITOR of Codeverse")

    public ResponseEntity<?> addDsaTitle(@RequestBody DsaTitle dsaTitle){
        boolean isTemplateAdded = dsaTitleService.addDsaTitle(dsaTitle);

        Map<String,Integer> returnResponse = new HashMap<>();
        returnResponse.put("status",0);

        if(isTemplateAdded){
            returnResponse.put("status",1);
            return new ResponseEntity<>(returnResponse,HttpStatus.OK);
        }

        return new ResponseEntity<>(returnResponse,HttpStatus.BAD_REQUEST);
    }

}

package com.adityavikas.codeverse.controllers;

import com.adityavikas.codeverse.entity.DsaTemplate;
import com.adityavikas.codeverse.services.DsaTemplateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/DsaTemplateController")
@Tag(name="Dsa Template API Controller",description = "This is the controller added for handling the operation related to DsaTemplate")
public class DsaTemplateController {

    @Autowired
    private DsaTemplateService dsaTemplateService;

    @PostMapping("/addTemplate/{parentTemplateId}")
    @Operation(summary = "This endpoint is used to add the DSA Template by EDITOR of Codeverse")
    public ResponseEntity<?> addDsaTemplate(@PathVariable String parentTemplateId, @RequestBody DsaTemplate dsaTemplate){

        dsaTemplate.setParentTemplateId(parentTemplateId);

        boolean isTemplateAdded = dsaTemplateService.addDsaTemplate(dsaTemplate);
        Map<String,Integer> returnResponse = new HashMap<>();
        returnResponse.put("status",0);
        if(isTemplateAdded){
            returnResponse.put("status",1);
            return new ResponseEntity<>(returnResponse,HttpStatus.OK);
        }

        return new ResponseEntity<>(returnResponse,HttpStatus.BAD_REQUEST);
    }

}

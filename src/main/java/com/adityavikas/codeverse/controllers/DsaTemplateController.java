package com.adityavikas.codeverse.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/DsaTemplateController")
@Tag(name="Dsa Template API Controller",description = "This is the controller added for handling the operation related to DsaTemplate")
public class DsaTemplateController {

    @PostMapping("/addTemplate/{title}")
    public ResponseEntity<?> addDsaTemplate(@PathVariable String title){
        return new ResponseEntity<>("title is : "+title,HttpStatus.OK);
    }

}

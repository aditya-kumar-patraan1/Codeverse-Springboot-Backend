package com.adityavikas.codeverse.controllers;

import com.adityavikas.codeverse.entity.Trick;
import com.adityavikas.codeverse.services.TrickService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/trick")
public class TrickController {

    @Autowired
    private TrickService trickService;


    @PutMapping("/update")
    public ResponseEntity<?> updateNote(@RequestBody Trick updatedNote){
        Map<String,Integer> returnResponse = new HashMap<>();
        boolean isNotesUpdated = trickService.updateTrick(updatedNote.getNote());
        returnResponse.put("status",0);
        if(isNotesUpdated){
            returnResponse.put("status",1);
        }
        return ResponseEntity.ok(returnResponse);
    }

}

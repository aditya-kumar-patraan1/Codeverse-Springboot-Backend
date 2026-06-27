package com.adityavikas.codeverse.controllers;

import com.adityavikas.codeverse.entity.Trick;
import com.adityavikas.codeverse.services.TrickService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/trick")
@Tag(name="Trick Controller",description = "This Controller is used to add/update or clear the trick that are repeatedly used in DSA/CP.")
public class TrickController {

    @Autowired
    private TrickService trickService;


    @PutMapping("/update")
    @Operation(summary = "This API Ednpoint is used to add/update/clear the trick note")
    public ResponseEntity<?> updateNote(@RequestBody Trick updatedNote){
        Map<String,Integer> returnResponse = new HashMap<>();
        boolean isNotesUpdated = trickService.updateTrick(updatedNote.getNote());
        returnResponse.put("status",0);
        if(isNotesUpdated){
            returnResponse.put("status",1);
        }
        return ResponseEntity.ok(returnResponse);
    }

    @GetMapping("/get")
    @Operation(summary = "This API Endpoint is used to access the trick note")
    public ResponseEntity<?> getNote(){
        Trick note = trickService.getNote();
        Map<String,Object> returnResponse = new HashMap<>();
        returnResponse.put("html","");
        returnResponse.put("savedAt",null);
        if(note!=null){
            returnResponse.put("html",note.getNote());
            returnResponse.put("savedAt",note.getUpdated_at());
        }
        return ResponseEntity.ok(returnResponse);
    }

}

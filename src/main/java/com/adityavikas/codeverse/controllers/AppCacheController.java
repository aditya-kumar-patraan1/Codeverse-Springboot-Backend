package com.adityavikas.codeverse.controllers;

import com.adityavikas.codeverse.entity.Cache;
import com.adityavikas.codeverse.services.AppCacheService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/appCache")
@Tag(name="App Cache controller (Admin access only)",description = "API endpoints used to handle cache")
public class AppCacheController {

    private final AppCacheService appCacheService;

    public AppCacheController(AppCacheService appCacheService){
        this.appCacheService = appCacheService;
    }

    @PostMapping("/add")
    @Operation(summary = "This API Endpoint is used to add Api-Cache Data")
    public ResponseEntity<?> addApiCache(@RequestBody Cache cache){
        boolean isApiCacheAdded = appCacheService.addApiData(cache);
        Map<String,Integer> returnResponse = new HashMap<>();
        returnResponse.put("status",0);

        if(!isApiCacheAdded){
            return new ResponseEntity<>(returnResponse, HttpStatus.OK);
        }

        returnResponse.put("status",1);
        return new ResponseEntity<>(returnResponse,HttpStatus.OK);
    }

    @GetMapping("/getAll")
    @Operation(summary = "This API Endpoint is used to access all Api-Cache Data")
    public ResponseEntity<List<Cache>> getAllApiData(){
        List<Cache> allApiData = appCacheService.getAllApiData();
        if(allApiData.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(allApiData,HttpStatus.OK);
    }



}

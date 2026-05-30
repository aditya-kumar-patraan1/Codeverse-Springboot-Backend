package com.adityavikas.codeverse.controllers;

import com.adityavikas.codeverse.entity.DsaTemplate;
import com.adityavikas.codeverse.entity.DsaTitle;
import com.adityavikas.codeverse.repository.DsaTemplateRepositoryImpl;
import com.adityavikas.codeverse.repository.DsaTitleRepository;
import com.adityavikas.codeverse.services.DsaTemplateService;
import com.adityavikas.codeverse.services.DsaTitleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/DsaTemplate")
@Tag(name="Dsa Template API Controller",description = "This is the controller added for handling the operation related to DsaTemplate")
public class DsaTemplateController {

    @Autowired
    private DsaTemplateService dsaTemplateService;

    @Autowired
    private DsaTitleService dsaTitleService;

    @Autowired
    private DsaTitleRepository dsaTitleRepository;

    private final DsaTemplateRepositoryImpl dsaTemplateRepositoryImpl;

    public DsaTemplateController(DsaTemplateRepositoryImpl dsaTemplateRepositoryImpl){
        this.dsaTemplateRepositoryImpl = dsaTemplateRepositoryImpl;
    }

    @PostMapping("/addTemplate/{parentId}")
    @Operation(summary = "This endpoint is used to add the DSA Template by EDITOR of Codeverse")
    public ResponseEntity<?> addDsaTemplate(@PathVariable String parentId, @RequestBody DsaTemplate dsaTemplate){

        dsaTemplate.setParentId(parentId);
        ObjectId templateId = dsaTemplateService.addDsaTemplate(dsaTemplate);
        Map<String,Integer> returnResponse = new HashMap<>();
        returnResponse.put("status",0);

        if(templateId!=null){

            DsaTitle dsaTitle = dsaTitleService.findByTitleId(parentId);
            dsaTitle.getListOfTemplateIds().add(templateId);
            dsaTitleRepository.save(dsaTitle);
            returnResponse.put("status",1);
            return new ResponseEntity<>(returnResponse,HttpStatus.OK);
        }

        return new ResponseEntity<>(returnResponse,HttpStatus.OK);
    }

    @PutMapping("/updateTemplate/{templateId}")
    public ResponseEntity<?> updateDsaTemplate(@PathVariable String templateId,@RequestBody DsaTemplate dsaTemplate){
        Map<String,Integer> returnResponse = new HashMap<>();
        returnResponse.put("status",0);

        boolean isUpdated = dsaTemplateRepositoryImpl.updateDSAContent(templateId,dsaTemplate);

        if(isUpdated){
            returnResponse.put("status",1);
            return new ResponseEntity<>(returnResponse,HttpStatus.OK);
        }

        return new ResponseEntity<>(returnResponse,HttpStatus.OK);
    }

}

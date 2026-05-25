package com.adityavikas.codeverse.services;

import com.adityavikas.codeverse.entity.DsaTemplate;
import com.adityavikas.codeverse.repository.DsaTemplateRepository;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DsaTemplateService {

    @Autowired
    private DsaTemplateRepository dsaTemplateRepository;

    private static final Logger logger = LoggerFactory.getLogger(DsaTemplateService.class);

    public ObjectId addDsaTemplate(DsaTemplate dsaTemplate){
        try{
            return dsaTemplateRepository.save(dsaTemplate).getId();
        }
        catch (Exception e){
            logger.error("Template not added");
            return null;
        }
    }

    public List<DsaTemplate> getTemplates(){
        try{
            return dsaTemplateRepository.findAll();
        } catch (Exception e) {
            logger.error("Templates are empty");
            return null;
        }
    }

}

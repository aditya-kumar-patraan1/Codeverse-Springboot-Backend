package com.adityavikas.codeverse.services;

import com.adityavikas.codeverse.entity.DsaTemplate;
import com.adityavikas.codeverse.repository.DsaTemplateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DsaTemplateService {

    @Autowired
    private DsaTemplateRepository dsaTemplateRepository;

    private static final Logger logger = LoggerFactory.getLogger(DsaTemplateService.class);

    public boolean addDsaTemplate(DsaTemplate dsaTemplate){
        try{
            dsaTemplateRepository.save(dsaTemplate);
            return true;
        }
        catch (Exception e){
            logger.error("Template not added");
            return false;
        }
    }

}

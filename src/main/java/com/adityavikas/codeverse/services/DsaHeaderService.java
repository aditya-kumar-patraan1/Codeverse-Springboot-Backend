package com.adityavikas.codeverse.services;

import com.adityavikas.codeverse.entity.DsaHeader;
import com.adityavikas.codeverse.repository.DsaHeaderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class DsaHeaderService {

    private static final Logger logger = LoggerFactory.getLogger(DsaHeaderService.class);

    private final DsaHeaderRepository dsaHeaderRepository;

    public DsaHeaderService(DsaHeaderRepository dsaHeaderRepository){
        this.dsaHeaderRepository = dsaHeaderRepository;
    }

    public boolean addDsaHeader(@RequestBody DsaHeader dsaHeader){
        try{
            dsaHeaderRepository.save(dsaHeader);
            return true;
        } catch (Exception e) {
            logger.error("DSA Header not added");
            return false;
        }
    }

    public DsaHeader findDsaHeaderByHeaderId(String categoryId){
        try{
            return dsaHeaderRepository.findByHeaderId(categoryId);
        }
        catch(Exception e){
            logger.error("No DsaHeader find by HeaderId");
            return null;
        }
    }

}

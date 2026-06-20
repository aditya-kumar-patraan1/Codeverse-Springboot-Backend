package com.adityavikas.codeverse.services;

import com.adityavikas.codeverse.entity.Cache;
import com.adityavikas.codeverse.repository.AppCacheRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class AppCacheService {
    
    private final AppCacheRepository appCacheRepository;
    private final Logger logger = LoggerFactory.getLogger(AppCacheService.class);  
    
    public AppCacheService(AppCacheRepository appCacheRepository){
        this.appCacheRepository = appCacheRepository;
    }
    
    public List<Cache> getAllApiData(){
        return appCacheRepository.findAll();
    }
    
    public boolean addApiData(Cache cache){
        try{
            appCacheRepository.save(cache);
            return true;
        } catch (Exception e) {
            logger.error("App Cache data not added");
            return false;
        }
    }
    
}

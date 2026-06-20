package com.adityavikas.codeverse.cache;

import com.adityavikas.codeverse.entity.Cache;
import com.adityavikas.codeverse.services.AppCacheService;
import jakarta.annotation.PostConstruct;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppCache {

    public enum Keys{
        JDOODLE_API_URL;
    }

    private final AppCacheService appCacheService;
    public Map<String,String> apiCache = new HashMap<>();

    public AppCache(AppCacheService appCacheService){
        this.appCacheService = appCacheService;
    }

    public void reload(){
        init();
    }

    @PostConstruct
    public void init(){
        apiCache.clear();
        List<Cache> allApiData = appCacheService.getAllApiData();
        for(Cache cache : allApiData){
            apiCache.put(cache.getKey(), cache.getValue());
        }
    }

}

package com.adityavikas.codeverse.repository;

import com.adityavikas.codeverse.entity.DsaTitle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class DsaTitleRepositoryImpl {

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<DsaTitle> getAllTitleByCategoryId(String categoryId){
        try{
            Query query = new Query();
            query.addCriteria(Criteria.where("categoryId").is(categoryId));
            return mongoTemplate.find(query, DsaTitle.class);
        } catch (Exception e) {
            log.error("No title found");
            return null;
        }
    }

}

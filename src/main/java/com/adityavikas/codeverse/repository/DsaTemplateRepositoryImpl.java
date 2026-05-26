package com.adityavikas.codeverse.repository;

import com.adityavikas.codeverse.entity.DsaTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

@Slf4j
public class DsaTemplateRepositoryImpl {

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<DsaTemplate> getDsaTemplatesByParentId(String parentId){
        try{
            Query query = new Query();
            query.addCriteria(Criteria.where("parentId").is(parentId));
            return mongoTemplate.find(query,DsaTemplate.class);
        } catch (Exception e) {
            log.error("DSA Template are not present here !");
            return null;
        }
    }

}

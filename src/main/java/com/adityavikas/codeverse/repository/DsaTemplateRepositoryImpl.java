package com.adityavikas.codeverse.repository;

import com.adityavikas.codeverse.entity.DsaTemplate;
import com.mongodb.client.result.UpdateResult;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

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

    public boolean updateDSAContent(ObjectId mongoObjectId, DsaTemplate dsaTemplate){
        try{

            Query query = new Query();
            query.addCriteria(Criteria.where("id").is(mongoObjectId));

            Update update = new Update();
            update.set("title",dsaTemplate.getTitle());
            update.set("templateId",dsaTemplate.getTemplateId());
            update.set("problemLinks",dsaTemplate.getProblemLinks());
            update.set("videoLinks",dsaTemplate.getVideoLinks());
            update.set("cpp",dsaTemplate.getCpp());
            update.set("java",dsaTemplate.getJava());
            update.set("python",dsaTemplate.getPython());
            update.set("javascript",dsaTemplate.getJavascript());

            UpdateResult updateResult = mongoTemplate.updateFirst(query, update, DsaTemplate.class);

            return updateResult.getModifiedCount()>0;
        } catch (Exception e) {
            log.error("DSA Template are not present here !");
            return false;
        }
    }

}

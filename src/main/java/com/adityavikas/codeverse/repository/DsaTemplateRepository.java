package com.adityavikas.codeverse.repository;

import com.adityavikas.codeverse.entity.DsaTemplate;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DsaTemplateRepository extends MongoRepository<DsaTemplate, ObjectId> {
}

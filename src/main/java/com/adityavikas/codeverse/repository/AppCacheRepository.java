package com.adityavikas.codeverse.repository;

import com.adityavikas.codeverse.entity.Cache;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AppCacheRepository extends MongoRepository<Cache, ObjectId> {
}

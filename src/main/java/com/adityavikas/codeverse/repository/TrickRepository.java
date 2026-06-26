package com.adityavikas.codeverse.repository;

import com.adityavikas.codeverse.entity.Tricks;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TrickRepository extends MongoRepository<Tricks, ObjectId> {
}

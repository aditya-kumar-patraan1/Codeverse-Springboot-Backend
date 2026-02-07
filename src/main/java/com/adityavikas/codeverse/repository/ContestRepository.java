package com.adityavikas.codeverse.repository;

import com.adityavikas.codeverse.entity.Contest;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ContestRepository extends MongoRepository<Contest, ObjectId> {



}

package com.adityavikas.codeverse.repository;

import com.adityavikas.codeverse.entity.Problem;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProblemRepository extends MongoRepository<Problem, ObjectId> {
}

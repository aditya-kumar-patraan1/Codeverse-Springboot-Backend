package com.adityavikas.codeverse.repository;

import com.adityavikas.codeverse.entity.ProblemDetails;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProblemDetailRepository extends MongoRepository<ProblemDetails,ObjectId> {
}

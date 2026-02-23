package com.adityavikas.codeverse.repository;

import com.adityavikas.codeverse.entity.Testcase;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TestcaseRepository extends MongoRepository<Testcase, ObjectId> {
}

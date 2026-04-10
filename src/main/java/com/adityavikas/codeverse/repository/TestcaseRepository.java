package com.adityavikas.codeverse.repository;

import com.adityavikas.codeverse.entity.Testcase;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TestcaseRepository extends MongoRepository<Testcase, ObjectId> {

    List<Testcase> findAllByProblemId(ObjectId problemId);
    void deleteByProblemId(ObjectId problemId);

}

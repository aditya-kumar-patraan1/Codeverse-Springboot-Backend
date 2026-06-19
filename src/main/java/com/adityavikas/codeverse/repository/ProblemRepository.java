package com.adityavikas.codeverse.repository;

import com.adityavikas.codeverse.entity.Problem;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ProblemRepository extends MongoRepository<Problem, ObjectId> {

    Problem findBySlug(String slug);

    List<Problem> findByContestId(ObjectId contestId);

}

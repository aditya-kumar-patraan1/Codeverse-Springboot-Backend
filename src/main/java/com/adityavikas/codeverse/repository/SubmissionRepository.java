package com.adityavikas.codeverse.repository;

import com.adityavikas.codeverse.entity.Submission;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SubmissionRepository extends MongoRepository<Submission, ObjectId> {



}

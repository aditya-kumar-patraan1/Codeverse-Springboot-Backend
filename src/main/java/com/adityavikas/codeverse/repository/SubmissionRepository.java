package com.adityavikas.codeverse.repository;

import com.adityavikas.codeverse.entity.Submission;
import com.adityavikas.codeverse.services.SubmissionService;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SubmissionRepository extends MongoRepository<Submission, ObjectId> {

    Submission findByslug(String slug);

}

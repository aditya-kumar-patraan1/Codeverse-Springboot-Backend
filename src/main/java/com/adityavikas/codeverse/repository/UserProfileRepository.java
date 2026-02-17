package com.adityavikas.codeverse.repository;

import com.adityavikas.codeverse.entity.UserProfile;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserProfileRepository extends MongoRepository<UserProfile, ObjectId> {

    UserProfile findByUsername(String Username);

}

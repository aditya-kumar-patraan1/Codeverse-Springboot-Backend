package com.adityavikas.codeverse.repository;

import com.adityavikas.codeverse.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, ObjectId> {

     User findByUsername(String username);

}

package com.adityavikas.codeverse.repository;
import com.adityavikas.codeverse.entity.DsaHeader;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DsaHeaderRepository extends MongoRepository<DsaHeader, ObjectId> {

    DsaHeader findByHeaderId(String headerId);
    void deleteByHeaderId(String headerId);

}

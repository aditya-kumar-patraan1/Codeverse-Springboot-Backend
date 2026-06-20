package com.adityavikas.codeverse.entity;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "ConfigCodeverseApp")
public class Cache {

    @Id
    private ObjectId id;
    private String key;
    private String value;

}

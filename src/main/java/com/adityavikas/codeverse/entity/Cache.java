package com.adityavikas.codeverse.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "ConfigCodeverseApp")
public class Cache {

    @Id
    @JsonSerialize(using= ToStringSerializer.class)
    private ObjectId id;
    private String key;
    private String value;

}

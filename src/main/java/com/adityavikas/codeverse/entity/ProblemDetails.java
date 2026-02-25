package com.adityavikas.codeverse.entity;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;
import java.util.Map;

@Data
@Document(collection = "ProblemDetails")
public class ProblemDetails {

    @Id
    private ObjectId id;
    private ObjectId problemId;
    private String description = "";
    private String editorial = "";
    private Map<String,String> templates = new HashMap<>();
    private Map<String,String> solutions = new HashMap<>();



}

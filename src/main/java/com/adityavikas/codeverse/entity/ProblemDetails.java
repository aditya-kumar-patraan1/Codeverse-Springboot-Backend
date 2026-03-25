package com.adityavikas.codeverse.entity;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    // newly added
    private Map<String,Double> timeComplexity = new HashMap<>();
    private Map<String,Double> spaceComplexity = new HashMap<>();
    List<String> algorithmSteps = new ArrayList<>();
}

package com.adityavikas.codeverse.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
    private Map<String,String> timeComplexity = new HashMap<>();
    private Map<String,String> spaceComplexity = new HashMap<>();
    List<String> algorithmSteps = new ArrayList<>();
}
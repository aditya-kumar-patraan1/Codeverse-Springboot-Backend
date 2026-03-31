package com.adityavikas.codeverse.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



@Data
public class ProblemDTO {

    //to be store in problem entity
    @Indexed(unique = true)
    private int sno;   // I will change it later on
    @NotNull
    private String title;
    private String slug;
    String topicTags;
    private boolean status = false;    // be default = false
    private String difficulty;
    private int acceptanceRate = 0;       // depend on system I will change it to double later on
    private String inputType;
    private String returnType;
    private String functionName;

    // to be store in testcase entity
    List<TestcaseDTO> testCases = new ArrayList<>();

    // to be store in problem detail
    private String description = "";
    private String editorial = "";
    private Map<String,String> templates = new HashMap<>();
    private Map<String,String> solutions = new HashMap<>();
    // newly added
    private Map<String,String> timeComplexity = new HashMap<>();
    private Map<String,String> spaceComplexity = new HashMap<>();
    List<String> algorithmSteps = new ArrayList<>();
}

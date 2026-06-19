package com.adityavikas.codeverse.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class ContestProblemDTO {

    private int sno;

    @NotNull
    private String title;
    private String slug;
    String topicTags;
    private String difficulty;
    private double acceptanceRate = 0;
    private String inputType;
    private String returnType;
    private String functionName;
    private int problemOrder;
    private String description = "";
    private String editorial = "";
    private Map<String, String> templates = new HashMap<>();
    private Map<String, String> solutions = new HashMap<>();
    private Map<String, String> timeComplexity = new HashMap<>();
    private Map<String, String> spaceComplexity = new HashMap<>();
    private List<String> algorithmSteps = new ArrayList<>();
    private List<TestcaseDTO> testCases = new ArrayList<>();
}

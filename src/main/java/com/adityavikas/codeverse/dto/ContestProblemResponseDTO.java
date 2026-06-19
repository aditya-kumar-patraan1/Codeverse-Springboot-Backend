package com.adityavikas.codeverse.dto;

import com.adityavikas.codeverse.entity.Testcase;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class ContestProblemResponseDTO {
    private String problemId;
    private String title;
    private String slug;
    private int problemOrder;
    private String difficulty;
    private String description;
    private Map<String,String> templates = new HashMap<>();
    private List<Testcase> testcases = new ArrayList<>();
}

package com.adityavikas.codeverse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class DSAContentDTO {

    private String title;
    private String description;
    private Map<String,SpecificAlgoCollection> topics = new HashMap<>();

    @Data
    public static class SpecificAlgoCollection{
        private String title;
        private String description;
        private String difficulty;
        Map<String,SpecificTemplate> codeTemplates = new HashMap<>();
    }

    @Data
    public static class SpecificTemplate{
        private String title;
        private List<String> videoLinks = new ArrayList<>();
        private List<String> problemLinks = new ArrayList<>();
        private String cpp;
        private String java;
        private String python;
        private String javascript;
    }

}

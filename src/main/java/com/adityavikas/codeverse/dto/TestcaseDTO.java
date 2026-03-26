package com.adityavikas.codeverse.dto;

import lombok.Data;

@Data
public class TestcaseDTO{
    private boolean isHidden;    //is hidden testcase exists true/false
    private String input;
    private String output;
    private String explanation = "";
}

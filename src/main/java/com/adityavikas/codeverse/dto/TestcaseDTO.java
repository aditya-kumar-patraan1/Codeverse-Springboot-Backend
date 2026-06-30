package com.adityavikas.codeverse.dto;
import lombok.Data;
import org.bson.types.ObjectId;

@Data
public class TestcaseDTO{
    private String id;  //unique string type there
    private boolean isHidden;    //is hidden testcase exists true/false
    private String input;
    private String output;
    private String explanation = "";
}

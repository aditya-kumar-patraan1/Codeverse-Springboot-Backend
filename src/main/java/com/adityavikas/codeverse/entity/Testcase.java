package com.adityavikas.codeverse.entity;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "testcase")
public class Testcase {

    @Id
    private ObjectId id;
    private ObjectId problemId;
    private String testcaseId;
    private boolean isHidden;    //is hidden testcase exists true/false
    private String input;
    private String output;


}

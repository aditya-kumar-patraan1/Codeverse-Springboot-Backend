package com.adityavikas.codeverse.entity;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "DsaTemplate")
public class DsaTemplate {


    private String templateName;
    private ObjectId titleId;
    private String templateId;
    private List<String> practiceProblemLinks = new ArrayList<>();
    private List<String> youtubeLinks = new ArrayList<>();
    private boolean status;
    private String cpp;
    private String java;
    private String js;
    private String python;

}

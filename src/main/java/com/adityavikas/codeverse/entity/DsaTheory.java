package com.adityavikas.codeverse.entity;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "DsaTheory")
public class DsaTheory {

    private ObjectId dsaSubtitleId;
    private String definition;
    private String analogy;
    private List<String> coreOperations = new ArrayList<>();
    private String spaceComplexity;
    private String timeComplexity;




}

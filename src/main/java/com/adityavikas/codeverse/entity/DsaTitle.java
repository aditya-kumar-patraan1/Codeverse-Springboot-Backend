package com.adityavikas.codeverse.entity;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "DsaTitle")
public class DsaTitle {

    @Indexed(unique = true)
    private String titleId;   // basically slug
    private String title;
    private String difficulty;
    private String description;
    private List<ObjectId> listOfTemplateIds = new ArrayList<>();

}

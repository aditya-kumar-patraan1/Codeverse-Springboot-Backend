package com.adityavikas.codeverse.entity;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "DsaTitle")
public class DsaTitle {

    private String title;
    private List<ObjectId> listOfIds = new ArrayList<>();

}

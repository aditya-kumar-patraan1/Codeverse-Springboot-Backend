package com.adityavikas.codeverse.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("DsaTitle")
public class DsaTitle {

    private String title;
    private String listOfIds;

}

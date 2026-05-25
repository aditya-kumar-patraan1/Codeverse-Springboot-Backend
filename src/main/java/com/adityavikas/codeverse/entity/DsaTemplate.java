package com.adityavikas.codeverse.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "DsaTemplate")
public class DsaTemplate {

    private String parentId = "";
    private String title;

    @Id
    @JsonSerialize(using=ToStringSerializer.class)
    private ObjectId id;

    @Indexed(unique = true)
    private String templateId;
    private List<String> problemLinks = new ArrayList<>();
    private List<String> videoLinks = new ArrayList<>();
    private boolean status = false;
    private String cpp;
    private String java;
    private String javascript;
    private String python;

}

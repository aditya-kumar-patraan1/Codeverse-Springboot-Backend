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
@Document(collection = "DsaTitle")
public class DsaTitle {

    @Id
    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId id;
    private String categoryId;
    @Indexed(unique = true)
    private String titleId;   // basically slug
    private String title;
    private String difficulty;
    private String description;

    @JsonSerialize(contentUsing=ToStringSerializer.class)
    private List<ObjectId> listOfTemplateIds = new ArrayList<>();

}

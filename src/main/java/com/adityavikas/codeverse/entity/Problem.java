package com.adityavikas.codeverse.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.StringSerializer;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "problem")
@Data
public class Problem {

    @Id
    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId id;   //mongo unique id assigned auto by atlas

    @Indexed(unique = true)
    private int sno;
    @NotNull
    private String title;
    private String slug;
    List<String> topicTags = new ArrayList<>();
    private boolean status;
    private String difficulty;
    private int acceptanceRate;
    private String inputType;
    private String returnType;
    private String functionName;
    private LocalDateTime created_at;

}

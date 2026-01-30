package com.adityavikas.codeverse.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class ProblemDTO {

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

}

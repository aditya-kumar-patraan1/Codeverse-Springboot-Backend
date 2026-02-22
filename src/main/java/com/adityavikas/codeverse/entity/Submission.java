package com.adityavikas.codeverse.entity;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "Submission")
public class Submission {

    @Id
    private ObjectId submissionId;

    @Indexed(unique = true)
    private String username;

    private String problemId;

    private String userCode;
    private String language;
    private LocalDateTime submittedAt;
    private String status;   //ACCEPTED,TLE,WRONG ANSWER
    private String time;
    private String memory;
    @NotNull
    private String slug;

}

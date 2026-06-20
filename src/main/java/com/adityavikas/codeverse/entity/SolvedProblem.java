package com.adityavikas.codeverse.entity;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "SolvedProblem")
public class SolvedProblem {
    private ObjectId problemId;
    private LocalDateTime solvedAt;
    private int score;
}

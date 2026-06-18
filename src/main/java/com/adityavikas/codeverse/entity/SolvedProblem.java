package com.adityavikas.codeverse.entity;

import lombok.Data;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;

@Data
public class SolvedProblem {
    private ObjectId problemId;
    private LocalDateTime solvedAt;
    private int score;
}

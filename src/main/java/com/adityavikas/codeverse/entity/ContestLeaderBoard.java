package com.adityavikas.codeverse.entity;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "ContestLeaderBoard")
public class ContestLeaderBoard {
    private ObjectId contestId;
    private ObjectId userId;
    private List<SolvedProblem> solvedProblems = new ArrayList<>();
    private int totalScore;
    private LocalDateTime totalTime;
}

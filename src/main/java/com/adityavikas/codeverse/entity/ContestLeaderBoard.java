package com.adityavikas.codeverse.entity;

import lombok.Data;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class ContestLeaderBoard {
    private ObjectId contestId;
    private ObjectId userId;
    private List<SolvedProblem> solvedProblems = new ArrayList<>();
    private int totalScore;
    private LocalDateTime totalTime;
}

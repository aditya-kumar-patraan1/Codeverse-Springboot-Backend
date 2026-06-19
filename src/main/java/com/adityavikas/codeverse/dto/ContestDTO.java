package com.adityavikas.codeverse.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ContestDTO {
    private String contestName;
    private String contestDescription;
    private LocalDateTime startTime;
    private LocalDateTime duration;
}

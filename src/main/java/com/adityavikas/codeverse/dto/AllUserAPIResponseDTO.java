package com.adityavikas.codeverse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AllUserAPIResponseDTO {

    private String username;
    private String email;
    private boolean isAdmin;
    private String joined;

}
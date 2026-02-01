package com.adityavikas.codeverse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class APIResponseDTO {

    private String email;
    private String username;
    private List<String> roles = new ArrayList<>();

}

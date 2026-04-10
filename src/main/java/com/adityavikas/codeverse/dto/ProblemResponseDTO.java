package com.adityavikas.codeverse.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ProblemResponseDTO {
    private String problemID;
    private ProblemDTO problemDTO;
    private List<TestcaseDTO> listOfTestcase;
}

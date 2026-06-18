package com.adityavikas.codeverse.dto;

public record JdoodleResponseDTO(
        String output,
        String statusCode,
        String cpuTime,
        String memory,
        String error
) {
}

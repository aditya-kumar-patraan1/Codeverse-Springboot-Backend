package com.adityavikas.codeverse.dto;

public record JdoodleRequestDTO(
        String clientId,
        String clientSecret,
        String script,
        String language,
        String versionIndex
) {
}

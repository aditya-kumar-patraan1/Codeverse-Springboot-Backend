package com.adityavikas.codeverse.api.response;

public record JdoodleResponse(
        String output,
        String statusCode,
        String cpuTime,
        String memory,
        String error
) {
}

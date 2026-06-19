package com.adityavikas.codeverse.dto;

import org.bson.types.ObjectId;

public record EditorAccessDTO(
        ObjectId userId
) {
}

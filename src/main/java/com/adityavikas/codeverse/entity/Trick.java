package com.adityavikas.codeverse.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "Tricks")
@Data
public class Trick {

    private String notes;
    private LocalDateTime updated_at;
    private LocalDateTime created_at;

}

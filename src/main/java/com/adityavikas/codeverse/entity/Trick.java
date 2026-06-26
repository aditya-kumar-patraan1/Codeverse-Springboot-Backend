package com.adityavikas.codeverse.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "Trick")
@Data
public class Trick {

    @Id
    private String id;
    private String note = "";
    private LocalDateTime updated_at;

}

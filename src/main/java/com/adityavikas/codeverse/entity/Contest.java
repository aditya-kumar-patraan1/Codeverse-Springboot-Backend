package com.adityavikas.codeverse.entity;

import lombok.Data;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "contest")
public class Contest {

    @Id
    private ObjectId contestId;

    @NonNull
    private String contestName;

    private String contestDescription;
    private LocalDateTime startTime;
    private int duration;

    private String contestStatus;

    @DBRef
    private List<User> registeredUsers = new ArrayList<>();

}

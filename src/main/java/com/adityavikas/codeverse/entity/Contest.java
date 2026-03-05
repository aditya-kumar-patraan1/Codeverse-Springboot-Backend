package com.adityavikas.codeverse.entity;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
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
    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId contestId;

    @NonNull
    private String contestName;

    private String contestDescription;
    private LocalDateTime startTime;
    private int duration;
//    @DBRef
    private List<ObjectId> registeredUsers = new ArrayList<>();
    private String contestStatus;

}
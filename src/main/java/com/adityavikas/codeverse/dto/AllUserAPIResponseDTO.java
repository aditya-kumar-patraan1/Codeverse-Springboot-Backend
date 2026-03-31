package com.adityavikas.codeverse.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AllUserAPIResponseDTO {

    private String username;
    private String email;
    private boolean isAdmin;
    private String joined;
    private boolean isBan;
    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId userId;
    private String avatarLink = "";

}
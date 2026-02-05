package com.adityavikas.codeverse.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "codeverse_users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

//    primary key
    @Id
    private ObjectId userId;

    @Indexed(unique = true)
    @NonNull
    private String username;

    @Indexed(unique = true)
    private String email;
    private String password;
    private List<String> roles = new ArrayList<>();
    // provider can be LOCAL or GOOGLE
    private List<String> provider = new ArrayList<>();
    private LocalDateTime created_at;

}

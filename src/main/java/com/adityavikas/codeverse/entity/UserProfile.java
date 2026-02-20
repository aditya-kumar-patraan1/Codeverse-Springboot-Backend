package com.adityavikas.codeverse.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "userProfile")
@NoArgsConstructor
@AllArgsConstructor
public class UserProfile {

    @Id
    private ObjectId id;
    private ObjectId userId;   //change
    private String fullName = "";
    @Indexed(unique = true)
    private String username;
    private String gender = "";
//    private String DOB
    private String avatarLink = "https://i.pravatar.cc";
    private String bio = "";
    private String schoolName = "";
    private String location = "";
    private int overallRank = 5000000;
    private int contestRank = 67000;
    private String websiteLink = "";
    private List<ObjectId> badgesList = new ArrayList<>();

}

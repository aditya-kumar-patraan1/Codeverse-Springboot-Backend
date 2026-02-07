package com.adityavikas.codeverse.entity;

import com.fasterxml.jackson.annotation.JsonGetter;
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
    private ObjectId contestId;

    @NonNull
    private String contestName;

    private String contestDescription;
    private LocalDateTime startTime;
    private int duration;

    @Transient
    private String contestStatus;

    @DBRef
    private List<User> registeredUsers = new ArrayList<>();

    @JsonGetter("contestStatus")
    private String getContestStatus(){
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime endTime = startTime.plusMinutes(duration);
//        _____________________________________________
//           4=currentTime       10=startTime
        if(currentTime.isBefore(startTime)){
            return "Upcoming";
        }
//        __________________________________________________
//                  3=endTime                  6=currentTime
        else if(endTime.isBefore(currentTime)){
            return "Ended";
        }
//        ____________________________________________________
//            startTime=3    currentTime=4          8=endTime
        else{
            return "Ongoing";
        }
    }

}

package com.adityavikas.codeverse.services;

import com.adityavikas.codeverse.dto.ContestProblemDTO;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ContestProblemService {

    public boolean addContestProblem(ObjectId contestId, ContestProblemDTO contestProblemDTO){
        try{

            return true;
        } catch (Exception e) {
            log.error("contest problem not added & rollback...");
            return false;
        }
    }

}

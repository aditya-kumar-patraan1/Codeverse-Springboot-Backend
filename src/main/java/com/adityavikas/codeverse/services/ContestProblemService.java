package com.adityavikas.codeverse.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ContestProblemService {

    public boolean addContestProblem(){
        try{
            return true;
        } catch (Exception e) {
            log.error("contest problem not added & rollback...");
            return false;
        }
    }

}

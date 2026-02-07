package com.adityavikas.codeverse.services;

import com.adityavikas.codeverse.entity.Contest;
import com.adityavikas.codeverse.repository.ContestRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class ContestService {

    @Autowired
    private ContestRepository contestRepository;

    private boolean addContest(Contest contest){
        try{
            contestRepository.save(contest);
        } catch (Exception e) {
            log.error("Contest not added",e);
            return false;
        }
        return true;
    }

    private void deleteContest(ObjectId id){
        contestRepository.deleteById(id);
    }

}

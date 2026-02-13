package com.adityavikas.codeverse.services;

import com.adityavikas.codeverse.entity.Contest;
import com.adityavikas.codeverse.repository.ContestRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ContestService {

    @Autowired
    private ContestRepository contestRepository;

    public boolean addContest(Contest contest){
        try{
            contestRepository.save(contest);
        } catch (Exception e) {
            log.error("Contest not added",e);
            return false;
        }
        return true;
    }

    public void deleteContest(ObjectId id){
        contestRepository.deleteById(id);
    }

    public boolean deleteContestByContestName(String contestName){
        try{
            Contest contest = contestRepository.findContestByContestName(contestName);
            if(contest!=null){
                contestRepository.deleteContestByContestName(contestName);
                return true;
            }
            return false;
        }
        catch(Exception e){
            log.error("Contest not deleted by contest name",e);
            return false;
        }
    }

}

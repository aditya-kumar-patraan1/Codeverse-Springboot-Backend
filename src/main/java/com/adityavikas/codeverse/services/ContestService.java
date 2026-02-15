package com.adityavikas.codeverse.services;

import com.adityavikas.codeverse.entity.Contest;
import com.adityavikas.codeverse.entity.User;
import com.adityavikas.codeverse.middleware.Middlewares;
import com.adityavikas.codeverse.repository.ContestRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ContestService {

    @Autowired
    private ContestRepository contestRepository;

    @Autowired
    private Middlewares middlewares;



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

    public boolean updateContest(String contestName,Contest contest){
        try{
            Contest oldContest = contestRepository.findContestByContestName(contestName);
            if(oldContest!=null){
                if(!contest.getContestName().isEmpty()){
                    oldContest.setContestName(contest.getContestName());
                }
                if(!contest.getContestDescription().isEmpty()){
                    oldContest.setContestDescription(contest.getContestDescription());
                }
                oldContest.setDuration(contest.getDuration());
                oldContest.setStartTime(contest.getStartTime());
                contestRepository.save(oldContest);
                return true;
            }
        } catch (Exception e) {
            log.error("contest not updated",e);
            return false;
        }
        return false;
    }

    public User registerInContest(String contestName,String authorizationHeader){
        Contest contest = contestRepository.findContestByContestName(contestName);
        if(contest!=null){
            User user = middlewares.getUserByJwt(authorizationHeader);
            if(!user.getRegisteredContest().isEmpty()){
                contest.get
            }
            else{

            }
            return ;
        }
        else{
            log.error("Contest does'nt exist");
            return null;
        }
    }

}

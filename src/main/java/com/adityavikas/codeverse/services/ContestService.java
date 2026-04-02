package com.adityavikas.codeverse.services;

import com.adityavikas.codeverse.entity.Contest;
import com.adityavikas.codeverse.entity.User;
import com.adityavikas.codeverse.middleware.Middlewares;
import com.adityavikas.codeverse.repository.ContestRepository;
import com.adityavikas.codeverse.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ContestService {

    @Autowired
    private ContestRepository contestRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Middlewares middlewares;

    @Autowired
    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(ContestService.class);

    public boolean addContest(Contest contest){
        try{
            contestRepository.save(contest);
        } catch (Exception e) {
            log.error("Contest not added",e);
            return false;
        }
        return true;
    }

    public boolean deleteContest(ObjectId id){
        try{
            contestRepository.deleteById(id);
            return true;
        }
        catch (Exception e){
            logger.error("contest not deleted due to error",e);
            return false;
        }
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

    public boolean updateContest(ObjectId contestId,Contest contest){
        try{
            Contest oldContest = contestRepository.findById(contestId).orElse(null);
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

    @Transactional
    public boolean registerInContest(String contestId,String authorizationHeader){
        ObjectId contestObjectId = new ObjectId(contestId);
        Contest contest = contestRepository.findById(contestObjectId).orElse(null);
        if(contest!=null){
            User user = middlewares.getUserByJwt(authorizationHeader);
            contest.getRegisteredUsers().add(user.getUserId());
            contestRepository.save(contest);
            user.getRegisteredContest().add(contestObjectId);
            userService.saveUser(user);
            return true;
        }
        else{
            log.error("Contest does'nt exist");
            return false;
        }
    }

    public List<Contest> getAllContest(){
        try{
            return contestRepository.findAll();
        } catch (Exception e) {
            logger.error("All contests not fetched due to error",e);
            return List.of();
        }
    }

    
    public boolean isRegisterInContest(String contestId,String userId){
        try{
            User user = userRepository.findById(new ObjectId(userId)).orElse(null);

            boolean isRegistered = false;

            if(user!=null){
                isRegistered = user.getRegisteredContest().stream().anyMatch(conn -> conn.equals(new ObjectId(contestId)));
            }
            if(isRegistered) return true;

            return false;
        } catch (Exception e) {
            log.error("Something wrong happened while searching whether user registered in contest or not");
            return false;
        }
    }

}

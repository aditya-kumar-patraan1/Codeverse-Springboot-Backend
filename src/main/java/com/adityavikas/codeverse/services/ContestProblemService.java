package com.adityavikas.codeverse.services;

import com.adityavikas.codeverse.dto.ContestProblemDTO;
import com.adityavikas.codeverse.entity.Contest;
import com.adityavikas.codeverse.entity.Problem;
import com.adityavikas.codeverse.entity.ProblemDetails;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class ContestProblemService {

    private final ContestService contestService;
    private final ModelMapper modelMapper;

    @Autowired
    private ProblemService problemService;

    public ContestProblemService(ContestService contestService,ModelMapper modelMapper){
        this.contestService = contestService;
        this.modelMapper = modelMapper;
    }

    public boolean addContestProblem(ObjectId contestId, ContestProblemDTO contestProblemDTO){
        try{
            boolean isContestExist = contestService.findContestByContestId(contestId);

            if(!isContestExist){
                return false;
            }

            // find problem to add
            Problem problem = modelMapper.map(contestProblemDTO,Problem.class);
            problem.setContestId(contestId);
            problem.setContestProblem(false);

            if(contestProblemDTO.getTopicTags()!=null && !contestProblemDTO.getTopicTags().isEmpty()){
                List<String> tags = List.of(contestProblemDTO.getTopicTags().split(","));
                problem.setTopicTags(tags);
            }

            problemService.saveProblem(problem);
            return true;
        } catch (Exception e) {
            log.error("contest problem not added & rollback...");
            return false;
        }
    }

}

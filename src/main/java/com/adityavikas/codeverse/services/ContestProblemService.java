package com.adityavikas.codeverse.services;

import com.adityavikas.codeverse.dto.ContestProblemDTO;
import com.adityavikas.codeverse.dto.TestcaseDTO;
import com.adityavikas.codeverse.entity.Contest;
import com.adityavikas.codeverse.entity.Problem;
import com.adityavikas.codeverse.entity.ProblemDetails;
import com.adityavikas.codeverse.entity.Testcase;
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
    private final TestcaseService testcaseService;

    @Autowired
    private ProblemService problemService;

    public ContestProblemService(ContestService contestService,ModelMapper modelMapper,TestcaseService testcaseService){
        this.contestService = contestService;
        this.modelMapper = modelMapper;
        this.testcaseService = testcaseService;
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

            boolean isProblemSaved = problemService.saveProblem(problem);

            if(!isProblemSaved){
                return false;
            }

            ObjectId problemId = problemService.getProblemIdBySlugName(contestProblemDTO.getSlug());

            for(TestcaseDTO testcase : contestProblemDTO.getTestCases()){
                Testcase currentTestcase = modelMapper.map(testcase, Testcase.class);
                currentTestcase.setProblemId(problemId);
                System.out.println(currentTestcase.getExplanation());
            }

            problemService.saveProblem(problem);
            return true;
        } catch (Exception e) {
            log.error("contest problem not added & rollback...");
            return false;
        }
    }

}

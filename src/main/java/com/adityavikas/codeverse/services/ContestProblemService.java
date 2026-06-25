package com.adityavikas.codeverse.services;

import com.adityavikas.codeverse.dto.ContestProblemDTO;
import com.adityavikas.codeverse.dto.ContestProblemResponseDTO;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class ContestProblemService {

    private final ContestService contestService;
    private final ModelMapper modelMapper;
    private final TestcaseService testcaseService;

    @Autowired
    private ProblemService problemService;

    @Autowired
    private ProblemDetailService problemDetailService;

    public ContestProblemService(ContestService contestService,ModelMapper modelMapper,TestcaseService testcaseService){
        this.contestService = contestService;
        this.modelMapper = modelMapper;
        this.testcaseService = testcaseService;
    }

    @Transactional
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

            for(TestcaseDTO testcase : contestProblemDTO.getTestCases()) {
                Testcase currentTestcase = modelMapper.map(testcase, Testcase.class);
                currentTestcase.setProblemId(problemId);
                boolean isTestcaseSaved = testcaseService.addTestcase(currentTestcase, problemId.toString());
                if(!isTestcaseSaved){
                    return false;
                }
            }

            ProblemDetails problemDetails = modelMapper.map(contestProblemDTO,ProblemDetails.class);
            problemDetails.setProblemId(problemId);
            boolean isProblemDetailsSaved = problemDetailService.addProblemDetails(problemDetails);
            if(!isProblemDetailsSaved){
                return false;
            }
            return true;
        } catch (Exception e) {
            log.error("contest problem not added & rollback...");
            return false;
        }
    }

    public List<ContestProblemResponseDTO> getAllContestProblems(String contestId){
        try{
            List<Problem> allContestProblem = problemService.findProblemByContestId(new ObjectId(contestId));
            List<ContestProblemResponseDTO> result = new ArrayList<>();

            for(Problem problem : allContestProblem){
                ContestProblemResponseDTO contestProblemResponseDTO = modelMapper.map(problem,ContestProblemResponseDTO.class);
                String pId = problem.getId().toString();
                ProblemDetails problemDetails = problemDetailService.fetchProblemDetail(pId);
                if(problemDetails!=null){
                    contestProblemResponseDTO.setDescription(problemDetails.getDescription());
                    contestProblemResponseDTO.setTemplates(problemDetails.getTemplates());
                }

                List<Testcase> testcases = testcaseService.fetchTestcase(pId);
                contestProblemResponseDTO.setTestcases(testcases);
                result.add(contestProblemResponseDTO);
            }

            return result;

        } catch (Exception e) {
            log.error("error during fetching the problem of contest id : ",contestId);
            return null;
        }

    }

    public boolean updateContestProblem(String problemId,ContestProblemDTO updatedProblem){
        try{
            return problemService.updateEntireProblem(problemId,updatedProblem);
        } catch (Exception e) {
            log.error("contest problem not updated...");
            return false;
        }
    }


}

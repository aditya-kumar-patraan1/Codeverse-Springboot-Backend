package com.adityavikas.codeverse.services;

import com.adityavikas.codeverse.dto.ProblemDTO;
import com.adityavikas.codeverse.dto.ProblemResponseDTO;
import com.adityavikas.codeverse.dto.TestcaseDTO;
import com.adityavikas.codeverse.entity.Problem;
import com.adityavikas.codeverse.entity.ProblemDetails;
import com.adityavikas.codeverse.entity.Testcase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class PublicService {

    @Autowired
    private ProblemService problemService;

    @Autowired
    private ProblemDetailService problemDetailService;

    @Autowired
    private TestcaseService testcaseService;

    public ProblemResponseDTO getSpecificProblemData(String problemId){
        try{

            Problem problem = problemService.fetchProblem(problemId).orElse(null);
            ProblemDetails problemDetails = problemDetailService.fetchProblemDetail(problemId);
            List<Testcase> testcases = testcaseService.fetchTestcase(problemId);

            if(problem!=null){

                ProblemDTO problemDTO = new ProblemDTO();
                problemDTO.setAcceptanceRate(problem.getAcceptanceRate());
                problemDTO.setInputType(problem.getInputType());
                problemDTO.setReturnType(problem.getReturnType());
                problemDTO.setFunctionName(problem.getFunctionName());
                problemDTO.setDifficulty(problem.getDifficulty());
                problemDTO.setStatus(problem.isStatus());
                problemDTO.setSlug(problem.getSlug());
                problemDTO.setTitle(problem.getTitle());
                problemDTO.setSno(problem.getSno());

                List<String> topicTags = problem.getTopicTags();

                String topics = "";

                for(int i=0;i< topicTags.size();i++){
                    topics = topics + topicTags.get(i);
                    if(i < topicTags.size()-1){
                        topics+=',';
                    }
                }

                problemDTO.setTopicTags(topics);
                problemDTO.setDescription(problemDetails.getDescription());
                problemDTO.setTemplates(problemDetails.getTemplates());
                problemDTO.setSolutions(problemDetails.getSolutions());
                problemDTO.setTimeComplexity(problemDetails.getTimeComplexity());
                problemDTO.setSpaceComplexity(problemDetails.getSpaceComplexity());
                problemDTO.setEditorial(problemDetails.getEditorial());
                problemDTO.setDescription(problemDetails.getDescription());
                problemDTO.setAlgorithmSteps(problemDetails.getAlgorithmSteps());


                List<TestcaseDTO> listOfTestcase = new ArrayList<>();

                for(int i=0;i< testcases.size();i++){
                    Testcase testcase = testcases.get(i);
                    TestcaseDTO testcaseDTO = new TestcaseDTO();
                    testcaseDTO.setInput(testcase.getInput());
                    testcaseDTO.setHidden(testcase.isHidden());
                    // output added
                    testcaseDTO.setOutput(testcase.getOutput());
                    testcaseDTO.setExplanation(testcase.getExplanation());
                    listOfTestcase.add(testcaseDTO);
                }

                ProblemResponseDTO problemResponseDTO  = new ProblemResponseDTO();
                problemResponseDTO.setProblemID(problemId);
                problemResponseDTO.setProblemDTO(problemDTO);
                problemResponseDTO.setListOfTestcase(listOfTestcase);



                return problemResponseDTO;
            }

            return null;

        } catch (Exception e) {
            log.error("problem not accessed");
            return null;
        }
    }

}

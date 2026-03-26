package com.adityavikas.codeverse.services;

import com.adityavikas.codeverse.dto.ProblemDTO;
import com.adityavikas.codeverse.dto.TestcaseDTO;
import com.adityavikas.codeverse.entity.Problem;
import com.adityavikas.codeverse.entity.ProblemDetails;
import com.adityavikas.codeverse.entity.Testcase;
import com.adityavikas.codeverse.repository.ProblemRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class ProblemService {

    private static final Logger logger = LoggerFactory.getLogger(ProblemService.class);

    @Autowired
    private ProblemRepository problemRepository;

    @Autowired
    private ProblemDetailService problemDetailService;

    @Autowired
    private TestcaseService testcaseService;

    public Boolean saveProblem(Problem problem){
        try{
            problem.setCreated_at(LocalDateTime.now());
            problemRepository.save(problem);
        } catch (Exception e) {
            logger.error("Problem not saved");
            return false;
        }
        return true;
    }

    public List<Problem> fetchAllProblems(){
        return problemRepository.findAll();
    }

    public Optional<Problem> fetchProblem(String objectStringId){
        ObjectId objectId = new ObjectId(objectStringId);
        return problemRepository.findById(objectId);
    }

    public boolean deleteProblem(String problemId){
        ObjectId objectId = new ObjectId(problemId);
        try{
            problemRepository.deleteById(objectId);
            return true;
        }
        catch (Exception e) {
            logger.error("testcase not deleted");
            return false;
        }
    }

    @Transactional
    public boolean deleteCompleteProblem(String problemId){
        try{
            // from problem
            boolean isDeleted1 = deleteProblem(problemId);
            // from testcase collection
            boolean isDeleted2 = testcaseService.deleteTestcase(problemId);
            // from problemDetails collection
            boolean isDeleted3 = problemDetailService.deleteProblemDetails(problemId);

            return isDeleted2 && isDeleted1 && isDeleted3;
        }
        catch(Exception e){
            logger.error("problem not deleted");
            return false;
        }
    }

    public ObjectId getProblemIdBySlugName(String slug){
        try{
            return problemRepository.findBySlug(slug).getId();
        }
        catch (Exception e){
            logger.error("Problem Id not fetched by slug name");
            return null;
        }
    }

    @Transactional
    public boolean addEntireProblem(ProblemDTO problemDTO){
        try{
            Problem problem = new Problem();
            String[] tags = problemDTO.getTopicTags().split(",");

            problem.setTitle(problemDTO.getTitle());
            problem.setSlug(problemDTO.getSlug());
            problem.setTopicTags(Arrays.stream(tags).toList());
            problem.setDifficulty(problemDTO.getDifficulty());
            problem.setSno(problemDTO.getSno());
            problem.setFunctionName(problemDTO.getFunctionName());
            problem.setReturnType(problemDTO.getReturnType());
            problem.setInputType(problemDTO.getInputType());
            problem.setStatus(problemDTO.isStatus());
            problem.setAcceptanceRate(problemDTO.getAcceptanceRate());

            Boolean isProblemSaved = saveProblem(problem);

            ObjectId problemId = getProblemIdBySlugName(problemDTO.getSlug());

            boolean isAllTestcaseSaved = true;

            for(TestcaseDTO testcaseDTO : problemDTO.getTestCases()){
                Testcase testcase = new Testcase();
                testcase.setHidden(testcaseDTO.isHidden());
                testcase.setInput(testcaseDTO.getInput());
                testcase.setOutput(testcaseDTO.getOutput());
                testcase.setExplanation(testcaseDTO.getExplanation());
                boolean isTestcaseSaved = testcaseService.addTestcase(testcase, problemId.toString());
                isAllTestcaseSaved = isAllTestcaseSaved && isTestcaseSaved;
            }

            ProblemDetails problemDetails = new ProblemDetails();

            problemDetails.setProblemId(problemId);
            problemDetails.setDescription(problemDTO.getDescription());
            problemDetails.setEditorial(problemDetails.getEditorial());
            problemDetails.setTemplates((problemDTO.getTemplates()!=null?problemDTO.getTemplates():new HashMap<>()));
            problemDetails.setSolutions((problemDTO.getSolutions()!=null?problemDTO.getSolutions():new HashMap<>()));
            problemDetails.setTimeComplexity((problemDTO.getTimeComplexity()!=null?problemDTO.getTimeComplexity():new HashMap<>()));
            problemDetails.setSpaceComplexity((problemDTO.getSpaceComplexity()!=null?problemDTO.getSpaceComplexity():new HashMap<>()));
            problemDetails.setAlgorithmSteps(problemDTO.getAlgorithmSteps());

            boolean isProblemDetailsSaved = problemDetailService.problemDetailsAdded(problemDetails);

            return isProblemDetailsSaved && isAllTestcaseSaved && isProblemSaved;

        } catch (Exception e) {
            logger.error("Problem not added completely");
            return false;
        }
    }

}

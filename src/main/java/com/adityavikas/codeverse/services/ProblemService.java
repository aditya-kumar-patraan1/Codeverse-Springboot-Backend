package com.adityavikas.codeverse.services;

import com.adityavikas.codeverse.entity.Problem;
import com.adityavikas.codeverse.entity.ProblemDetails;
import com.adityavikas.codeverse.repository.ProblemRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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

}

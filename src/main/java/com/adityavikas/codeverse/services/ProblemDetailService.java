package com.adityavikas.codeverse.services;

import com.adityavikas.codeverse.dto.ContestProblemDTO;
import com.adityavikas.codeverse.entity.ProblemDetails;
import com.adityavikas.codeverse.repository.ProblemDetailRepository;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProblemDetailService {

    @Autowired
    private ProblemDetailRepository problemDetailRepository;

    private static final Logger logger = LoggerFactory.getLogger(ProblemDetailService.class);

    public boolean addProblemDetails(ProblemDetails problemDetails){
        try{
            problemDetailRepository.save(problemDetails);
            return true;
        }
        catch(Exception e){
            logger.error("Problem not added",e);
            return false;
        }
    }

    public ProblemDetails fetchProblemDetail(String problemId){
        try{
            ObjectId objectId = new ObjectId(problemId);
            return problemDetailRepository.findByProblemId(objectId);
        }
        catch(Exception e){
            logger.error("Problem details not fetched");
            return null;
        }
    }

    public boolean deleteProblemDetails(String problemId){
        ObjectId objectId = new ObjectId(problemId);
        try{
            problemDetailRepository.deleteByProblemId(objectId);
            return true;
        }
        catch(Exception e){
            logger.error("problem Details not deleted");
            return false;
        }
    }

    public boolean updateProblemDetails(String problemId, ContestProblemDTO contestProblemDTO){
        try{
            ProblemDetails oldProblemDetails = fetchProblemDetail(problemId);
            oldProblemDetails.setTemplates(contestProblemDTO.getTemplates());
            oldProblemDetails.setDescription(contestProblemDTO.getDescription());
            oldProblemDetails.setSolutions(contestProblemDTO.getSolutions());
            oldProblemDetails.setSpaceComplexity(contestProblemDTO.getSpaceComplexity());
            oldProblemDetails.setAlgorithmSteps(contestProblemDTO.getAlgorithmSteps());
            oldProblemDetails.setEditorial(contestProblemDTO.getEditorial());
            oldProblemDetails.setTimeComplexity(contestProblemDTO.getTimeComplexity());
            problemDetailRepository.save(oldProblemDetails);
            return true;
        } catch (Exception e) {
            logger.error("Problem Details not updated");
            return false;
        }
    }

}

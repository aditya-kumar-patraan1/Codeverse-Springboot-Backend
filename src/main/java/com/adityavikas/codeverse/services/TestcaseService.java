package com.adityavikas.codeverse.services;

import com.adityavikas.codeverse.dto.TestcaseDTO;
import com.adityavikas.codeverse.entity.Testcase;
import com.adityavikas.codeverse.repository.TestcaseRepository;
import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestcaseService {

    @Autowired
    private TestcaseRepository testcaseRepository;

    private static final Logger logger = LoggerFactory.getLogger(TestcaseService.class);

    public boolean addTestcase(Testcase testcase,String problemId){
        ObjectId objectId = new ObjectId(problemId);
        testcase.setProblemId(objectId);
        try{
            testcaseRepository.save(testcase);
            return true;
        } catch (Exception e) {
            logger.error("testcase not added due to error : ",e);
            return false;
        }
    }

    @Autowired
    private ModelMapper modelMapper;

    public boolean deleteTestcase(String problemId){
        ObjectId objectId = new ObjectId(problemId);
        try{
            testcaseRepository.deleteByProblemId(objectId);
            return true;
        }
        catch (Exception e) {
            logger.error("testcase not deleted");
            return false;
        }
    }

    public List<Testcase> fetchTestcase(String problemId){
        ObjectId objectProblemId = new ObjectId(problemId);
        try{
            return testcaseRepository.findAllByProblemId(objectProblemId);
        }
        catch(Exception e){
            logger.error("Testcase not found");
            return null;
        }
    }

    public boolean updateTestcase(String problemId,List<TestcaseDTO> updatedTestcases){
        try{
            for(var updatedIndividualTestcase : updatedTestcases){
                Testcase oldTestcase = testcaseRepository.findById(new ObjectId(updatedIndividualTestcase.getId())).orElse(null);
                if(oldTestcase!=null){
                    oldTestcase.setOutput(updatedIndividualTestcase.getOutput());
                    oldTestcase.setInput(updatedIndividualTestcase.getInput());
                    oldTestcase.setExplanation(updatedIndividualTestcase.getExplanation());
                    oldTestcase.setHidden(updatedIndividualTestcase.isHidden());
//                    Testcase save = testcaseRepository.save(oldTestcase);
//                    if(save.getId()==null){
//                        return false;
//                    }
                }
                else{
                    Testcase testcase = modelMapper.map(updatedIndividualTestcase, Testcase.class);
                    addTestcase(testcase,problemId);
                }
            }
            return true;
        } catch (Exception e) {
            logger.error("testcase not updated");
            return false;
        }
    }

}

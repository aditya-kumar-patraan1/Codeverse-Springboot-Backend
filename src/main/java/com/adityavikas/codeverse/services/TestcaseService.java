package com.adityavikas.codeverse.services;

import com.adityavikas.codeverse.entity.Testcase;
import com.adityavikas.codeverse.repository.TestcaseRepository;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public boolean deleteTestcase(String problemId){
        ObjectId objectId = new ObjectId(problemId);
        try{
            testcaseRepository.deleteById(objectId);
            return true;
        }
        catch (Exception e) {
            logger.error("testcase not deleted");
            return false;
        }
    }

}

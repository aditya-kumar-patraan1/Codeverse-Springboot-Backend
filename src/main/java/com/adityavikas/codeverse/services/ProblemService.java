package com.adityavikas.codeverse.services;

import com.adityavikas.codeverse.entity.Problem;
import com.adityavikas.codeverse.repository.ProblemRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ProblemService {

    private static final Logger logger = LoggerFactory.getLogger(ProblemService.class);

    @Autowired
    private ProblemRepository problemRepository;

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

}

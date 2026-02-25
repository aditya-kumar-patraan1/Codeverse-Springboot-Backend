package com.adityavikas.codeverse.services;

import com.adityavikas.codeverse.entity.Submission;
import com.adityavikas.codeverse.repository.SubmissionRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@Service

public class SubmissionService {

    @Autowired
    private SubmissionRepository submissionRepository;

    private static final Logger logger = LoggerFactory.getLogger(Submission.class);

    public List<Submission> getAllSubmissionOfUser(String username){
        try{
            Stream<Submission> submissionStream = submissionRepository.findAll().stream().filter(submission -> submission.getUsername().equalsIgnoreCase(username));
            return submissionStream.toList();
        }
        catch(Exception e){
            logger.error("submissions not retrieved");
            return null;
        }
    }

    public List<Submission> getAllSubmissionForSameProblemByUser(String username,String problemId){
        try{
            Stream<Submission> submissionStream = submissionRepository.findAll().stream().filter(submission -> submission.getUsername().equalsIgnoreCase(username));
            return submissionStream.filter(submission -> submission.getProblemId().equalsIgnoreCase(problemId)).toList();
        } catch (Exception e) {
            logger.error("submissions not retrieved");
            return null;
        }
    }

    public boolean createSubmission(Submission submission){
        try{
            submission.setSubmittedAt(LocalDateTime.now());
            submission.setSlug(UUID.randomUUID().toString());
            submissionRepository.save(submission);
            return true;
        } catch (Exception e) {
            logger.error("Submission not created");
            return false;
        }
    }


    public Submission getSharedSubmission(String slug){
        try{
            return submissionRepository.findByslug(slug);
        } catch (Exception e) {
            logger.error("wrong slug to access submission code",e);
            return null;
        }
    }

}

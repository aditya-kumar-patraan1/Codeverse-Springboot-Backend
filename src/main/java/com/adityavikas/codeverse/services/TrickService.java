package com.adityavikas.codeverse.services;

import com.adityavikas.codeverse.entity.Trick;
import com.adityavikas.codeverse.repository.TrickRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
public class TrickService {

    @Autowired
    private TrickRepository trickRepository;

    public boolean updateTrick(String updatedNote){
        try{
            Trick oldTrick = trickRepository.findAll().get(0);
            if(oldTrick!=null){
                oldTrick.setNote(updatedNote);
                oldTrick.setUpdated_at(LocalDateTime.now());
                trickRepository.save(oldTrick);
                return true;
            }
            else{
                return false;
            }
        } catch (Exception e) {
            log.error("trick not updated");
            return false;
        }
    }

    public Trick getNote(){
        try{
            return trickRepository.findAll().stream().findFirst().orElse(null);
        } catch (Exception e) {
            log.error("empty notes found");
            return null;
        }
    }

}

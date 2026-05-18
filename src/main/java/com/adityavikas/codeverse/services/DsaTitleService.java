package com.adityavikas.codeverse.services;

import com.adityavikas.codeverse.entity.DsaTitle;
import com.adityavikas.codeverse.repository.DsaTitleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DsaTitleService {

    @Autowired
    private DsaTitleRepository dsaTitleRepository;

    private static final Logger logger = LoggerFactory.getLogger(DsaTitleService.class);

    public boolean addDsaTitle(DsaTitle dsaTitle){
        try{
            dsaTitleRepository.save(dsaTitle);
            return true;
        } catch (Exception e) {
            logger.error("Dsa Title Not added");
            return false;
        }
    }

    public DsaTitle findByTitleId(String titleId){
        try{
            return dsaTitleRepository.findByTitleId(titleId);
        }
        catch (Exception e){
            return null;
        }
    }

    public List<DsaTitle> findAllTitles(){
        try{
            return dsaTitleRepository.findAll();
        }
        catch(Exception e){
            logger.error("Titles are not extracted");
            return new ArrayList<>();
        }
    }

}

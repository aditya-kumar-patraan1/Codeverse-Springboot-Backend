package com.adityavikas.codeverse.services;

import com.adityavikas.codeverse.entity.DsaTitle;
import com.adityavikas.codeverse.repository.DsaTitleRepository;
import org.bson.types.ObjectId;
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

    public ObjectId addDsaTitle(DsaTitle dsaTitle){
        try{
            return dsaTitleRepository.save(dsaTitle).getId();
        } catch (Exception e) {
            logger.error("Dsa Title Not added");
            return null;
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

    public boolean deleteTitleById(ObjectId titleId){
        try{
            dsaTitleRepository.deleteById(titleId);
            return true;
        } catch (Exception e) {
            logger.error("DSA Title not deleted due to error : "+e);
            return false;
        }
    }

}

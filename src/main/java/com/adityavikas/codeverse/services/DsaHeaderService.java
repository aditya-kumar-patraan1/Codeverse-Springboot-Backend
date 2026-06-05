package com.adityavikas.codeverse.services;

import com.adityavikas.codeverse.entity.DsaHeader;
import com.adityavikas.codeverse.entity.DsaTitle;
import com.adityavikas.codeverse.repository.DsaHeaderRepository;
import com.adityavikas.codeverse.repository.DsaTitleRepository;
import com.adityavikas.codeverse.repository.DsaTitleRepositoryImpl;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class DsaHeaderService {

    private static final Logger logger = LoggerFactory.getLogger(DsaHeaderService.class);

    private final DsaHeaderRepository dsaHeaderRepository;
    private final DsaTitleRepositoryImpl dsaTitleRepositoryImpl;
    private final DsaTemplateService dsaTemplateService;
    private final DsaTitleService dsaTitleService;

    public DsaHeaderService(DsaHeaderRepository dsaHeaderRepository,DsaTitleRepositoryImpl dsaTitleRepositoryImpl,DsaTemplateService dsaTemplateService,DsaTitleService dsaTitleService){
        this.dsaHeaderRepository = dsaHeaderRepository;
        this.dsaTitleRepositoryImpl = dsaTitleRepositoryImpl;
        this.dsaTemplateService = dsaTemplateService;
        this.dsaTitleService = dsaTitleService;
    }

    public boolean addDsaHeader(@RequestBody DsaHeader dsaHeader){
        try{
            dsaHeaderRepository.save(dsaHeader);
            return true;
        } catch (Exception e) {
            logger.error("DSA Header not added");
            return false;
        }
    }

    public DsaHeader findDsaHeaderByHeaderId(String categoryId){
        try{
            return dsaHeaderRepository.findByHeaderId(categoryId);
        }
        catch(Exception e){
            logger.error("No DsaHeader find by HeaderId");
            return null;
        }
    }

    public List<DsaHeader> getAllHeaders(){
        try{
            return dsaHeaderRepository.findAll();
        } catch (Exception e) {
            logger.error("No DSA Headers are present");
            return null;
        }
    }

    @Transactional
    public boolean deleteEntireDSACategory(String categoryId){
        try{
            List<DsaTitle> allTitleByCategoryId = dsaTitleRepositoryImpl.getAllTitleByCategoryId(categoryId);
            for(DsaTitle dsaTitle : allTitleByCategoryId){
                for(ObjectId id : dsaTitle.getListOfTemplateIds()){
                    dsaTemplateService.deleteDsaTemplateById(id);
                }
                dsaTitleService.deleteTitleById(dsaTitle.getId());
            }
            dsaHeaderRepository.deleteByHeaderId(categoryId);
            return true;
        } catch (Exception e) {
            logger.error("DSA Category not deleted entirely due to error : "+e);
            return false;
        }
    }

}

package com.adityavikas.codeverse.services;

import com.adityavikas.codeverse.entity.DsaTemplate;
import com.adityavikas.codeverse.entity.User;
import com.adityavikas.codeverse.repository.DsaTemplateRepository;
import com.adityavikas.codeverse.repository.DsaTemplateRepositoryImpl;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DsaTemplateService {

    @Autowired
    private DsaTemplateRepository dsaTemplateRepository;

    @Autowired
    private UserService userService;

    private  final DsaTemplateRepositoryImpl dsaTemplateRepositoryImpl;

    public DsaTemplateService(DsaTemplateRepositoryImpl dsaTemplateRepositoryImpl){
        this.dsaTemplateRepositoryImpl = dsaTemplateRepositoryImpl;
    }

    private static final Logger logger = LoggerFactory.getLogger(DsaTemplateService.class);

    public ObjectId addDsaTemplate(DsaTemplate dsaTemplate){
        try{
            return dsaTemplateRepository.save(dsaTemplate).getId();
        }
        catch (Exception e){
            logger.error("Template not added");
            return null;
        }
    }

    public List<DsaTemplate> getTemplates(){
        try{
            return dsaTemplateRepository.findAll();
        } catch (Exception e) {
            logger.error("Templates are empty");
            return null;
        }
    }

    public boolean deleteDsaTemplate(String templateId){
        try{
            dsaTemplateRepository.deleteById(new ObjectId(templateId));
            return true;
        } catch (Exception e) {
            logger.error("DSA Template not delete error : "+e);
            return false;
        }
    }

    public boolean deleteDsaTemplateById(ObjectId templateId){
        try{
            dsaTemplateRepository.deleteById(templateId);
            return true;
        } catch (Exception e) {
            logger.error("no deletion DSA Template due to error : "+e);
            return false;
        }
    }

    public boolean updateDSATemplate(ObjectId mongoObjectId,DsaTemplate updatedDSATemplate){
        try{
            return dsaTemplateRepositoryImpl.updateDSAContent(mongoObjectId, updatedDSATemplate);
        } catch (Exception e) {
            logger.error("Error occurred during updating DSA Template : {0}",e);
            return false;
        }
    }

    public boolean addToCompletedStatus(User user,String titleSlug){
        try{
            if(user.getCompletedSlugs().contains(titleSlug)){
                return false;
            }
            user.getCompletedSlugs().add(titleSlug);
            userService.saveUser(user);
            return true;
        } catch (Exception e) {
            logger.error("Error faced while adding to completed status");
            return false;
        }
    }

    public boolean removeFromCompletedStatus(User user,String titleSlug){
        try{
            if(user.getCompletedSlugs().contains(titleSlug)){
                boolean isRemoved = user.getCompletedSlugs().remove(titleSlug);
                if(isRemoved) {
                    userService.saveUser(user);
                    return true;
                }
                return false;
            }
            return false;
        } catch (Exception e) {
            logger.error("Error faced while removing from completed status");
            return false;
        }
    }

}

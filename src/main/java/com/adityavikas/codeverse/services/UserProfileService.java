package com.adityavikas.codeverse.services;

import com.adityavikas.codeverse.entity.UserProfile;
import com.adityavikas.codeverse.repository.UserProfileRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserProfileService {

    @Autowired
    private UserProfileRepository userProfileRepository;

    public boolean saveUserProfile(UserProfile userProfile){
        try{
            userProfileRepository.save(userProfile);
            return true;
        }
        catch (Exception e){
            log.error("User Profile not saved",e);
            return false;
        }
    }

}

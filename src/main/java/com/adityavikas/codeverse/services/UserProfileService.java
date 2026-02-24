package com.adityavikas.codeverse.services;

import com.adityavikas.codeverse.entity.UserProfile;
import com.adityavikas.codeverse.repository.UserProfileRepository;
import com.adityavikas.codeverse.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
public class UserProfileService {

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private CloudinaryService cloudinaryService;

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

    public boolean updateUserProfile(UserProfile oldUser, UserProfile userProfile, MultipartFile avatarMedia){

//        String jwtToken = null;

        try{
            //handling "avatarMedia"
            if(avatarMedia!=null && !avatarMedia.isEmpty()){
                String avatarLink = cloudinaryService.uploadFileToCloudinary(avatarMedia);
                oldUser.setAvatarLink(avatarLink);
            }
            if(!oldUser.getFullName().equalsIgnoreCase(userProfile.getFullName()) && !userProfile.getFullName().isEmpty()){
                oldUser.setFullName(userProfile.getFullName());
            }
            if(!oldUser.getUsername().equalsIgnoreCase(userProfile.getUsername()) && !userProfile.getUsername().isEmpty()){
                // if username changes generate new jwt token
//                jwtToken = jwtUtils.generateToken(userProfile.getUsername());
                oldUser.setUsername(userProfile.getUsername());
            }
            if(!oldUser.getGender().equalsIgnoreCase(userProfile.getGender()) && !userProfile.getGender().isEmpty()){
                oldUser.setGender(userProfile.getGender());
            }
            if(!oldUser.getSchoolName().equalsIgnoreCase(userProfile.getSchoolName()) && !userProfile.getSchoolName().isEmpty()){
                oldUser.setSchoolName(userProfile.getSchoolName());
            }
            if(!oldUser.getLocation().equalsIgnoreCase(userProfile.getLocation()) && !userProfile.getLocation().isEmpty()){
                oldUser.setLocation(userProfile.getLocation());
            }
            if(!oldUser.getWebsiteLink().equalsIgnoreCase(userProfile.getWebsiteLink()) && !userProfile.getWebsiteLink().isEmpty()){
                oldUser.setWebsiteLink(userProfile.getWebsiteLink());
            }
            if(!oldUser.getBio().equalsIgnoreCase(userProfile.getBio()) && !userProfile.getBio().isEmpty()){
                oldUser.setBio(userProfile.getBio());
            }
            // saving the changes
            userProfileRepository.save(oldUser);
            return true;
        } catch (Exception e) {
            log.error("User profile not updated",e);
            return false;
        }
    }

    public UserProfile getUserProfile(String username){
        return userProfileRepository.findByUsername(username);
    }

}

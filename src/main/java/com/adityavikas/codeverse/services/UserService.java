package com.adityavikas.codeverse.services;

import com.adityavikas.codeverse.entity.User;
import com.adityavikas.codeverse.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void saveUser(User user){
        userRepository.save(user);
    }

    public boolean saveUserWithBcryptPassword(User user){
        try{
            // hashing password
            user.setCreated_at(LocalDateTime.now());
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
        }
        catch(Exception e){
            log.error("User not saved to mongo db",e);
            return false;
        }
        return true;
    }

    // change
    public User getUserById(String userId){
        ObjectId objectId = new ObjectId(userId);
        return userRepository.findById(objectId).orElse(null);
    }

    // this is the exception only right that we are providing to the user for userProfile
    public boolean updateUser(String Id,String newUsername){
        ObjectId userId = new ObjectId(Id);
        try{
            User oldUser = userRepository.findById(userId).orElse(null);
            if(oldUser!=null && !newUsername.isEmpty() && !oldUser.getUsername().equalsIgnoreCase(newUsername)){
                oldUser.setUsername(newUsername);
            }
            userRepository.save(oldUser);
            return true;
        } catch (Exception e) {
            log.error("User not update for user profile");
            return false;
        }
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public boolean banOrUnbanUser(User user){
        try{
            user.setBan(!user.isBan());
            userRepository.save(user);
            return true;
        } catch (Exception e) {
            log.error("User not banned due to the error",e);
            return false;
        }
    }

}

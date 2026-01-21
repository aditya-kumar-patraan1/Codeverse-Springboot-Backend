package com.adityavikas.codeverse.services;

import com.adityavikas.codeverse.entity.User;
import com.adityavikas.codeverse.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
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


    public boolean saveUserWithBcryptPassword(User user){
        try{
            // hashing password
//            user.setCreated_at(LocalDateTime.now());
            user.setRoles(List.of("USER"));
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
        }
        catch(Exception e){
            log.error("User not saved to mongo db",e);
            return false;
        }
        return true;
    }

}

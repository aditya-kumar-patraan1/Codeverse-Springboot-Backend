package com.adityavikas.codeverse.middleware;

import com.adityavikas.codeverse.entity.User;
import com.adityavikas.codeverse.repository.UserRepository;
import com.adityavikas.codeverse.services.UserDetailsServiceImpl;
import com.adityavikas.codeverse.services.UserService;
import com.adityavikas.codeverse.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class Middlewares {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserRepository userRepository;

    public User getUserByJwt(String authorizationHeader){
        String username = null;
        String jwt = null;
        User user = null;
        if(authorizationHeader!=null && authorizationHeader.startsWith("Bearer")){
            jwt = authorizationHeader.substring(7);
            username = jwtUtils.extractUsername(jwt);
            user = userRepository.findByUsername(username);
        }
        return user;
    }

}

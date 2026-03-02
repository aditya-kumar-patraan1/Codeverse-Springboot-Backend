package com.adityavikas.codeverse.controllers;
import com.adityavikas.codeverse.dto.LoginUserDTO;
import com.adityavikas.codeverse.dto.UserDTO;
import com.adityavikas.codeverse.entity.Problem;
import com.adityavikas.codeverse.entity.User;
import com.adityavikas.codeverse.entity.UserProfile;
import com.adityavikas.codeverse.repository.UserRepository;
import com.adityavikas.codeverse.services.*;
import com.adityavikas.codeverse.utils.JwtUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/public")
@Tag(name = "All Public API's",description = "This is the public controller used to check health of API connection,Registering and login user")
public class PublicController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserProfileService userProfileService;

    @Autowired
    private ProblemService problemService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProblemDetailService problemDetailService;

    @Operation(summary = "To check API health")
    @GetMapping("/health-check")
    public ResponseEntity<?> checkHealth(){
        return ResponseEntity.ok(List.of("Hey !","It's","Working"));
    }

    @Operation(summary = "to register user to codeverse")
    @PostMapping("/register")
    @Transactional
    public ResponseEntity<?> saveUser(@RequestBody LoginUserDTO userDTO){
        Map<String,Integer> returnStatus = new HashMap<>();
        returnStatus.put("status",0);
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setEmail(userDTO.getEmail());
        // UserProfile created
        UserProfile userProfile = new UserProfile();
        userProfile.setFullName(userDTO.getUsername());
        userProfile.setUsername(userDTO.getUsername());
//        userProfileService.saveUserProfile(userProfile);
        try{
            user.setRoles(List.of("USER"));
            List<String> providers = user.getProvider();
            providers.add("LOCAL");    //sign-in by LOCAL
            user.setProvider(providers);
            boolean isSaved = userService.saveUserWithBcryptPassword(user);
            userProfile.setUserId(userRepository.findByUsername(user.getUsername()).getUserId());
            userProfileService.saveUserProfile(userProfile);

            if(isSaved) {
                returnStatus.put("status",1);
                return new ResponseEntity<>(returnStatus, HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>(returnStatus, HttpStatus.BAD_REQUEST);
            }
        }
        catch(Exception e){
            return new ResponseEntity<>(returnStatus, HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "to login user to codeverse")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDTO userDTO){
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        Map<String,Object> returnResponse = new HashMap<>();
        returnResponse.put("jwtToken","");
        try{
            ObjectId Id = userRepository.findByUsername(user.getUsername()).getUserId();
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(Id,user.getPassword())
            );
            String userId = null;
            if(Id!=null){
                userId = Id.toString();
            }
            String jwt = jwtUtils.generateToken(userId);
            returnResponse.put("jwtToken",jwt);
            returnResponse.put("status",1);
            return new ResponseEntity<>(returnResponse,HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(returnResponse,HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "This is used to fetch the specific problem")
    @GetMapping("/fetchOne/{problemId}")
    public ResponseEntity<?> fetchOneProblem(@PathVariable String problemId) throws Exception {
        Map<String, Object> returnResponse = new HashMap<>();
        returnResponse.put("status",0);
        returnResponse.put("problem",null);
        try{
            Problem problem = problemService.fetchProblem(problemId).orElse(null);
            if(problem!=null){
                returnResponse.put("status",0);
                returnResponse.put("problem",problem);
                return new ResponseEntity<>(returnResponse,HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>(returnResponse,HttpStatus.NO_CONTENT);
            }
        }
        catch (Exception e){
            throw new Exception("API error");
        }
    }


    @Operation(summary = "This API endpoint is used to fetch the Problem Details")
    @GetMapping("/fetchProblemDetail")
    public ResponseEntity<?> fetchProblemDetail(@PathVariable String problemId){
        problemDetailService.fetchProblemDetail();
    }



}

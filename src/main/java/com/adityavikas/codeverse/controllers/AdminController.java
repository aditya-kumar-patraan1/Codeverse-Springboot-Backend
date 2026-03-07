package com.adityavikas.codeverse.controllers;

import com.adityavikas.codeverse.dto.AdminDTO;
import com.adityavikas.codeverse.dto.AllUserAPIResponseDTO;
import com.adityavikas.codeverse.entity.Contest;
import com.adityavikas.codeverse.entity.User;
import com.adityavikas.codeverse.middleware.Middlewares;
import com.adityavikas.codeverse.services.ContestService;
import com.adityavikas.codeverse.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.coyote.Response;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@RestController
@RequestMapping("/admin")
@Tag(name="All Admin API's",description = "this is the admin controller where only admin can do the necessary change to our codeverse system application not used is allowed.")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private ContestService contestService;

    @Autowired
    private Middlewares middlewares;

    @Operation(summary = "this is used to create admin (Note: only a admin can create other admin)")
    // only one admin can create other admin not user is permitted to call this you can try it by yourself
    @PostMapping("/create-admin")
    public ResponseEntity<?> createAdmin(@RequestBody AdminDTO adminDTO){
        try{
            User admin = new User();
            admin.setEmail(adminDTO.getEmail());
            admin.setUsername(adminDTO.getUsername());
            admin.setPassword(adminDTO.getPassword());
            admin.setRoles(List.of("ADMIN","USER"));   //giving both provisions
            boolean isAdminCreated = userService.saveUserWithBcryptPassword(admin);
            if(isAdminCreated){
                return new ResponseEntity<>("Admin created successfully...", HttpStatus.OK);
            }
            return new ResponseEntity<>("Admin not created", HttpStatus.BAD_GATEWAY);
        }
        catch(Exception e){
            return new ResponseEntity<>("Admin not created", HttpStatus.BAD_GATEWAY);
        }
    }

    @Operation(summary = "This is the end point used to retrieve all user details")
    @GetMapping("/fetchUsers")
    public ResponseEntity<?> fetchAllUsers(){
        try{
            List<User> allUsers = userService.getAllUsers();
            List<AllUserAPIResponseDTO> response = new ArrayList<>();
            for(User user : allUsers){
                LocalDateTime dt = user.getCreated_at();
                String month = dt.getDayOfMonth() + " " + dt.getMonth().toString() + ", " + dt.getYear();
                boolean isAdmin = user.getRoles().stream().anyMatch(u -> "ADMIN".equalsIgnoreCase(u));
                response.add(new AllUserAPIResponseDTO(user.getUsername(), user.getEmail(), isAdmin,month));
            }
            if(allUsers.isEmpty()){
                return new ResponseEntity<>(null,HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(response,HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "This endpoint is used to add a contest for users")
    @PostMapping("/createContest")
    public ResponseEntity<?> addNewContest(@RequestBody Contest contest){
        Map<String,Integer> response = new HashMap<>();
        response.put("status",0);
        try{
            boolean isAdded = contestService.addContest(contest);
            if(isAdded){
                response.put("status",1);
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Operation(summary = "This endpoint is used to delete a contest by contestname")
    @DeleteMapping("/deleteContestByContestName/{contestName}")
    public ResponseEntity<?> deleteContestByContestName(@PathVariable String contestName){
        Map<String,Integer> response = new HashMap<>();
        response.put("status",0);
        try{
            boolean isDeleted = contestService.deleteContestByContestName(contestName);
            if(isDeleted){
                response.put("status",1);
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Operation(summary = "This endpoint is used to modify the contest by contestname")
    @PutMapping("/updateContest/{contestName}")
    public ResponseEntity<?> updateContest(@PathVariable String contestName,@RequestBody Contest contest){
        Map<String,Integer> response = new HashMap<>();
        response.put("status",0);
        try{
            boolean isUpdated = contestService.updateContest(contestName,contest);
            if(isUpdated){
                response.put("status",1);
                return new ResponseEntity<>(response,HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
            }
        }
        catch (Exception e){
            return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "This API Endpoint is used to delete the contest based on contest id by the admin")
    @DeleteMapping("/deleteContestByContestId/{contestId}")
    public ResponseEntity<?> deleteContestByContestId(@PathVariable String contestId){
        ObjectId contestObjectId = new ObjectId(contestId);
        Map<String,Integer> returnResponse = new HashMap<>();
        returnResponse.put("status",0);
        boolean isDeleted = contestService.deleteContest(contestObjectId);
        if(isDeleted){
            returnResponse.put("status",1);
            return new ResponseEntity<>(returnResponse,HttpStatus.OK);
        }
        return new ResponseEntity<>(returnResponse,HttpStatus.BAD_REQUEST);
    }

    @Operation(summary = "This API Enpoint is used to ban the user")
    @DeleteMapping("/banUser")
    public ResponseEntity<?> banUser(HttpServletRequest request){
        Map<String,Integer> returnResponse = new HashMap<>();
        returnResponse.put("status",0);

        try{
            String authorizationHeader = request.getHeader("Authorization");
            String userId = middlewares.getUserIdByJwt(authorizationHeader);
            boolean isBanned = userService.banUser(new ObjectId(userId));
            if(isBanned){
                returnResponse.put("status",1);
                return new ResponseEntity<>(returnResponse,HttpStatus.OK);
            }
            return new ResponseEntity<>(returnResponse,HttpStatus.BAD_REQUEST);
        }
        catch(Exception e){
            return new ResponseEntity<>(returnResponse,HttpStatus.BAD_REQUEST);
        }
    }

}

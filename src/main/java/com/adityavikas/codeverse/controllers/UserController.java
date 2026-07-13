package com.adityavikas.codeverse.controllers;

import com.adityavikas.codeverse.dto.APIResponseDTO;
import com.adityavikas.codeverse.dto.DSAContentDTO;
import com.adityavikas.codeverse.entity.DsaHeader;
import com.adityavikas.codeverse.entity.DsaTemplate;
import com.adityavikas.codeverse.entity.DsaTitle;
import com.adityavikas.codeverse.entity.User;
import com.adityavikas.codeverse.middleware.Middlewares;
import com.adityavikas.codeverse.repository.DsaTemplateRepository;
import com.adityavikas.codeverse.repository.DsaTemplateRepositoryImpl;
import com.adityavikas.codeverse.repository.DsaTitleRepositoryImpl;
import com.adityavikas.codeverse.repository.UserRepository;
import com.adityavikas.codeverse.services.DsaHeaderService;
import com.adityavikas.codeverse.services.DsaTemplateService;
import com.adityavikas.codeverse.services.UserService;
import com.adityavikas.codeverse.utils.JwtUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RestController
@Tag(name = "All User API's",description = "This is the user controller used to execute all user functionalities after authenticating through" +
        "the JWT token getting by successful login")
@RequestMapping("/user")
public class UserController {


    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserService userService;

    @Autowired
    private DsaTitleRepositoryImpl dsaTitleRepositoryImpl;

    @Autowired
    private DsaHeaderService dsaHeaderService;

    @Autowired
    private DsaTemplateRepositoryImpl dsaTemplateRepositoryImpl;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DsaTemplateService dsaTemplateService;

    @Autowired
    private Middlewares middlewares;

    @Operation(summary = "to check working based on authentication token")
    @GetMapping("/isWorking")
    public ResponseEntity<?> isWorking(){
        return ResponseEntity.ok("It is working !");
    }

    @Operation(summary = "this is used to retrieve the current user details using jwt token")
    @GetMapping("/currentUser")
    public ResponseEntity<?> getCurrentUser(HttpServletRequest httpServletRequest){
        try{
            String authorizationHeader = httpServletRequest.getHeader("authorization");
            User user = null;

            if(authorizationHeader!=null && authorizationHeader.startsWith("Bearer ")){
                String jwt = authorizationHeader.substring(7);
                String userId = jwtUtils.extractUserId(jwt);
                user = userService.getUserById(userId);
            }else{
                throw new Exception("Invalid Jwt token");
            }



            APIResponseDTO apiResponse = new APIResponseDTO(user.getEmail(),user.getUsername(),user.getRoles());

            return new ResponseEntity<>(apiResponse,HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>("jwt token is incorrect",HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/markDSAContentCompleted/{subtitleSlug}")
    @Operation(summary="This API Endpoint is used to mark the DSA Content Completed")
    public ResponseEntity<?> addToCompletedStatus(@PathVariable String subtitleSlug,HttpServletRequest header){

        String authorizationHeader = header.getHeader("authorization");
        User registeredUser = middlewares.getUserByJwt(authorizationHeader);
        HashMap<String,Integer> returnResponse = new HashMap<>();
        returnResponse.put("status",0);

        boolean isCompletedMarked = dsaTemplateService.addToCompletedStatus(registeredUser,subtitleSlug);

        if(isCompletedMarked){
            returnResponse.put("status",1);
        }

        return ResponseEntity.ok(returnResponse);
    }

    @PutMapping("/markDSAContentInCompleted/{subtitleSlug}")
    @Operation(summary="This API Endpoint is used to unmark the DSA Content Completed")
    public ResponseEntity<?> deleteFromCompletedStatus(@PathVariable String subtitleSlug,HttpServletRequest header){

        String authorizationHeader = header.getHeader("authorization");
        User registeredUser = middlewares.getUserByJwt(authorizationHeader);
        HashMap<String,Integer> returnResponse = new HashMap<>();

        returnResponse.put("status",0);

        boolean isCompletedUnmarked = dsaTemplateService.removeFromCompletedStatus(registeredUser,subtitleSlug);

        if(isCompletedUnmarked){
            returnResponse.put("status",1);
        }

        return ResponseEntity.ok(returnResponse);
    }

    @Operation(summary = "This API Endpoint is used to display the entire DSA Revision Content (with user logged in)")
    @GetMapping("/getDSAContent")
    public ResponseEntity<?> getDSAContent(HttpServletRequest httpRequest){
        List<DsaHeader> allHeaders = dsaHeaderService.getAllHeaders();
        Map<String, DSAContentDTO> returnResponse = new HashMap<>();

        User user = middlewares.getUserByJwt(httpRequest.getHeader("Authorization"));
        List<String> completedSlugs = new ArrayList<>();

        if(user!=null){
            completedSlugs = user.getCompletedSlugs();
        }

        for(var currHeader : allHeaders){

            String categoryId = currHeader.getHeaderId();
            List<DsaTitle> allTitleByCategoryId = dsaTitleRepositoryImpl.getAllTitleByCategoryId(categoryId);
            DSAContentDTO dsaContentDTO = new DSAContentDTO();

            dsaContentDTO.setTitle(currHeader.getTitle());
            dsaContentDTO.setDescription(currHeader.getDescription());

            for(DsaTitle dsaTitle : allTitleByCategoryId){

                String titleId = dsaTitle.getTitleId();
                List<DsaTemplate> dsaTemplates = dsaTemplateRepositoryImpl.getDsaTemplatesByParentId(titleId);
                DSAContentDTO.SpecificAlgoCollection specificAlgoCollection = new DSAContentDTO.SpecificAlgoCollection();

                specificAlgoCollection.setDescription(dsaTitle.getDescription());
                specificAlgoCollection.setTitle(dsaTitle.getTitle());
                specificAlgoCollection.setDifficulty(dsaTitle.getDifficulty());

                for(var currTemplate : dsaTemplates){
                    DSAContentDTO.SpecificTemplate specificTemplate = new DSAContentDTO.SpecificTemplate();
                    specificTemplate.setCpp(currTemplate.getCpp());
                    specificTemplate.setPython(currTemplate.getPython());
                    specificTemplate.setJavascript(currTemplate.getJavascript());
                    specificTemplate.setJava(currTemplate.getJava());
                    specificTemplate.setProblemLinks(currTemplate.getProblemLinks());
                    specificTemplate.setVideoLinks(currTemplate.getVideoLinks());
                    specificTemplate.setTitle(currTemplate.getTitle());
                    specificTemplate.setId(currTemplate.getId());
                    // newly added
                    if(completedSlugs.contains(currTemplate.getTemplateId())){
                        specificTemplate.setStatus(true);
                    }
                    else{
                        specificTemplate.setStatus(false);
                    }
                    specificAlgoCollection.getCodeTemplates().put(currTemplate.getTemplateId(),specificTemplate);

                }

                dsaContentDTO.getTopics().put(dsaTitle.getTitleId(),specificAlgoCollection);

            }

            returnResponse.put(categoryId,dsaContentDTO);

        }

        return new ResponseEntity<>(returnResponse,HttpStatus.OK);
    }


}

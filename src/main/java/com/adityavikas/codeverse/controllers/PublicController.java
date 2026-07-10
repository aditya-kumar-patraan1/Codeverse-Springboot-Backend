package com.adityavikas.codeverse.controllers;
import com.adityavikas.codeverse.api.response.JdoodleResponse;
import com.adityavikas.codeverse.dto.*;
import com.adityavikas.codeverse.entity.*;
import com.adityavikas.codeverse.middleware.Middlewares;
import com.adityavikas.codeverse.repository.DsaTemplateRepositoryImpl;
import com.adityavikas.codeverse.repository.DsaTitleRepositoryImpl;
import com.adityavikas.codeverse.repository.UserRepository;
import com.adityavikas.codeverse.services.*;
import com.adityavikas.codeverse.utils.CodeExecutionUtils;
import com.adityavikas.codeverse.utils.JwtUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/public")
@Tag(name = "All Public API's",description = "This is the public controller used to check health of API connection,Registering and login user")
public class PublicController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private Middlewares middlewares;

    @Autowired
    private PublicService publicService;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserProfileService userProfileService;

    @Autowired
    private ContestService contestService;

    @Autowired
    private ProblemService problemService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProblemDetailService problemDetailService;

    @Autowired
    private TestcaseService testcaseService;

    private final DsaTitleService dsaTitleService;
    private final DsaHeaderService dsaHeaderService;
    private final DsaTemplateService dsaTemplateService;

    @Autowired
    private DsaTitleRepositoryImpl dsaTitleRepositoryImpl;

    @Autowired
    private DsaTemplateRepositoryImpl dsaTemplateRepositoryImpl;

    @Autowired
    private CodeExecutionUtils codeExecutionUtils;

    @Autowired
    private ContestProblemService contestProblemService;

    // new way to use service without @Autowired
    public PublicController(DsaTitleService dsaTitleService,DsaTemplateService dsaTemplateService,DsaHeaderService dsaHeaderService){
        this.dsaTemplateService = dsaTemplateService;
        this.dsaTitleService = dsaTitleService;
        this.dsaHeaderService = dsaHeaderService;
    }

    @Operation(summary = "To check API health")
    @GetMapping("/health-check")
    public ResponseEntity<?> checkHealth(){
        return ResponseEntity.ok(List.of("Hey !","It's","Working"));
    }

    @Operation(summary = "to register user to codeverse")
    @PostMapping("/register")
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
        returnResponse.put("status",0);
        try{
            User dbUser = userRepository.findByUsername(user.getUsername());
            if(dbUser.isBan()){
                returnResponse.put("status",-1);
                return new ResponseEntity<>(returnResponse,HttpStatus.OK);
            }
            ObjectId Id = dbUser.getUserId();
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
    @GetMapping("/fetchProblemDetail/{problemId}")
    public ResponseEntity<?> fetchProblemDetail(@PathVariable String problemId){
        Map<String,Object> returnResponse = new HashMap<>();
        returnResponse.put("status",0);
        returnResponse.put("data",null);
        ProblemDetails problemDetails = problemDetailService.fetchProblemDetail(problemId);
        if(problemDetails!=null){
            returnResponse.put("status",1);
            returnResponse.put("data",problemDetails);
            return new ResponseEntity<>(returnResponse,HttpStatus.OK);
        }
        return new ResponseEntity<>(returnResponse,HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "This API Endpoint is used to fetch all the contests")
    @GetMapping("/fetchAllContest")
    public ResponseEntity<?> fetchAllContests(){
        List<Contest> allContests = contestService.getAllContest();
        if(allContests!=null && !allContests.isEmpty()){
            return new ResponseEntity<>(allContests,HttpStatus.OK);
        }
        return new ResponseEntity<>(null,HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "This API Endpoint is used to access Entire Problem")
    @GetMapping("/getEntireProblem/{problemId}")
    public ResponseEntity<?> getEntireProblem(@PathVariable String problemId){
        ProblemResponseDTO specificProblemData = publicService.getSpecificProblemData(problemId);
        if(specificProblemData!=null){
            return new ResponseEntity<>(specificProblemData,HttpStatus.OK);
        }
        return new ResponseEntity<>(null,HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "This is used to fetch all Problems (including it's detail & testcases)")
    @GetMapping("/fetchAllProblem")
    public ResponseEntity<?> fetchAllProblems(){
        try{
            List<Problem> allProblems = problemService.fetchAllProblems();
            List<ProblemResponseDTO> result = new ArrayList<>();

            for(Problem problem : allProblems){
                result.add(publicService.getSpecificProblemData(problem.getId().toString()));
            }
            if(allProblems.isEmpty()){
                return new ResponseEntity<>(null,HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(result,HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "This API Endpoint is used to display the entire DSA Revision Content (with user logged in)")
    @GetMapping("/getDSAContent")
    public ResponseEntity<?> getDSAContent(){
        List<DsaHeader> allHeaders = dsaHeaderService.getAllHeaders();
        Map<String,DSAContentDTO> returnResponse = new HashMap<>();

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
                    specificTemplate.setStatus(false);
                    specificAlgoCollection.getCodeTemplates().put(currTemplate.getTemplateId(),specificTemplate);

                }

                dsaContentDTO.getTopics().put(dsaTitle.getTitleId(),specificAlgoCollection);

            }

            returnResponse.put(categoryId,dsaContentDTO);

        }

        return new ResponseEntity<>(returnResponse,HttpStatus.OK);
    }

    @Operation(summary = "This API Endpoint is used to test the updation of contest problem")
    @PutMapping("/test-update-contest-problem/{problemId}")
    public ResponseEntity<?> updateContestProblem(@PathVariable String problemId,@RequestBody ContestProblemDTO updatedContestProblem){
        Map<String,Integer> returnResponse = new HashMap<>();
        returnResponse.put("status",0);
        boolean isContestProblemUpdated = contestProblemService.updateContestProblem(problemId, updatedContestProblem);
        if(isContestProblemUpdated){
            returnResponse.put("status",1);
        }
        return ResponseEntity.ok(returnResponse);
    }

    @Operation(summary = "This API Endpoint is used to test add Contest Problem")
    @PostMapping("/test-add-contest-problem/{contestId}")
    public ResponseEntity<?> addContestProblem(@PathVariable String contestId,@RequestBody ContestProblemDTO contestProblemDTO){
        boolean isContestProblemAdded = contestProblemService.addContestProblem(new ObjectId(contestId), contestProblemDTO);
        Map<String,Integer> returnResponse = new HashMap<>();
        returnResponse.put("status",0);
        if(isContestProblemAdded){
            returnResponse.put("status",1);
        }
        return ResponseEntity.ok(returnResponse);
    }

    @PostMapping("/test-code-doodle")
    @Operation(summary = "This API Endpoint is used to test the Jdoodle Code runner")
    public ResponseEntity<?> testJdoodleResponse(@RequestBody ExecuteRequest executeRequest){
        JdoodleResponse jdoodleResponse = codeExecutionUtils.runJdoodleCode(executeRequest);
        return ResponseEntity.ok(jdoodleResponse);
    }

    @GetMapping("/test-get-all-contest-problem/{contestId}")
    @Operation(summary = "This API Endpoint is used to test the fetching of all Contest Problem")
    public ResponseEntity<?> getAllContestProblem(@PathVariable String contestId){
        List<ContestProblemResponseDTO> allContestProblems = contestProblemService.getAllContestProblems(contestId);
        return ResponseEntity.ok(allContestProblems);
    }



}

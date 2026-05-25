package com.adityavikas.codeverse.controllers;
import com.adityavikas.codeverse.dto.*;
import com.adityavikas.codeverse.entity.*;
import com.adityavikas.codeverse.repository.DsaTitleRepositoryImpl;
import com.adityavikas.codeverse.repository.UserRepository;
import com.adityavikas.codeverse.services.*;
import com.adityavikas.codeverse.utils.JwtUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<?> fetchAll(){
        List<Contest> allContests = contestService.getAllContest();
        if(allContests!=null && !allContests.isEmpty()){
            return new ResponseEntity<>(allContests,HttpStatus.OK);
        }
        return new ResponseEntity<>(null,HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "This API Endpoint is used to access Entire Problem")
    @GetMapping("/getEntireProblem/{problemId}")
    public ResponseEntity<?> getEntireProblem(@PathVariable String problemId){
        Problem problem = problemService.fetchProblem(problemId).orElse(null);
        ProblemDetails problemDetails = problemDetailService.fetchProblemDetail(problemId);
        List<Testcase> testcases = testcaseService.fetchTestcase(problemId);

        if(problem!=null){

            ProblemDTO problemDTO = new ProblemDTO();
            problemDTO.setAcceptanceRate(problem.getAcceptanceRate());
            problemDTO.setInputType(problem.getInputType());
            problemDTO.setReturnType(problem.getReturnType());
            problemDTO.setFunctionName(problem.getFunctionName());
            problemDTO.setDifficulty(problem.getDifficulty());
            problemDTO.setStatus(problem.isStatus());
            problemDTO.setSlug(problem.getSlug());
            problemDTO.setTitle(problem.getTitle());
            problemDTO.setSno(problem.getSno());

            List<String> topicTags = problem.getTopicTags();

            String topics = "";

            for(int i=0;i< topicTags.size();i++){
                topics = topics + topicTags.get(i);
                if(i < topicTags.size()-1){
                    topics+=',';
                }
            }

            problemDTO.setTopicTags(topics);
            problemDTO.setDescription(problemDetails.getDescription());
            problemDTO.setTemplates(problemDetails.getTemplates());
            problemDTO.setSolutions(problemDetails.getSolutions());
            problemDTO.setTimeComplexity(problemDetails.getTimeComplexity());
            problemDTO.setSpaceComplexity(problemDetails.getSpaceComplexity());
            problemDTO.setEditorial(problemDetails.getEditorial());
            problemDTO.setDescription(problemDetails.getDescription());
            problemDTO.setAlgorithmSteps(problemDetails.getAlgorithmSteps());


            List<TestcaseDTO> listOfTestcase = new ArrayList<>();

            for(int i=0;i< testcases.size();i++){
                Testcase testcase = testcases.get(i);
                TestcaseDTO testcaseDTO = new TestcaseDTO();
                testcaseDTO.setInput(testcase.getInput());
                testcaseDTO.setHidden(testcase.isHidden());
                // output added
                testcaseDTO.setOutput(testcase.getOutput());
                testcaseDTO.setExplanation(testcase.getExplanation());
                listOfTestcase.add(testcaseDTO);
            }

            ProblemResponseDTO problemResponseDTO  = new ProblemResponseDTO();
            problemResponseDTO.setProblemID(problemId);
            problemResponseDTO.setProblemDTO(problemDTO);
            problemResponseDTO.setListOfTestcase(listOfTestcase);

            return new ResponseEntity<>(problemResponseDTO,HttpStatus.OK);
        }

        return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);

    }

    @Operation(summary = "This is used to fetch all Problems")
    @GetMapping("/fetchAllProblem")
    public ResponseEntity<?> fetchAllProblems(){
        try{
            List<Problem> allProblems = problemService.fetchAllProblems();
            if(allProblems.isEmpty()){
                return new ResponseEntity<>(null,HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(allProblems,HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "This API Endpoint is used to display the entire DSA Revision Content")
    @GetMapping("/getDSAContent")
    public ResponseEntity<?> getDSAContent(){
        List<DsaTemplate> allTemplates = dsaTemplateService.getTemplates();
//        List<DsaHeader> allHeaders = dsaHeaderService.getAllHeaders();
        Map<String,DSAContentDTO> returnResponse = new HashMap<>();


//        for(var currHeader : allHeaders){

//            String categoryId = currHeader.getHeaderId();
//            List<DsaTitle> allTitleByCategoryId = dsaTitleRepositoryImpl.getAllTitleByCategoryId(categoryId);

//            for(DsaTitle dsaTitle : allTitleByCategoryId){
//                dsaTitle.get
//
//
//
//            }

//            returnResponse.put(categoryId,);

//        }

        for(var template : allTemplates){


            // accessing parent ID
            String parentId = template.getParentId();
            // access title document
            DsaTitle dsaTitle = dsaTitleService.findByTitleId(parentId);
//            access category document
            DsaHeader dsaHeader = dsaHeaderService.findDsaHeaderByHeaderId(dsaTitle.getCategoryId());
//            access heading ID
            String headerId = dsaHeader.getHeaderId();


            // creating template
            DSAContentDTO.SpecificTemplate specificTemplate = new DSAContentDTO.SpecificTemplate();
            specificTemplate.setVideoLinks(template.getVideoLinks());
            specificTemplate.setTitle(template.getTitle());
            specificTemplate.setProblemLinks(template.getProblemLinks());
            specificTemplate.setJava(template.getJava());
            specificTemplate.setPython(template.getPython());
            specificTemplate.setCpp(template.getCpp());
            specificTemplate.setJavascript(template.getJavascript());



            if(returnResponse.containsKey(headerId)){
                DSAContentDTO dsaContentDTO;
                dsaContentDTO = returnResponse.get(headerId);
                if(dsaContentDTO.getTopics().get(parentId)!=null){
                    dsaContentDTO.getTopics().get(parentId).getCodeTemplates().put(template.getTemplateId(),specificTemplate);
                }
                else{
                    DSAContentDTO.SpecificAlgoCollection specificAlgoCollection = new DSAContentDTO.SpecificAlgoCollection();
                    specificAlgoCollection.setTitle(dsaTitle.getTitle());
                    specificAlgoCollection.setDescription(dsaTitle.getDescription());
                    specificAlgoCollection.setDifficulty(dsaTitle.getDifficulty());
                    specificAlgoCollection.getCodeTemplates().put(template.getTemplateId(),specificTemplate);
                    dsaContentDTO.getTopics().put(parentId,specificAlgoCollection);
                }
                returnResponse.put(headerId,dsaContentDTO);
            }
            else{
                DSAContentDTO dsaContentDTO = new DSAContentDTO();
                dsaContentDTO.setTitle(dsaHeader.getTitle());
                dsaContentDTO.setDescription(dsaHeader.getDescription());

                // create specificAlgoCollection
                DSAContentDTO.SpecificAlgoCollection specificAlgoCollection = new DSAContentDTO.SpecificAlgoCollection();
                specificAlgoCollection.setTitle(dsaTitle.getTitle());
                specificAlgoCollection.setDescription(dsaTitle.getDescription());
                specificAlgoCollection.setDifficulty(dsaTitle.getDifficulty());
                specificAlgoCollection.getCodeTemplates().put(template.getTemplateId(),specificTemplate);
                dsaContentDTO.getTopics().put(parentId,specificAlgoCollection);

                returnResponse.put(headerId,dsaContentDTO);
            }
        }
        return new ResponseEntity<>(returnResponse,HttpStatus.OK);
    }

}

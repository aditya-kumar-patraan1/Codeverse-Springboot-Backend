package com.adityavikas.codeverse.utils;

import com.adityavikas.codeverse.api.response.JdoodleResponse;
import com.adityavikas.codeverse.cache.AppCache;
import com.adityavikas.codeverse.dto.ExecuteRequest;
import com.adityavikas.codeverse.dto.JdoodleRequestDTO;
import com.adityavikas.codeverse.dto.LanguageFormatDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class CodeExecutionUtils {

    @Value("${jdoodle.client.id}")
    private String JDOODLE_CLIENT_ID;

    @Value("${jdoodle.client.secret}")
    private String JDOODLE_CLIENT_SECRET;

    RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private AppCache appCache;

    public LanguageFormatDTO getJdoodleConfig(String language){

        List<List<String>> languages = new ArrayList<>();
        languages.add(new ArrayList<>(List.of("python","python3")));
        languages.add(new ArrayList<>(List.of("cpp","c++")));
        languages.add(new ArrayList<>(List.of("js","javascript")));
        languages.add(new ArrayList<>(List.of("java")));

        int idx=0;

        for(var currList : languages){
            if(currList.contains(language.toLowerCase())){
                break;
            }
            idx++;
        }

        List<List<String>> versionData = new ArrayList<>();
        versionData.add(List.of("python3","3"));
        versionData.add(List.of("cpp","5"));
        versionData.add(List.of("nodejs","4"));
        versionData.add(List.of("java","4"));

        List<String> finalData = versionData.get(idx);

        return new LanguageFormatDTO(finalData.get(0), finalData.get(1));

    }

    public JdoodleResponse runJdoodleCode(ExecuteRequest executeRequest){
        LanguageFormatDTO config = getJdoodleConfig(executeRequest.getLanguage());

        try{
            return executeUserCode(JDOODLE_CLIENT_ID,JDOODLE_CLIENT_SECRET,executeRequest.getUserCode(),config);
        }
        catch(HttpClientErrorException e){
            log.error("Some error occurred while executing user code");
            return null;
        }

    }

    public JdoodleResponse executeUserCode(String clientId,String clientSecret,String userCode,LanguageFormatDTO config){

        JdoodleRequestDTO JdoodleRequest = new JdoodleRequestDTO(
                clientId,
                clientSecret,
                userCode,
                config.language(),
                config.versionIndex()
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<JdoodleRequestDTO> entity = new HttpEntity<>(JdoodleRequest,headers);

        String jdoodleURL = appCache.apiCache.get(AppCache.Keys.JDOODLE_API_URL.toString());

        ResponseEntity<JdoodleResponse> JdoodleResponse = restTemplate.exchange(
                jdoodleURL,
                HttpMethod.POST,
                entity,
                JdoodleResponse.class
        );

        return JdoodleResponse.getBody();

    }

}

package com.adityavikas.codeverse.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/google/auth")
public class GoogleAuthController {

    @Value("${google.auth.client_id}")
    private String clientId;

    @Value("${google.auth.client_secret}")
    private String clientSecret;

    @Value("${google.auth.redirect_uri}")
    private String redirectUI;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/callback")
    public ResponseEntity<?> handleGoogleCallback(@RequestParam String authCode){
        try{
            // 1.exchanging the authentication code with the token
            String tokenEndpoint = "https://oauth2.googleapis.com/token";
            MultiValueMap<String,String> params = new LinkedMultiValueMap<>();
            params.add("client_id",clientId);
            params.add("client_secret",clientSecret);
            params.add("redirect_uri",redirectUI);
            params.add("code",authCode);
            params.add("grant_type","authorization_code");

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            HttpEntity<MultiValueMap<String,String>> request = new HttpEntity<>(params,httpHeaders);

            ResponseEntity<Map> tokenResponse = restTemplate.postForEntity(tokenEndpoint, request, Map.class);
            String idToken = tokenResponse.getBody().get("id_token").toString();
            System.out.println(idToken);
            return new ResponseEntity<>(idToken, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>("", HttpStatus.BAD_REQUEST);
        }
    }

}

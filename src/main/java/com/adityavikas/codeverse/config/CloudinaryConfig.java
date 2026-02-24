package com.adityavikas.codeverse.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {

    @Value("${cloud.cloud-name}")
    private String CloudName;

    @Value("${cloud.api-key}")
    private String CloudinaryApiKey;

    @Value("${cloud.api-secret}")
    private String CloudinaryApiSecret;

    @Bean
    public Cloudinary cloudinary(){
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name",CloudName,
                "api_key",CloudinaryApiKey,
                "api_secret",CloudinaryApiSecret
        ));
    }

}

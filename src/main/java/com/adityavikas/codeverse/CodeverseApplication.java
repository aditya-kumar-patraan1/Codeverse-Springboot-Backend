package com.adityavikas.codeverse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableTransactionManagement
public class CodeverseApplication {

	public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(CodeverseApplication.class,args);
        System.out.println(context.getEnvironment());
  	}

    @Bean
    public PlatformTransactionManager falana(MongoDatabaseFactory factory){
        return new MongoTransactionManager(factory);
    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

}

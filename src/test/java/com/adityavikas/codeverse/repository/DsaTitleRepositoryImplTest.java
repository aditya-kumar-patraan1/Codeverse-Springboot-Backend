package com.adityavikas.codeverse.repository;

import com.adityavikas.codeverse.entity.DsaTitle;
import io.swagger.v3.oas.annotations.Parameter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@SpringBootTest
public class DsaTitleRepositoryImplTest {

    @Autowired
    private DsaTitleRepositoryImpl dsaTitleRepository;

    @ParameterizedTest
    @ValueSource(
            strings = {
                    "advanced-topics",
                    "algorithms",
                    "algorithm"
            }
    )
     void testingTitleByCategoryId(String categoryId){
        List<DsaTitle> titlesList = dsaTitleRepository.getAllTitleByCategoryId(categoryId);
        Assertions.assertNotNull(titlesList);
    }

}

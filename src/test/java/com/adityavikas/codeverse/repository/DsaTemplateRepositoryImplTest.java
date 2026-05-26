package com.adityavikas.codeverse.repository;

import com.adityavikas.codeverse.entity.DsaTemplate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class DsaTemplateRepositoryImplTest {

    @Autowired
    private DsaTemplateRepositoryImpl dsaTemplateRepositoryImpl;

    @ParameterizedTest
    @ValueSource(
            strings = {
                    "graphs",
                    "square-decomposition-technique",
                    "graph"
            }
    )
    void testDsaTemplateByParentId(String parentId){
        List<DsaTemplate> dsaTemplatesByParentId = dsaTemplateRepositoryImpl.getDsaTemplatesByParentId(parentId);
        System.out.println(dsaTemplatesByParentId);
        Assertions.assertNotEquals(0,dsaTemplatesByParentId.size());
    }

}

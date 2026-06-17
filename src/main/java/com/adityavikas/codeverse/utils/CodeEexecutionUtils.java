package com.adityavikas.codeverse.utils;

import com.adityavikas.codeverse.dto.LanguageFormatDTO;

import java.util.ArrayList;
import java.util.List;

public class CodeEexecutionUtils {

    public LanguageFormatDTO getJdoodleConfig(String language){

        List<List<String>> listofLanguages = new ArrayList<>();
        listofLanguages.add(new ArrayList<>(List.of("python","python3")));
        listofLanguages.add(new ArrayList<>(List.of("cpp","c++")));
        listofLanguages.add(new ArrayList<>(List.of("js","javascript")));
        listofLanguages.add(new ArrayList<>(List.of("java")));

        int idx=0;

        for(var currList : listofLanguages){
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

}

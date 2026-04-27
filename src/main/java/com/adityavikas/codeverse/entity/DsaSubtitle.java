package com.adityavikas.codeverse.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "DsaSubtitle")
public class DsaSubtitle {

    private String parentTitleId;
    private String title;
    private String level;
    private String icon;

}

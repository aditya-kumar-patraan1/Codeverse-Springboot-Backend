package com.adityavikas.codeverse.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NonNull;
import org.springframework.data.mongodb.core.index.Indexed;

@Data
public class UserDTO {

    @Schema(description = "the user's username")
    private String username;
    private String password;

}

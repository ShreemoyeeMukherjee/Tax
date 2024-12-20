package com.sm.tax.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestDTO {
    // the annotations automatically validates input from user
    
    private String name;
    
    private String email;
    
    private String role;
    
    private String password;
    
}


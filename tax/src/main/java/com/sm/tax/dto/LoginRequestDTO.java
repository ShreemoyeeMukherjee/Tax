package com.sm.tax.dto;

import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDTO {
    
    @NotBlank
    private String email;
    
    @NotBlank
    private String password;
    
    
}

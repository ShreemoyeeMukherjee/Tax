package com.sm.tax.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateTaxRequestDTO {
    @NotBlank
    private String taxtype;
    @NotBlank
    private Double  amount;
    
}

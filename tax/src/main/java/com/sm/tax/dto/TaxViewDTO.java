package com.sm.tax.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaxViewDTO {
    private String taxtype;
    private Double amount;
    private Long user_id;
    
}

package com.sm.tax.models;

import org.springframework.stereotype.Component;

import jakarta.annotation.Generated;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Component
public class TaxReport {
    @Id
    @GeneratedValue
    private Long id;
    private String fileName;
    private String name;
    //private String originalFileName;
    private String description;
    private String  link;
    @OneToOne(cascade = CascadeType.ALL) 
    @JoinColumn(name = "tax_id", referencedColumnName = "id")
    private  Tax tax;

    
}

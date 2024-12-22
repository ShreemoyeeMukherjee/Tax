package com.sm.tax.models;

import org.springframework.stereotype.Component;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Component
public class Tax {
    @Id
    @GeneratedValue
    private Long id;
    private String taxtype;
    private Double  amount;
    @ManyToOne
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private User_information user;
    @OneToOne(mappedBy =  "tax")
    private TaxReport  taxreport;
    //private String link;

    
}

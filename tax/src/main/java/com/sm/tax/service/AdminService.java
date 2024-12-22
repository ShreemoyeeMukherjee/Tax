package com.sm.tax.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sm.tax.models.Tax;
import com.sm.tax.repository.TaxRepository;
import com.sm.tax.dto.TaxReportViewDTO;
import com.sm.tax.dto.TaxViewDTO;

@Service
public class AdminService {
    @Autowired
    private Tax tax;
    @Autowired
    private TaxRepository taxRepository;
    public List<TaxViewDTO> ViewTaxService()
    {
      List<Tax> all_taxes = taxRepository.findAll();
      List<TaxViewDTO> all_tax_view = new ArrayList<>();
      for(Tax tax:all_taxes)
      {
        System.out.println(tax.getUser());
        System.out.println(tax.getTaxtype());
          TaxViewDTO taxviewdto = new TaxViewDTO();
          taxviewdto.setAmount(tax.getAmount());
          taxviewdto.setTaxtype(tax.getTaxtype());
          //taxviewdto.setUser_id(tax.getUser_id());
          
          //taxviewdto.setUser_email(tax.getUser().getEmail());
          all_tax_view.add(taxviewdto);
      }
      return(all_tax_view);
    }
   
    

    
}

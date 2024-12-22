package com.sm.tax.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sm.tax.models.TaxReport;
@Repository
public interface TaxReportRepository  extends JpaRepository<TaxReport,Long>{
    
   Optional<TaxReport>findByTax_id(Long tax_id);
}

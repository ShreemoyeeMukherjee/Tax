package com.sm.tax.controller;

import org.springframework.web.bind.annotation.RestController;

import com.sm.tax.dto.TaxReportViewDTO;
import com.sm.tax.dto.TaxViewDTO;
import com.sm.tax.models.TaxReport;
import com.sm.tax.repository.TaxReportRepository;
import com.sm.tax.service.AdminService;
import com.sm.tax.utils.AppUtil;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.Optional;
import org.springframework.web.bind.annotation.RequestMapping;



@RestController
@RequestMapping("/admin")
public class AdminController {
    static final String PHOTOS_FOLDER_NAME = "photos";
    @Autowired
    private AdminService adminService;
    @Autowired
    TaxReportRepository taxReportRepository;

    @Autowired
    TaxReport taxReport;
   
    @GetMapping("/view-tax")
    public  ResponseEntity<List<TaxViewDTO>> ViewAllTax()
    {
           List<TaxViewDTO> all_tax_view = adminService.ViewTaxService();
           return(ResponseEntity.ok().body(all_tax_view));
    }
    @GetMapping("/view-tax-report/{tax_id}")
     public ResponseEntity<?>ViewTaxReport(@PathVariable Long tax_id,Authentication authentication)
    {
            
        Optional<TaxReport> optional_tax_report = taxReportRepository.findByTax_id(tax_id);
        if(optional_tax_report.isPresent() == false)
        {
              return(ResponseEntity.badRequest().body("Tax_id is not correct"));
        }
        TaxReport tax_report = optional_tax_report.get();
        
        Resource resource = null;
                try {
                    
                    resource = AppUtil.getFileAsResource(tax_id, PHOTOS_FOLDER_NAME, tax_report.getFileName());
                   
                } catch (IOException e) {
                    return ResponseEntity.internalServerError().build();
                }
    
                if (resource == null) {
                    return new ResponseEntity<>("File not found", HttpStatus.NOT_FOUND);
                }
    
                String contentType = "application/octet-stream";
                String headerValue = "attachment; filename=\"" +tax_report.getFileName() + "\"";
    
                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(contentType))
                        .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
                        .body(resource);
            } 
        }

    
    
        //   Authentication authentication) {

    //     String email = authentication.getName();
    //     Optional<Account> optionalAccount = accountService.findByEmail(email);
    //     Account account = optionalAccount.get();

    //     Optional<Album> optionaAlbum = albumService.findById(album_id);
    //     Album album;
    //     if (optionaAlbum.isPresent()) {
    //         album = optionaAlbum.get();
    //         if (account.getId() != album.getAccount().getId()) {
    //             return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
    //         }
    //     } else {
    //         return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    //     }

    //     Optional<Photo> optionalPhoto = photoService.findById(photo_id);
    //     if (optionalPhoto.isPresent()) {
    //         Photo photo = optionalPhoto.get();
    //         if (photo.getAlbum().getId() != album_id) {
    //             return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
    //         }
    //         Resource resource = null;
    //         try {
    //             resource = AppUtil.getFileAsResource(album_id, folder_name, photo.getFileName());
    //         } catch (IOException e) {
    //             return ResponseEntity.internalServerError().build();
    //         }

    //         if (resource == null) {
    //             return new ResponseEntity<>("File not found", HttpStatus.NOT_FOUND);
    //         }

    //         String contentType = "application/octet-stream";
    //         String headerValue = "attachment; filename=\"" + photo.getOriginalFileName() + "\"";

    //         return ResponseEntity.ok()
    //                 .contentType(MediaType.parseMediaType(contentType))
    //                 .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
    //                 .body(resource);
    //     } else {
    //         return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    //     }
    // }
    // }

    
    
    


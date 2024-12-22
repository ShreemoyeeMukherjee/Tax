package com.sm.tax.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sm.tax.dto.CreateTaxRequestDTO;
import com.sm.tax.dto.LoginRequestDTO;
import com.sm.tax.dto.TaxReportViewDTO;
import com.sm.tax.dto.UserRequestDTO;
import com.sm.tax.models.Tax;
import com.sm.tax.models.TaxReport;
import com.sm.tax.repository.TaxReportRepository;
import com.sm.tax.repository.TaxRepository;
import com.sm.tax.service.UserService;
import com.sm.tax.utils.AppUtil;
import com.sm.tax.utils.Success;

import io.swagger.v3.oas.annotations.Operation;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")

public class UserController {
    static final String PHOTOS_FOLDER_NAME = "photos";
    @Autowired
    private UserService userservice;
    @Autowired
    private TaxReportRepository taxReportRepository;
    @Autowired
    private TaxRepository taxRepository;
  
    @Autowired
    TaxReport taxReport;
    @PostMapping("/create-user")
    @ApiResponse(responseCode =  "200" , description =  "User created")
    @ApiResponse(responseCode =  "200",description =  "Bad Request")
    @Operation(summary = "Create user")

    public ResponseEntity<String>createUser( @RequestBody UserRequestDTO  userrequest)


    {
        try{
            
            System.out.println(userrequest);
       userservice.createUserService(userrequest) ;
       return( ResponseEntity.ok(Success.User_created.toString()));
        }
        catch(Exception e)
        {
           return(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage()));
        }
    }
   @PostMapping("/login-user")
    public ResponseEntity<String>loginUser(@Valid @RequestBody LoginRequestDTO loginrequest)
    {
        try{
            System.out.println("In user controller");
            String token = userservice.loginUserService(loginrequest);
            return(ResponseEntity.ok().body(token));
    }
    catch(Exception e)
    {
        return(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null));
    }
    }
    @PostMapping("/create-tax")
    public ResponseEntity<String> createUserTax(@Valid @RequestBody CreateTaxRequestDTO taxrequest,Authentication authentication)
    {
        try{
            String response = (userservice.CreateTax(taxrequest,authentication));
            return(ResponseEntity.ok().body(response));
        }
        catch(Exception e)
        {
            return(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage()));
        }
    }
 
    @PostMapping("/{tax_id}/upload-tax-report")
public ResponseEntity<List<HashMap<String, List<?>>>> upload_tax_report(
            @RequestPart(required = true) MultipartFile[] files, @PathVariable Long tax_id,
             Authentication authentication) {

        String email = authentication.getName();
        //System.out.println(files);
        Optional<Tax> optional_existing_tax = taxRepository.findById(tax_id);
        Tax existing_tax;
        if(optional_existing_tax.isPresent())
        { 
            existing_tax = optional_existing_tax.get();
            
        }
        else
        {
            return(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null));
        }
System.out.println("1");
 List<TaxReportViewDTO> fileNamesWithSuccess = new ArrayList<>();
        List<String> fileNamesWithError = new ArrayList<>();

        Arrays.asList(files).stream().forEach(file -> {
            String contentType = file.getContentType();
            if (contentType.equals("image/png")
                    || contentType.equals("image/jpg")
                    || contentType.equals("image/jpeg")) {

                int length = 10;
                boolean useLetters = true;
                boolean useNumbers = true;

                try {
                    
                    String fileName = file.getOriginalFilename();
                    String generatedString = RandomStringUtils.random(length, useLetters, useNumbers);
                    String final_photo_name = generatedString + fileName;
                    String absolute_fileLocation = AppUtil.get_photo_upload_path(final_photo_name, PHOTOS_FOLDER_NAME,
                            tax_id);
                    Path path = Paths.get(absolute_fileLocation);
                    
                    Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                    taxReport.setFileName(final_photo_name);
                    taxReport.setTax(existing_tax);
                    taxReport.setName(fileName);
                    taxReportRepository.save(taxReport);
                    //a new instance is required everytime
                    TaxReportViewDTO photoviewdto  = new TaxReportViewDTO();
                    photoviewdto.setName(fileName);
                    photoviewdto.setTax_id(existing_tax.getId());
                    fileNamesWithSuccess.add(photoviewdto);
                }
                catch(Exception e)
                {
                    fileNamesWithError.add(file.getOriginalFilename());
                }
            }
        }
        );

        // Optional<Account> optionalAccount = accountService.findByEmail(email);
        // Account account = optionalAccount.get();
        // Optional<Album> optionaAlbum = albumService.findById(album_id);
        // Album album;
        // if (optionaAlbum.isPresent()) {
        //     album = optionaAlbum.get();
        //     if (account.getId() != album.getAccount().getId()) {
        //         return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        //     }
        // } else {
        //     return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        // }

        // List<PhotoViewDTO> fileNamesWithSuccess = new ArrayList<>();
        // List<String> fileNamesWithError = new ArrayList<>();

        // Arrays.asList(files).stream().forEach(file -> {
        //     String contentType = file.getContentType();
        //     if (contentType.equals("image/png")
        //             || contentType.equals("image/jpg")
        //             || contentType.equals("image/jpeg")) {

        //         int length = 10;
        //         boolean useLetters = true;
        //         boolean useNumbers = true;

        //         try {
        //             String fileName = file.getOriginalFilename();
        //             String generatedString = RandomStringUtils.random(length, useLetters, useNumbers);
        //             String final_photo_name = generatedString + fileName;
        //             String absolute_fileLocation = AppUtil.get_photo_upload_path(final_photo_name, PHOTOS_FOLDER_NAME,
        //                     album_id);
        //             Path path = Paths.get(absolute_fileLocation);
        //             Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        //             Photo photo = new Photo();
        //             photo.setName(fileName);
        //             photo.setFileName(final_photo_name);
        //             photo.setOriginalFileName(fileName);
        //             photo.setAlbum(album);
        //             photoService.save(photo);

        //             PhotoViewDTO photoViewDTO = new PhotoViewDTO(photo.getId(), photo.getName(), photo.getDesciption());
        //             fileNamesWithSuccess.add(photoViewDTO);
        //             BufferedImage thumbImg = AppUtil.getThumbnail(file, THUMBNAIL_WIDTH);
        //             File thumbnail_location = new File(
        //                     AppUtil.get_photo_upload_path(final_photo_name, THUMBNAIL_FOLDER_NAME, album_id));
        //             ImageIO.write(thumbImg, file.getContentType().split("/")[1], thumbnail_location);

        //         } catch (Exception e) {
        //             log.debug(AlbumError.PHOTO_UPLOAD_ERROR.toString() + ": " + e.getMessage());
        //             fileNamesWithError.add(file.getOriginalFilename());
                //}

            // } else {
            //     fileNamesWithError.add(file.getOriginalFilename());
            // }
        // });

        HashMap<String, List<?>> result = new HashMap<>();
        result.put("SUCCESS", fileNamesWithSuccess);
        result.put("ERRORS", fileNamesWithError);

        List<HashMap<String, List<?>>> response = new ArrayList<>();
        response.add(result);

       return ResponseEntity.ok(response);
       

    }
    

    
    
    
}


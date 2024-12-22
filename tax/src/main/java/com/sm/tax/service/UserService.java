package com.sm.tax.service;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sm.tax.dto.CreateTaxRequestDTO;
//import com.sm.tax.dto.CreateTaxRequestDTO;
import com.sm.tax.dto.LoginRequestDTO;
import com.sm.tax.dto.UserRequestDTO;
import com.sm.tax.models.Tax;
//import com.sm.tax.models.Tax;
import com.sm.tax.models.User_information;
import com.sm.tax.repository.TaxRepository;
//import com.sm.tax.repository.TaxRepository;
import com.sm.tax.repository.UserRepository;
import com.sm.tax.utils.Success;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.JoinColumn;
import lombok.extern.slf4j.Slf4j;

@Service
@Tag(name = "User Controller", description = "Controller for user management")
@Slf4j // Simple Logging Facade for Java 
public class UserService {
    @Autowired
    User_information user;
    @Autowired
    UserRepository userrepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
   private TokenService tokenservice;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    Tax tax;
    @Autowired
    TaxRepository taxrepository;

    public User_information createUserService(UserRequestDTO userrequest)
    {
        
        user.setEmail(userrequest.getEmail());
        user.setName(userrequest.getName());
        user.setRole(userrequest.getRole());
    
        String email = userrequest.getEmail();
        System.out.println(email);
        String password = userrequest.getPassword();
        System.out.println("Password is"+password);
        String hashedpassword = passwordEncoder.encode(password);
        System.out.println("Hashed password"+hashedpassword);
        user.setPassword(hashedpassword);
        
       return(userrepository.save(user));
      


       
    }
    public String loginUserService(LoginRequestDTO loginrequest)
    {
        System.out.println("In user service");
        Authentication authentication = authenticationManager
                    .authenticate(
                            new UsernamePasswordAuthenticationToken(loginrequest.getEmail(), loginrequest.getPassword()));
        String jwt_token = tokenservice.generateToken(authentication);
        return(jwt_token);
    }
    public String  CreateTax(CreateTaxRequestDTO  taxrequest,Authentication authentication) throws Exception
    {
           String email = authentication.getName();
           Optional<User_information>optional_existing_user = userrepository.findByEmail(email);
           if(optional_existing_user.isPresent())
           {
               User_information existinguser = optional_existing_user.get();
//Set Entity: The user object is attached to the Tax entity.
// Persist: When you save the Tax entity:
// Hibernate checks the @JoinColumn annotation.
// It extracts the value of email from the user object (because referencedColumnName = "email").
// It stores this value in the user_email column of the Tax table.

               tax.setAmount(taxrequest.getAmount());
               tax.setTaxtype(taxrequest.getTaxtype());
               tax.setUser(existinguser);
               taxrepository.save(tax);
               return(Success.Tax_created.toString());

           }
           else{
            throw new Exception("User with email not found");
           }
    }
    

    
}
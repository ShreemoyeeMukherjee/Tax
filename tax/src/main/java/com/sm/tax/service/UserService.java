package com.sm.tax.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sm.tax.dto.LoginRequestDTO;
import com.sm.tax.dto.UserRequestDTO;

import com.sm.tax.models.User_information;
import com.sm.tax.repository.UserRepository;

import io.swagger.v3.oas.annotations.tags.Tag;
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
        Authentication authentication = authenticationManager
                    .authenticate(
                            new UsernamePasswordAuthenticationToken(loginrequest.getEmail(), loginrequest.getPassword()));
        String jwt_token = tokenservice.generateToken(authentication);
        return(jwt_token);
    }
    

    
}
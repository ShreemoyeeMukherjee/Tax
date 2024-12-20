package com.sm.tax.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sm.tax.dto.LoginRequestDTO;
import com.sm.tax.dto.UserRequestDTO;
import com.sm.tax.service.UserService;
import com.sm.tax.utils.Success;

import io.swagger.v3.oas.annotations.Operation;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")

public class UserController {
    @Autowired
    private UserService userservice;
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
            String token = userservice.loginUserService(loginrequest);
            return(ResponseEntity.ok().body(token));
    }
    catch(Exception e)
    {
        return(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null));
    }
    }

    
    
    
}


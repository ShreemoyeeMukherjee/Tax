package com.sm.tax.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.sm.tax.models.User_information;
import com.sm.tax.repository.UserRepository;
@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public  UserDetails loadUserByUsername(String email)
    {
       System.out.println("In customerdetails service");
         Optional<User_information> optional_existingUser = userRepository.findByEmail(email);
         if(optional_existingUser.isPresent())
         {
              User_information existing_user = optional_existingUser.get();
              List<GrantedAuthority>grantedAuthority = new ArrayList<>();
              grantedAuthority.add(new SimpleGrantedAuthority(existing_user.getRole()));
              for (GrantedAuthority authority : grantedAuthority) {
               System.out.println("Authority: " + authority.getAuthority()); // This will print the role/authority
           }
              User user_obj  = new User(existing_user.getEmail(),existing_user.getPassword(),grantedAuthority);
              return(user_obj);
         }
         else
         {
            throw  new UsernameNotFoundException("User with this email not found");
         }

    }
    
}

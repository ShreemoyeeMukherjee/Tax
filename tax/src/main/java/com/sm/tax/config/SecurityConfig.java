package com.sm.tax.config;



import com.nimbusds.jose.JOSEException;
//import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
//import com.sm.tax.utils.CustomUserDetailsService;
//import com.sm.tax.utils.CustomUserDetailsService;
import com.sm.tax.utils.CustomUserDetailsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;
// @Component	On a class.	 @Bean On a method inside a @Configuration.
@Configuration
@EnableWebSecurity
public class SecurityConfig {

   private RSAKey rsaKey;
    
    


    @Bean
    public JWKSource<SecurityContext> jwkSource() {
        System.out.println("In jwksource");
        rsaKey = Jwks.generateRsa();  
        //System.out.println(rsaKey);

        JWKSet jwkSet = new JWKSet(rsaKey);
        //System.out.println(jwkSet);
        return (jwkSelector, securityContext) -> jwkSelector.select(jwkSet);
    }
    @Bean
    JwtEncoder jwtEncoder(JWKSource<SecurityContext> jwks) {
        System.out.println("In jwt encoder");
        return new NimbusJwtEncoder(jwks);
    }
    @Bean
    JwtDecoder jwtDecoder() throws JOSEException {
         return NimbusJwtDecoder.withPublicKey(rsaKey.toRSAPublicKey()).build();
    }
    
      @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
// @Bean
// public UserDetailsService userDetailsService() {
//     return new CustomUserDetailsService();
// }


@Bean
    public AuthenticationManager authManager(CustomUserDetailsService userDetailsService) {
        System.out.println("In auth manager");
        var authProvider = new DaoAuthenticationProvider();
        authProvider.setPasswordEncoder(passwordEncoder());
        authProvider.setUserDetailsService(userDetailsService);
        return new ProviderManager(authProvider);
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        System.out.println("In security filter chain");
        http    
                .headers().frameOptions().sameOrigin()
                .and()
                .authorizeHttpRequests()
                .requestMatchers("/").permitAll()
                .requestMatchers("/users/create-user").permitAll()
                .requestMatchers("/users/login-user").permitAll()
                .requestMatchers("/users/**").permitAll()
                
                .requestMatchers("/db-console/**").permitAll()
                .requestMatchers("/admin/view-tax").hasAuthority("Admin")
                .anyRequest().authenticated()
                .and()
                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);
                //.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
                
                // TODO: remove these after upgrading the DB from H2 infile DB
              
                http.csrf().disable();
                http.headers().frameOptions().disable();

        return http.build();
    }




}

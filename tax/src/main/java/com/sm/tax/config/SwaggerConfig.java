package com.sm.tax.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@Configuration
@OpenAPIDefinition(
  info =@Info(
    title = "Tax Management",
    version = "Verions 1.0",
    contact = @Contact(
      name = "sm", email = "admin@sm"
    ),
    license = @License(
      name = "Apache 2.0", url = "https://www.apache.org/licenses/LICENSE-2.0"
    ),
    
    description = "Tax management"
  )
)

public class SwaggerConfig {
    
}

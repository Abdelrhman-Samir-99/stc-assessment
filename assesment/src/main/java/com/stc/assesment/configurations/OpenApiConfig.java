package com.stc.assesment.configurations;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@Configuration
@OpenAPIDefinition(info = @Info(title = "${spring.application.name}",
		description = "API for uploading and downloading files", version = "v1"))
public class OpenApiConfig {
}
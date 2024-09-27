package com.mohitjoshi.blog.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import jakarta.validation.constraints.AssertFalse.List;

@OpenAPIDefinition(
		info = @Info(
				description = "Backend APIs for blogging website.",
				contact = @Contact(
						name = "Mohit",
						email = "jshibhanu415@gmail.com",
						url = ""
						),
				title = "Blogging site APIs",
				version = "1.0",
				license = @License(
						name = "license",
						url = ""
						),
				termsOfService = "Terms & Condition"
				),
		servers = {
			@Server(
				description = "Local Env",
				url = "http://localhost:9090/"
				),
			@Server(
				description = "Prod Env",
				url = "http://localhost:9090/"
				)
			},
		security = @SecurityRequirement(
				name = "BearerAuth"
				)
		)
@SecurityScheme(
		name = "BearerAuth",
		description = "JWT Auth",
		scheme = "bearer",
		type = SecuritySchemeType.HTTP,
		bearerFormat = "JWT",
		in = SecuritySchemeIn.HEADER
		)
public class SwaggerConfig {

}

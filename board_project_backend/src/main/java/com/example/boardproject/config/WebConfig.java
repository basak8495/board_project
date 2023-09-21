package com.example.boardproject.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
			.allowedOrigins("http://localhost:8080")
			.allowedOrigins("https://web-frontend-4fju66f2clmsgujng.sel5.cloudtype.app")
			.allowedMethods("GET", "POST", "PUT", "DELETE")
			.allowCredentials(true)
			.allowedHeaders("*")
			.maxAge(3000);
	}
}
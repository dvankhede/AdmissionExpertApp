package com.admission.expert;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;

@SpringBootApplication
@EnableCaching
@EnableAutoConfiguration
public class AdmissionExpertStarter extends SpringBootServletInitializer {
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(AdmissionExpertStarter.class);
	}
	
	
	public static void main(String[] args) {
		SpringApplication.run(AdmissionExpertStarter.class, args);
	}
}

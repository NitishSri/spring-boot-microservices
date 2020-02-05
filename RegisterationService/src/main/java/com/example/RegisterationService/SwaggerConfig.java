package com.example.RegisterationService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	List<VendorExtension> vendorExtensions = new ArrayList<>();

	Contact contact = new Contact("Nitish Srivastava", "http://nitish.com", "nitish91.sri@gmail.com");

	ApiInfo apiInfo = new ApiInfo("Comparer Web Service documentation", "This pages contains registeration service",
			"1.0", "http://nitish.com", contact, "1.0", "", vendorExtensions);

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any()).build();
	}

}

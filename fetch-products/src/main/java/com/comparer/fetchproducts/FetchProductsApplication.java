package com.comparer.fetchproducts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class FetchProductsApplication {

	public static void main(String[] args) {
		SpringApplication.run(FetchProductsApplication.class, args);
	}

}

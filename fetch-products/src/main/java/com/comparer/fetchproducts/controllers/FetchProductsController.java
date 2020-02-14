package com.comparer.fetchproducts.controllers;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FetchProductsController {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@GetMapping("/status")
	public String checkStatus() {
		logger.info("status api called ");
		return "Product Service is running !!";
	}

	@GetMapping("/fetchproducts")
	public List<String> checkUsernameExist(String username) {

		List<String> list = new ArrayList<String>();
		list.add("Iphone 7");
		list.add("Iphone 7S");
		list.add("Iphone 8");
		list.add("Iphone 8S");
		list.add("Iphone X");
		list.add("Iphone XS");
		list.add("Iphone 11");
		list.add("Iphone 11 Pro");

		return list;
	}

}

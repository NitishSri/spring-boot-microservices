package com.comparer.fetchproducts.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.apache.solr.client.solrj.SolrServerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.comparer.fetchproducts.resourceobject.ProductRequestRO;
import com.comparer.fetchproducts.resourceobject.ProductResponseRO;
import com.comparer.fetchproducts.service.ProductService;

@RestController
public class FetchProductsController {

	@Autowired
	ProductService service;

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@GetMapping("/status")
	public String checkStatus() {
		logger.info("status api called ");
		return "Product Service is running !!";
	}

	@PostMapping(path = "/addProduct", consumes = "application/json", produces = "application/json")
	public ProductResponseRO addProduct(@Valid @RequestBody ProductRequestRO product) {
		try {
			return service.addProduct(product);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	@PostMapping(path = "/updateProduct", consumes = "application/json", produces = "application/json")
	public ProductResponseRO updateProduct(@RequestBody ProductRequestRO product) {
		return service.updateProduct(product);

	}

	@DeleteMapping(path = "/deleteProduct", consumes = "application/json", produces = "application/json")
	public String deleteProduct(String sku) {
		service.deleteProduct(sku);
		return "Product has been deleted";
	}

	@GetMapping("/fetchAllProducts")
	public List<ProductResponseRO> fetchProducts() {
		return service.getAllProducts();
	}

	@GetMapping("/fetchProductBySku")
	public ProductResponseRO fetchProductBySku(String sku) {
		return service.findProductBySku(sku);
	}

	@GetMapping("/fetchProductByName")
	public ProductResponseRO fetchProductByName(String name) {
		return service.findProductByName(name);
	}

	@GetMapping("/fetchProductByManufacture")
	public List<ProductResponseRO> fetchProductByManufacture(String manufactureName, int size) {
		try {
			return service.findProductByManufactureName(manufactureName, size);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@GetMapping("/fetchproducts")
	public List<String> fetchProductsFallback(String username) {

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

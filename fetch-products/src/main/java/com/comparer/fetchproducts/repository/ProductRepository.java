package com.comparer.fetchproducts.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.comparer.fetchproducts.model.Product;

@Repository
public interface ProductRepository extends CrudRepository<Product, String> {

	public Product findBySku(String sku);

	public Product findByProductName(String name);
}

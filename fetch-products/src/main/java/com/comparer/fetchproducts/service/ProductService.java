package com.comparer.fetchproducts.service;

import java.io.IOException;
import java.util.List;

import org.apache.solr.client.solrj.SolrServerException;

import com.comparer.fetchproducts.resourceobject.ProductRequestRO;
import com.comparer.fetchproducts.resourceobject.ProductResponseRO;

public interface ProductService {
	public ProductResponseRO findProductBySku(String sku);

	public ProductResponseRO findProductByName(String name);

	public List<ProductResponseRO> findProductByManufactureName(String manufactureName, int limit) throws SolrServerException, IOException;

	public List<ProductResponseRO> getAllProducts();

	public ProductResponseRO addProduct(ProductRequestRO productInput) throws SolrServerException, IOException;

	public ProductResponseRO updateProduct(ProductRequestRO productInput);

	public void deleteProduct(String sku);
}

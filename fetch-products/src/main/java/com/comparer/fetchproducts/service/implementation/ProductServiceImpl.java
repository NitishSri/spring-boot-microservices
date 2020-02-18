package com.comparer.fetchproducts.service.implementation;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comparer.fetchproducts.model.Product;
import com.comparer.fetchproducts.model.ProductSolrBean;
import com.comparer.fetchproducts.repository.ProductRepository;
import com.comparer.fetchproducts.resourceobject.ProductRequestRO;
import com.comparer.fetchproducts.resourceobject.ProductResponseRO;
import com.comparer.fetchproducts.service.ProductService;
import com.comparer.fetchproducts.solr.SolrUtility;

@Service
public class ProductServiceImpl implements ProductService {

	String clientUrl = "http://localhost:8983/solr/comparer";

	@Autowired
	ProductRepository repository;

	@Override
	public ProductResponseRO findProductBySku(String sku) {
		Product product = repository.findBySku(sku);
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(product, ProductResponseRO.class);
	}

	@Override
	public ProductResponseRO findProductByName(String name) {
		Product product = repository.findByProductName(name);
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(product, ProductResponseRO.class);
	}

	@Override
	public List<ProductResponseRO> getAllProducts() {
		Iterable<Product> products = repository.findAll();
		ModelMapper modelMapper = new ModelMapper();
		List<ProductResponseRO> list = Arrays.asList(modelMapper.map(products, ProductResponseRO[].class));
		return list;
	}

	@Override
	public ProductResponseRO addProduct(ProductRequestRO productInput) throws SolrServerException, IOException {
		ModelMapper modelMapper = new ModelMapper();
		Product product = modelMapper.map(productInput, Product.class);
		repository.save(product);
		SolrUtility solr = new SolrUtility(clientUrl);
		ProductResponseRO returnValue = modelMapper.map(product, ProductResponseRO.class);
		solr.addSolrDocument(String.valueOf(returnValue.getId()), returnValue.getProductName(), returnValue.getManufactureName(),
				returnValue.getSku(), returnValue.getShortDescription(), returnValue.getLongDescription());
		return returnValue;
	}

	@Override
	public ProductResponseRO updateProduct(ProductRequestRO productInput) {
		ModelMapper modelMapper = new ModelMapper();
		Product product = modelMapper.map(productInput, Product.class);
		repository.save(product);
		ProductResponseRO returnValue = modelMapper.map(product, ProductResponseRO.class);
		return returnValue;
	}

	@Override
	public void deleteProduct(String sku) {
		Product product = repository.findBySku(sku);
		repository.delete(product);
	}

	@Override
	public List<ProductResponseRO> findProductByManufactureName(String manufactureName, int limit)
			throws SolrServerException, IOException {
		SolrUtility solr = new SolrUtility(clientUrl);
		SolrQuery query = new SolrQuery();
		StringBuilder sb = new StringBuilder();
		sb.append("manufactureName:").append(manufactureName);
		query.setQuery(sb.toString());
		query.setStart(0);
		query.setRows(limit);
		QueryResponse response = solr.getSolrClient().query(query);
		List<ProductSolrBean> items = response.getBeans(ProductSolrBean.class);
		ModelMapper modelMapper = new ModelMapper();
		List<ProductResponseRO> list = Arrays.asList(modelMapper.map(items, ProductResponseRO[].class));
		return list;
	}

}

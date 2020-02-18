package com.comparer.fetchproducts.model;

import org.apache.solr.client.solrj.beans.Field;

public class ProductSolrBean {

	private String id;
	private String productName;
	private String manufactureName;
	private String sku;
	private String shortDescription;
	private String longDescription;

	public String getId() {
		return id;
	}

	@Field("id")
	public void setId(String id) {
		this.id = id;
	}

	public String getProductName() {
		return productName;
	}

	@Field("productName")
	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getManufactureName() {
		return manufactureName;
	}

	@Field("manufactureName")
	public void setManufactureName(String manufactureName) {
		this.manufactureName = manufactureName;
	}

	public String getSku() {
		return sku;
	}

	@Field("sku")
	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getShortDescription() {
		return shortDescription;
	}

	@Field("shortDescription")
	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	public String getLongDescription() {
		return longDescription;
	}

	@Field("longDescription")
	public void setLongDescription(String longDescription) {
		this.longDescription = longDescription;
	}

}

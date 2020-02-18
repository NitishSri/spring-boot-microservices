package com.comparer.fetchproducts.solr;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.apache.solr.common.SolrInputDocument;

import com.comparer.fetchproducts.model.ProductSolrBean;

public class SolrUtility {
	private HttpSolrClient solrClient;

	public SolrUtility(String clientUrl) {

		solrClient = new HttpSolrClient.Builder(clientUrl).build();
		solrClient.setParser(new XMLResponseParser());
	}

	public void addProductBean(ProductSolrBean pBean) throws IOException, SolrServerException {

		solrClient.addBean(pBean);
		solrClient.commit();
	}

	public void addSolrDocument(String id, String productName, String manufactureName, String sku,
			String shortDescription, String longDescription) throws SolrServerException, IOException {

		SolrInputDocument document = new SolrInputDocument();
		document.addField("id", id);
		document.addField("productName", productName);
		document.addField("manufactureName", manufactureName);
		document.addField("sku", sku);
		document.addField("shortDescription", shortDescription);
		document.addField("longDescription", longDescription);
		solrClient.add(document);
		solrClient.commit();
	}

	public void deleteSolrDocumentById(String documentId) throws SolrServerException, IOException {

		solrClient.deleteById(documentId);
		solrClient.commit();
	}

	public void deleteSolrDocumentByQuery(String query) throws SolrServerException, IOException {

		solrClient.deleteByQuery(query);
		solrClient.commit();
	}

	public HttpSolrClient getSolrClient() {
		return solrClient;
	}

	public void setSolrClient(HttpSolrClient solrClient) {
		this.solrClient = solrClient;
	}
}

package com.pctrade.price.dao;

import java.util.List;

import com.pctrade.price.entity.Product;

public interface DaoProduct {

	public List<Product> showAllProductList() throws IllegalAccessException;

	Product showProductById(Integer productId) throws IllegalAccessException; // -

	void createProduct(Product product) throws IllegalAccessException; // - +

	void updateProduct(Product product) throws IllegalAccessException; // - +

	void deleteProduct(Integer productId) throws IllegalAccessException; // -

	void setNotAvailableStatusForAll() throws IllegalAccessException; // - +

	void createProductTable(List<Product> products) throws IllegalAccessException;

	void updateProductTable(List<Product> products) throws IllegalAccessException;

	int countProductWithArticleCode(Product product) throws IllegalAccessException; // -
																					// +

	void clearTable() throws IllegalAccessException; // - +

}

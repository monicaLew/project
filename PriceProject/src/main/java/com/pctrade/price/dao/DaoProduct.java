package com.pctrade.price.dao;

import java.util.List;

import com.pctrade.price.entity.Product;

public interface DaoProduct {

	public List<Product> showAllProductList();

	Product showProductById(Integer productId);

	void createProduct(Product product);

	void updateProduct(Product product);

	void deleteProduct(Integer productId);

	void setNotAvailableStatusForAll();

	void createProductTable(List<Product> products);

	void updateProductTable(List<Product> products);

	int countProductWithArticleCode(Product product);

	void clearTable();

}

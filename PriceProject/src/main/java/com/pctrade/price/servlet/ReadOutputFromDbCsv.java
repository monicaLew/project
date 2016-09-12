package com.pctrade.price.servlet;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pctrade.price.dao.DaoProduct;
import com.pctrade.price.dao.DaoProductImpl;
import com.pctrade.price.entity.Product;

public class ReadOutputFromDbCsv extends HttpServlet {
	private static final long serialVersionUID = 1L;
	//private static final String FORWARD_NAME = "/output.jsp";
	//private static final String ERROR_NAME = "/errorPage.jsp";

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		// response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");

		DaoProduct daoProduct = new DaoProductImpl();
		List<Product> productsList = daoProduct.showAllProductList();	

//		try {
//			File file = new File("C:/priceList.txt"); // change to cross
//														// functional
//			FileWriter fileWriter = new FileWriter(file);
//			Writer writer = new BufferedWriter(fileWriter);
//
//			for (Product product : productsList) {
//				writer.write(product.toString() + System.getProperty("line.separator"));
//			}
//			writer.close();
//			fileWriter.close();
//		} catch (FileNotFoundException e) {
//			HttpUtils.forward(ERROR_NAME, request, response);
//		}
		 response.setContentType("application/octet-stream");
				 response.setHeader("Content-Disposition",
				 "attachment;filename=priceList.txt");
				 				 
				 BufferedWriter writer2 = new BufferedWriter(new
				 OutputStreamWriter(response.getOutputStream()));
				 for (Product product : productsList) {
				 writer2.write(product.toString() +
				 System.getProperty("line.separator"));
				 }
				 writer2.close();			 				
		//HttpUtils.forward(FORWARD_NAME, request, response);		
	}
}

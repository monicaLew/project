package com.pctrade.price.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.pctrade.price.dao.DaoProduct;
import com.pctrade.price.dao.DaoProductImpl;
import com.pctrade.price.readers.ReadExcel;
import com.pctrade.price.utils.HttpUtils;

public class UpdateExcel extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		try {
			String date = (String) session.getAttribute("dateOfUpload");
			String filePath = getServletContext().getInitParameter("file-upload")
					+ session.getAttribute("lastFileNameUpload");

			DaoProduct daoProductImpl = new DaoProductImpl();
			daoProductImpl.updateProductTable(ReadExcel.readExcelFillProduct(filePath, date));

		} catch (Exception e) {
			session.setAttribute("exception", e);
			HttpUtils.forward("/errorPage.jsp", request, response);
		}
		HttpUtils.forward("/result.jsp", request, response);
	}
}

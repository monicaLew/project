package com.pctrade.price.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.pctrade.price.dao.DaoProduct;
import com.pctrade.price.dao.DaoProductImpl;
import com.pctrade.price.readers.ReadCsv;
import com.pctrade.price.utils.HttpUtils;

public class UpdateCSV extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String FORWARD_NAME = "/result.jsp";
	private static final String ERROR_NAME = "/errorPage.jsp";

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
			daoProductImpl.updateProductTable(ReadCsv.readCsvFillProtuct(filePath, date));

		} catch (Exception e) {
			session.setAttribute("exception", e);
			request.getRequestDispatcher(ERROR_NAME).forward(request, response);
		}
		HttpUtils.forward(FORWARD_NAME, request, response);
	}
}

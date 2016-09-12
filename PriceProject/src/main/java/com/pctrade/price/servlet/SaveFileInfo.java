package com.pctrade.price.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.pctrade.price.dao.DaoUploadedFile;
import com.pctrade.price.dao.DaoUploadedFileImpl;
import com.pctrade.price.entity.UploadedFile;
import com.pctrade.price.utils.HttpUtils;

public class SaveFileInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String FORWARD_NAME = "/lastUploadFile.jsp";

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");

		DaoUploadedFile daoUploadedFile = new DaoUploadedFileImpl();
		UploadedFile uploadedFile = (UploadedFile) session.getAttribute("uploadedFileInfo");

		daoUploadedFile.createUploadedFileInfo(uploadedFile);

		HttpUtils.forward(FORWARD_NAME, request, response);
	}
}

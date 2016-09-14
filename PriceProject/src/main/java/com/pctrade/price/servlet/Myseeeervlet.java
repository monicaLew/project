package com.pctrade.price.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Myseeeervlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String s = request.getParameter("param1");
		System.out.println(s);
		System.out.println("воот");
		String[] ss = request.getParameterValues("param1");
		for (String c : ss) {
			System.out.println(c);
		}
	}
}

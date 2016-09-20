package com.pctrade.price.servlet;

import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.pctrade.price.dao.DaoCar;
import com.pctrade.price.dao.DaoCarImpl;
import com.pctrade.price.dao.DaoException;
import com.pctrade.price.provider.CarModelProvider;
import com.pctrade.price.utils.HttpUtils;
import com.pctrade.price.validation.FormValidator;

public class ScanCarPage extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String SUCCESS_VIEW_NAME = "/scanStatistics.jsp";
	private static final String INPUT_VIEW_NAME = "mainMenu.jsp";
	private static final String ERROR_NAME = "/errorPage.jsp";

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();

		if (!FormValidator.validate(request)) {
			HttpUtils.redirect(INPUT_VIEW_NAME, response);
			return;
		}
		Integer idFrom = HttpUtils.getIntParam(request, "idFrom");
		Integer idTill = HttpUtils.getIntParam(request, "idTill");
		Integer poolCapacity = HttpUtils.getIntParam(request, "poolCapacity");
		Queue<Integer> urls = new ConcurrentLinkedQueue<Integer>();
		for (int id = idFrom; id <= idTill; id++) {
			urls.add(id);
		}

		DaoCar daoCar = new DaoCarImpl();
		try {
			daoCar.clearTable();
		} catch (DaoException ex) {
			session.setAttribute("exception", ex);
			HttpUtils.forward(ERROR_NAME, request, response);
		}

		ExecutorService pool = (ThreadPoolExecutor) Executors.newFixedThreadPool(poolCapacity);
		long start = System.currentTimeMillis();
		for (int id = idFrom; id <= idTill; id++) {
			Runnable worker = new CarModelProvider(urls, session, request, response);
			pool.execute(worker);
		}
		pool.shutdown();
		boolean b = false;
		try {
			b = pool.awaitTermination(1, TimeUnit.MINUTES);
		} catch (InterruptedException e) {
			session.setAttribute("exception", e);
			HttpUtils.forward(ERROR_NAME, request, response);
		}
		long end = System.currentTimeMillis();
		System.out.println(end - start);
		if (b) {
			HttpUtils.forward(SUCCESS_VIEW_NAME, request, response);
		}
	}
}

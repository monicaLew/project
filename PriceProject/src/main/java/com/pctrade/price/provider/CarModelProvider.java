package com.pctrade.price.provider;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.pctrade.price.dao.DaoCar;
import com.pctrade.price.dao.DaoCarImpl;
import com.pctrade.price.dao.DaoException;
import com.pctrade.price.entity.Car;
import com.pctrade.price.parser.HtmlParser;
import com.pctrade.price.utils.HttpUtils;

public class CarModelProvider implements Runnable {
	private Queue<Integer> urls = new ConcurrentLinkedQueue<Integer>();
	HttpSession session;
	HttpServletRequest request;
	HttpServletResponse response;
	private Map<Integer, Integer> shotsGetConnection = new HashMap<Integer, Integer>();
	private static final String ERROR_NAME = "/errorPage.jsp";

	public CarModelProvider(Queue<Integer> urls, HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {
		this.urls = urls;
		this.session = session;
		this.request = request;
		this.response = response;
	}

	public void run() {
		DaoCar daoCar = new DaoCarImpl();
		while (urls.size() != 0) {
			System.out.println(Thread.currentThread().getName() + " ***** " + Thread.currentThread().getId());
			Integer id = urls.poll();
			System.out.println(" ID : " + id);
			if (id != null) {
				Car car = new Car();
				try {
					car = HtmlParser.extractCarInfo(id);
				} catch (Exception ex) {
					if (ex instanceof SocketTimeoutException && checkShotsGetConnection(id)) {
						System.out.println("SocketTimeoutException try again  ###     ###     ###   ###   ###   " + id);
						urls.add(id);
						continue;
					} else {
						car = Car.createCar(ex, id);
					}
				}
				try {
					daoCar.createCar(car);
				} catch (DaoException e) {
					session.setAttribute("exception", e);
					try {
						HttpUtils.forward(ERROR_NAME, request, response);
					} catch (ServletException e1) {
					} catch (IOException e1) {
					}
					System.out.println(" DaoException   DaoException   DaoException   CREATE");
				}
			}
		}
	}

	public boolean checkShotsGetConnection(Integer id) {
		if (!shotsGetConnection.containsKey(id)) {
			shotsGetConnection.put(id, 0);
		}
		int shots = shotsGetConnection.get(id);
		shots = shots + 1;
		shotsGetConnection.put(id, shots);
		return shotsGetConnection.get(id) < 3;
	}
}

package com.pctrade.price.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.pctrade.price.dao.DaoCar;
import com.pctrade.price.dao.DaoCarImpl;
import com.pctrade.price.entity.Car;
import com.pctrade.price.parser.HtmlParser;
import com.pctrade.price.utils.HttpUtils;
import com.pctrade.price.validation.FormValidator;

public class ScanCarPage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String urlBase = "http://www.abw.by/allpublic/sell/";

	public static final String FROM_TEXT_ERROR_CODE = "idFrom.text.error";
	public static final String FROM_NEGATIVE_ERROR_CODE = "idFrom.negative.error";
	public static final String TILL_TEXT_ERROR_CODE = "idTill.text.error";
	public static final String TILL_NEGATIVE_ERROR_CODE = "idTill.negative.error";
	public static final String FROM_LESS_ERROR_CODE = "idFrom.less.error";
	public static final String TILL_BIGGER_ERROR_CODE = "idTill.bigger.error";

	private static final String FROM_NEGATIVE_ERROR_TEXT = "ID 'From' must be greater than 0";
	private static final String TILL_NEGATIVE_ERROR_TEXT = "ID 'Till' must be greater than 0";
	private static final String FROM_TEXT_ERROR_TEXT = "Please input correct value";
	private static final String TILL_TEXT_ERROR_TEXT = "Please input correct value";
	private static final String FROM_LESS_ERROR_TEXT = "ID 'From' must be less than 'Till'";
	private static final String TILL_BIGGER_ERROR_TEXT = "ID 'Till' must be bigger than 'From'";

	private static final String SUCCESS_VIEW_NAME = "/inputCars.jsp";
	private static final String INPUT_VIEW_NAME = "index.jsp";

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpUtils.forward(SUCCESS_VIEW_NAME, request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();

		if (!validate(request)) {
			HttpUtils.redirect(INPUT_VIEW_NAME, response);
			return;
		}
		Integer idFrom = HttpUtils.getIntParam(request, "idFrom");
		Integer idTill = HttpUtils.getIntParam(request, "idTill");

		DaoCar daoCar = new DaoCarImpl();
		daoCar.clearTable();
		while (idFrom <= idTill) {
			Car car = HtmlParser.extractCarInfo(urlBase, idFrom);
			daoCar.createCar(car);
			idFrom++;
		}
		List<Car> carList = new ArrayList<Car>();
		// carList = daoCar.showAllCarsList();
		carList = daoCar.showCarsByStatus(); // show ALL
		session.setAttribute("carList", carList); // show only AVAILABLE
		session.setAttribute("numberOfCars", carList.size());
		HttpUtils.forward(SUCCESS_VIEW_NAME, request, response);
	}

	private boolean validate(HttpServletRequest request) {
		Map<String, String> errorMap = new HashMap<String, String>();

		if (HttpUtils.isIntNull(request, "idFrom")) {
			errorMap.put(FROM_TEXT_ERROR_CODE, FROM_TEXT_ERROR_TEXT);
		} else {
			if (HttpUtils.isNegativeInt(request, "idFrom")) {
				errorMap.put(FROM_NEGATIVE_ERROR_CODE, FROM_NEGATIVE_ERROR_TEXT);
			}
		}

		if (HttpUtils.isIntNull(request, "idTill")) {
			errorMap.put(TILL_TEXT_ERROR_CODE, TILL_TEXT_ERROR_TEXT);
		} else {
			if (HttpUtils.isNegativeInt(request, "idTill")) {
				errorMap.put(TILL_NEGATIVE_ERROR_CODE, TILL_NEGATIVE_ERROR_TEXT);
			}
		}

		if (HttpUtils.isFromBiggerThanTill(request, "idFrom", "idTill")) {
			errorMap.put(FROM_LESS_ERROR_CODE, FROM_LESS_ERROR_TEXT);
			errorMap.put(TILL_BIGGER_ERROR_CODE, TILL_BIGGER_ERROR_TEXT);
		}

		if (!errorMap.isEmpty()) {
			HttpSession session = request.getSession();
			session.setAttribute(FormValidator.FORM_ERRORS_MAP, errorMap);
			return false;
		}
		return true;
	}
}

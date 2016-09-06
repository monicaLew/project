package com.pctrade.price.validation;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

public class FormValidator {

	public static final String FORM_ERRORS_MAP = "form.errors";

	public static Map<String, String> getErrorMap(HttpSession session) {
		Map<String, String> errorMap = (Map<String, String>) session.getAttribute(FormValidator.FORM_ERRORS_MAP);
		if (errorMap == null) {
			errorMap = new HashMap<String, String>();
		}
		return errorMap;
	}
}

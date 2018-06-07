package org.framework.core.interceptors;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {

	public XssHttpServletRequestWrapper(HttpServletRequest servletRequest) {
		super(servletRequest);
	}

	public String[] getParameterValues(String parameter) {
		String[] values = super.getParameterValues(parameter);
		if (values == null)
			return null;

		int count = values.length;
		String[] encodedValues = new String[count];
		List<String> pList = new ArrayList<String>();
		pList.add("q");
		pList.add("q_flow_str");
		pList.add("q_log_str");
		for (int i = 0; i < count; i++) {
			if(pList.contains(parameter)){
				encodedValues[i] = values[i];
			}else{
				encodedValues[i] = cleanXss(values[i]);
			}
		}
		return encodedValues;
	}

	public String getParameter(String parameter) {
		String value = super.getParameter(parameter);
		if (value == null)
			return null;

		return this.cleanXss(value);
	}

	public String getHeader(String name) {
		String value = super.getHeader(name);
		if (value == null)
			return null;
		return this.cleanXss(value);
	}

	public String cleanXss(String value) {
		value = value.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
//		value = value.replaceAll("\\(", "&#40;").replaceAll("\\)", "&#41;");
		value = value.replaceAll("'", "&#39;");
		value = value.replaceAll("eval\\((.*)\\)", "");
		value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']",
				"\"\"");
		value = value.replaceAll("script", "");
		return value;
	}

}

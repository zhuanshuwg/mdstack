package org.framework.core.interceptors;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * 
 * @author wangguan
 * 
 */
public class XssFilter implements Filter {

	FilterConfig filterConfig = null;

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
		arg2.doFilter(new XssHttpServletRequestWrapper(
				(HttpServletRequest) arg0), arg1);
	}

	@Override
	public void destroy() {
		this.filterConfig = null;

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		this.filterConfig = arg0;

	}

}

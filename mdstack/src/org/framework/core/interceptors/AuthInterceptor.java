package org.framework.core.interceptors;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.framework.core.util.ContextHolderUtils;
import org.framework.core.util.ResponseUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * 权限拦截器
 * 
 * @author wangguan
 * @version v1.0
 */
public class AuthInterceptor implements HandlerInterceptor {

	/**
	 * 在controller前拦截
	 */
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object object) throws Exception {

		String uri = request.getRequestURI();
		uri = uri.substring(request.getContextPath().length());// 去掉项目路径

		HttpSession session = ContextHolderUtils.getSession();
		boolean flag = false;
		if(uri.indexOf("config/dashboard/index")>-1||uri.indexOf("dashboard/firewall")>-1||uri.indexOf("dlogin")>-1){
		    //新加，用于不拦截dashboard展示页
		    flag =  true;
		} else if (excludeUrls.contains(uri)) {
			flag =  true;
		} else {
			if (session != null) {
				if(session.getAttribute("userId") != null){
					flag = true;
				}else{
					response.sendRedirect(request.getContextPath() + "/login/exit");
				}
			}
		}
		return flag;
	}

	/**
	 * 在controller后拦截
	 */
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object object, Exception exception)
			throws Exception {
	}

	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object object,
			ModelAndView modelAndView) throws Exception {

	}

	private static final Logger logger = Logger
			.getLogger(AuthInterceptor.class);

	/** 拦截外的urls */
	private List<String> excludeUrls;
	private List<String> clusterHiddenUrls;

	public List<String> getExcludeUrls() {
		return excludeUrls;
	}

	public void setExcludeUrls(List<String> excludeUrls) {
		this.excludeUrls = excludeUrls;
	}

	public List<String> getClusterHiddenUrls() {
		return clusterHiddenUrls;
	}

	public void setClusterHiddenUrls(List<String> clusterHiddenUrls) {
		this.clusterHiddenUrls = clusterHiddenUrls;
	}
	

}

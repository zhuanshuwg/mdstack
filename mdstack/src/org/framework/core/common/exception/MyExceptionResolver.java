package org.framework.core.common.exception;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.framework.core.util.ResponseUtils;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

/**
 * 继承SimpleMappingExceptionResolver，重载其doResolverException方法 实现普通异常和AJAX异常的统一处理解
 * com.ccb.testcenter.sample.exception
 * 
 * @author wangguan
 */
public class MyExceptionResolver implements HandlerExceptionResolver {
	private static final Logger log = Logger.getLogger(MyExceptionResolver.class);
	@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
			StringBuffer msg = new StringBuffer();
			Map<String, Object> model = new HashMap<String, Object>(); 
			msg.append("<b>错误信息：</b><br />").append(ex.getMessage());
			
			model.put("ex", ex);
			log.error(ex.getMessage(), ex);
		
			if (!(request.getHeader("accept").indexOf("application/json") > -1 || (request
					.getHeader("X-Requested-With") != null && request
					.getHeader("X-Requested-With").indexOf("XMLHttpRequest") > -1))) {
				
				return new ModelAndView("common/error",model);
			} else {// JSON格式返回
				ResponseUtils.renderJson(response, "服务器端错误！");//msg.toString()
				return null;
			}
	}
}

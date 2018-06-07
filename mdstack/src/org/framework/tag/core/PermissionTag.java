package org.framework.tag.core;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.framework.core.util.ContextHolderUtils;


public class PermissionTag extends TagSupport {

	
	private static final long serialVersionUID = 7345296355617474895L;
	
	private String type;//类型menu,function
	private Integer menuId;//菜单id
	private Integer functionId;//功能id
	private Integer permissionId;//权限id
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getMenuId() {
		return menuId;
	}

	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}

	public Integer getFunctionId() {
		return functionId;
	}

	public void setFunctionId(Integer functionId) {
		this.functionId = functionId;
	}

	public Integer getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(Integer permissionId) {
		this.permissionId = permissionId;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int doStartTag() throws JspException {
		boolean result = false;
		HttpSession session = ContextHolderUtils.getSession();
		List<Long> persessions = (List<Long>) session
				.getAttribute("PERMISSIONS");
		List<Long> menus = (List<Long>) session
				.getAttribute("MENUS");

		if (type.equals("permission")) {
			if (persessions.contains(Long.valueOf(permissionId))) {
				result = true;
			}
		}
		
		if (type.equals("menu")) {
			if (menus.contains(Long.valueOf(menuId))) {
				result = true;
			}
		}
		
		if(type.equals("cluster")){
			result = ! (Boolean)session.getAttribute("cluster");
		}

		return result ? EVAL_BODY_INCLUDE : SKIP_BODY;
	}

	

}

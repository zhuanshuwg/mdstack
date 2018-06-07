package mdstack.login.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.digest.DigestUtils;
import org.framework.core.util.ContextHolderUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import mdstack.login.service.PublicService;

@Controller
@RequestMapping("login")
public class LoginController {
	
	@Autowired
	private PublicService publicService;
	
	@RequestMapping(value = "login")
	public String login(RedirectAttributes attributes, HttpServletRequest request,HttpServletResponse response, String userName, String userPwd) {
		Map<String, Object> param = new HashMap<String, Object>();
		HttpSession session = ContextHolderUtils.getSession();
		if(userName != null && userPwd != null){
			param.put("userName", userName);
			param.put("userPwd", DigestUtils.md5Hex(userPwd));
			//查询该用户的用户名和密码
			String sql_1 = "select * from t_user where userName = :userName and userPwd = :userPwd and isDisable = 0";
			List<Map<String, Object>> list = publicService.findMapListForJdbcMapper(sql_1, param);
			if(list.size() != 0){
				//将用户信息放入session
				request.getSession().setAttribute("userId", list.get(0).get("id"));
				request.getSession().setAttribute("userName", list.get(0).get("realName"));
				request.getSession().setMaxInactiveInterval(0);//以秒为单位，即在没有活动30分钟后，session将失效
			}else{
				return "redirect:/login/exit";
			}
		}else{
			if(session.getAttribute("userId") == null){
				return "redirect:/login/exit";
			}
		}
		return "home/main";
	}
	
	/**
	 * 获取当前登录用户信息
	 * @return
	 */
	@RequestMapping("getNowUserInfo")
	public @ResponseBody
	Map<String, Object> getNowUserInfo(){
		HttpSession session = ContextHolderUtils.getSession();
		Map<String, Object> map = new HashMap<String, Object>();
		if (session != null) {
			if(session.getAttribute("userId") != null){
				map.put("userId", session.getAttribute("userId"));
			}
			if(session.getAttribute("userName") != null){
				map.put("userName", session.getAttribute("userName"));
			}
		}
		return map;
	}
	
	/**
	 * 获取菜单
	 * @return
	 */
	@RequestMapping("getMenu")
	public @ResponseBody
	List<Map<String, Object>> getMenu(String userId){
		Map<String, Object> param = new HashMap<String, Object>();
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		param.put("userId", userId);
		String sql = "SELECT m.* "
				+ "FROM "
				+ "t_menu m, t_role_menu rm,t_role r "
				+ "WHERE "
				+ "r.id=rm.roleId AND rm.menuId=m.id AND  r.id "
				+ "IN "
				+ "(SELECT r.id "
				+ "FROM "
				+ "t_user u,t_role r, t_user_role ur "
				+ "WHERE "
				+ "u.id = :userId AND u.id=ur.userId AND ur.roleId=r.id) "
				+ "ORDER BY px";
		list = publicService.findMapListForJdbcMapper(sql, param);
		return list;
	}
	
	@RequestMapping(value = {"/exit"})
	public String exit(HttpServletRequest request) {
		return "login/login";
	}
	
	@RequestMapping(value = {"/layout"})
	public String layout(HttpServletRequest request) {
		HttpSession session = ContextHolderUtils.getSession();
		session.removeAttribute("userId");
		session.removeAttribute("userName");
		return "redirect:/login/exit";
	}
	
	@RequestMapping(value = "welcome")
	public String welcome(HttpServletRequest request,HttpServletResponse response) {
		return "home/main";
	}
	
	@RequestMapping(value = "header")
	public String header(HttpServletRequest request,HttpServletResponse response) {
		return "home/header";
	}
	
	@RequestMapping(value = "left")
	public String left(HttpServletRequest request,HttpServletResponse response) {
		return "home/left";
	}

	public PublicService getPublicService() {
		return publicService;
	}

	public void setPublicService(PublicService publicService) {
		this.publicService = publicService;
	}
	
}

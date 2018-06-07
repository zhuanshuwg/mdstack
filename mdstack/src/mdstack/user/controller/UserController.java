package mdstack.user.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

import mdstack.login.service.PublicService;
import mdstack.user.entity.User;

@Controller
@RequestMapping("userController")
public class UserController {
	
	@Autowired
	private PublicService publicService;
	String message = "操作失败";
	
	/**
	 * 用户首页跳转
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "user")
	public String user(HttpServletRequest request,HttpServletResponse response) {
		return "user/user_list";
	}
	
	/**
	 * 获取用户列表
	 * @param userName
	 * @param start
	 * @param end
	 * @return
	 */
	@RequestMapping("userList")
	public @ResponseBody
	Map<String, Object> userList(String userName, Long start, Long end){
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userName", userName);
		param.put("start", start);
		param.put("end", end);
		String sql_list = "select * from t_user  where 1=1 ";
		String sql_count = "select COUNT(*) count from t_user  where 1=1 ";
		if(!userName.equals("")){
			sql_list += "and userName = :userName";
			sql_count += "and userName = :userName";
		}
		sql_list +=" limit :start, :end";
		List<Map<String, Object>> list = publicService.findMapListForJdbcMapper(sql_list, param);
		List<Map<String, Object>> count = publicService.findMapListForJdbcMapper(sql_count, param);
		result.put("count", count);
		result.put("list", list);
		return result;
	}
	
	/**
	 * 用户注册
	 * @param userName
	 * @param userPwd
	 * @param realName
	 * @param phone
	 * @return
	 */
	@RequestMapping("addUserSubmit")
	public @ResponseBody
	String addUserSubmit(String userName, String userPwd, String realName, String phone) {
		message = "注册失败";
		User user = new User();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userName", userName);
		try {
			//查看该账号是否被注册
			String sql = "select userName from t_user where userName = :userName";
			List<Map<String, Object>> list = publicService.findMapListForJdbcMapper(sql, param);
			if(list.size() != 0){
				message = "该用户名已存在！";
				return message;
			}
			user.setUserName(userName);
			user.setUserPwd(DigestUtils.md5Hex(userPwd));
			user.setRealName(realName);
			user.setTel(phone);
			user.setIsDisable("0");
			publicService.save(user);
			message = "操作成功";
		} catch (Exception e) {
			System.out.println("注册用户出错！");
			return message;
		}
		return message;
	}
	
	/**
	 * 用户禁用启用功能
	 * @param id
	 * @param is
	 * @return
	 */
	@RequestMapping("userManageChangeBtn")
	public @ResponseBody
	Integer userManageChangeBtn(String id, String is) {
		Integer executeDMLForMap = null;
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("id", id);
		param.put("is", is);
		try {
			String sql = "update t_user set isDisable = :is where id = :id";
			executeDMLForMap = publicService.executeDMLForMap(sql, param);
		} catch (Exception e) {
			System.out.println("禁用启用用户出错！");
		}
		return executeDMLForMap;
	}
	
	/**
	 * 删除用户
	 * @param id
	 * @return
	 */
	@RequestMapping("userManageDelUser")
	public @ResponseBody
	Integer userManageDelUser(String id) {
		Integer executeDMLForMap = null;
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("id", id);
		try {
			String sql = "delete from t_user where id = :id";
			executeDMLForMap = publicService.executeDMLForMap(sql, param);
		} catch (Exception e) {
			System.out.println("删除用户出错！");
		}
		return executeDMLForMap;
	}
	
	/**
	 * 根据ID获取用户信息
	 * @param id
	 * @return
	 */
	@RequestMapping("userManageGetUserInfo")
	public @ResponseBody
	List<Map<String, Object>> userManageGetUserInfo(String id) {
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("id", id);
		try {
			String sql = "select * from t_user where id = :id";
			list = publicService.findMapListForJdbcMapper(sql, param);
		} catch (Exception e) {
			System.out.println("获取用户信息出错！");
		}
		return list;
	}
	
	/**
	 * 修改用户信息
	 * @param userId
	 * @param realName
	 * @param phone
	 * @return
	 */
	@RequestMapping("updateUserSubmit")
	public @ResponseBody
	Integer updateUserSubmit(String userId, String realName, String phone) {
		Integer executeDMLForMap = null;
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("id", userId);
		param.put("realName", realName);
		param.put("phone", phone);
		try {
			String sql = "update t_user set realName = :realName, tel = :phone where id = :id";
			executeDMLForMap = publicService.executeDMLForMap(sql, param);
		} catch (Exception e) {
			System.out.println("修改用户信息出错！");
		}
		return executeDMLForMap;
	}
	
	/**
	 * 获取角色列表
	 * @return
	 */
	@RequestMapping("userManageGetRoleList")
	public @ResponseBody
	Map<String, Object> userManageGetRoleList(String userId){
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> map = new HashMap<String, Object>();
		param.put("userId", userId);
		String user_role_sql = "select * from t_user_role where userId = :userId";
		List<Map<String, Object>> permistion = publicService.findMapListForJdbcMapper(user_role_sql, param);
		String sql = "select * from t_role";
		List<Map<String, Object>> list = publicService.findMapListForJdbcMapper(sql, null);
		map.put("list", list);
		map.put("permistion", permistion);
		return map;
	}
	
	/**
	 * 给用户赋予角色
	 * @param userId
	 * @param checkValue
	 * @return Integer
	 */
	@RequestMapping("submitUserRole")
	public @ResponseBody
	Integer submitUserRole(String userId, String checkValue) {
		Integer executeDMLForMap = 0;
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", userId);
		try {
			//删除现有角色
			String sql = "delete from t_user_role where userId = :userId";
			executeDMLForMap = publicService.executeDMLForMap(sql, param);
			//重新赋予角色
			List<Object[]> bathList = new ArrayList<Object[]>();
			String[] roleIds = checkValue.split(",");
			for(String roleId:roleIds){
				bathList.add(new String[]{userId,roleId});
			}
			String bathsql = "insert into t_user_role(userId, roleId) values(?,?)";
			publicService.batchUpdate(bathsql, bathList);
			executeDMLForMap = 1;
		} catch (Exception e) {
			System.out.println("用户赋予角色出错！");
		}
		return executeDMLForMap;
	}

	public PublicService getPublicService() {
		return publicService;
	}

	public void setPublicService(PublicService publicService) {
		this.publicService = publicService;
	}
	
	
}

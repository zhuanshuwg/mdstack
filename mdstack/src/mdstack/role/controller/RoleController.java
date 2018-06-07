package mdstack.role.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import mdstack.login.service.PublicService;
import mdstack.role.entity.Role;

@Controller
@RequestMapping("roleController")
public class RoleController {
	
	@Autowired
	private PublicService publicService;

	@RequestMapping(value = "roleManageRole_list")
	public String user(HttpServletRequest request,HttpServletResponse response) {
		return "role/role_list";
	}
	
	/**
	 * 获取角色列表
	 * @return
	 */
	@RequestMapping("roleMageGetRoleList")
	public @ResponseBody
	List<Map<String, Object>> roleMageGetRoleList(){
		String sql = "select * from t_role";
		List<Map<String, Object>> list = publicService.findMapListForJdbcMapper(sql, null);
		return list;
	}
	
	/**
	 * 删除角色
	 * @param id
	 * @return
	 */
	@RequestMapping("roleManageDelRole")
	public @ResponseBody
	Integer roleManageDelRole(String id) {
		Integer executeDMLForMap = null;
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("id", id);
		try {
			String sql = "delete from t_role where id = :id";
			executeDMLForMap = publicService.executeDMLForMap(sql, param);
		} catch (Exception e) {
			System.out.println("删除用户出错！");
		}
		return executeDMLForMap;
	}
	
	/**
	 * 添加角色
	 * @param roleName
	 * @param descrption
	 * @return
	 */
	@RequestMapping("roleManageAddRoleSubmit")
	public @ResponseBody
	String roleManageAddRoleSubmit(String roleName, String descrption) {
		String message = "操作失败";
		Role role = new Role();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("roleName", roleName);
		try {
			//查看该角色名称是否已经存在
			String sql = "select roleName from t_role where roleName = :roleName";
			List<Map<String, Object>> list = publicService.findMapListForJdbcMapper(sql, param);
			if(list.size() != 0){
				message = "该角色名已存在！";
				return message;
			}
			role.setRoleName(roleName);
			role.setDescrption(descrption);
			publicService.save(role);
			message = "操作成功";
		} catch (Exception e) {
			System.out.println("添加角色出错！");
			return message;
		}
		return message;
	}
	
	/**
	 * 根据ID获取角色信息
	 * @param id
	 * @return
	 */
	@RequestMapping("roleManageGetRoleInfo")
	public @ResponseBody
	List<Map<String, Object>> roleManageGetRoleInfo(String id) {
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("id", id);
		try {
			String sql = "select * from t_role where id = :id";
			list = publicService.findMapListForJdbcMapper(sql, param);
		} catch (Exception e) {
			System.out.println("获取用户信息出错！");
		}
		return list;
	}
	
	/**
	 * 修改角色信息
	 * @param realName
	 * @param phone
	 * @return
	 */
	@RequestMapping("updateRoleSubmit")
	public @ResponseBody
	Integer updateRoleSubmit(String id, String roleName, String descrption) {
		Integer executeDMLForMap = null;
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("id", id);
		param.put("roleName", roleName);
		param.put("descrption", descrption);
		try {
			String sql = "update t_role set roleName = :roleName, descrption = :descrption where id = :id";
			executeDMLForMap = publicService.executeDMLForMap(sql, param);
		} catch (Exception e) {
			System.out.println("修改角色信息出错！");
		}
		return executeDMLForMap;
	}
	
	/**
	 * 获取菜单
	 * @return
	 */
	@RequestMapping("roleManageGetMenuList")
	public @ResponseBody
	Map<String, Object> roleManageGetMenuList(String roleId){
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> map = new HashMap<String, Object>();
		param.put("roleId", roleId);
		String user_role_sql = "select * from t_role_menu where roleId = :roleId";
		List<Map<String, Object>> permistion = publicService.findMapListForJdbcMapper(user_role_sql, param);
		String sql = "select * from t_menu";
		List<Map<String, Object>> list = publicService.findMapListForJdbcMapper(sql, null);
		map.put("list", list);
		map.put("permistion", permistion);
		return map;
	}
	
	/**
	 * 给角色赋予菜单功能
	 * @param roleId
	 * @param checkValue
	 * @return Integer
	 */
	@RequestMapping("submitRoleMenu")
	public @ResponseBody
	Integer submitRoleMenu(String roleId, String checkValue) {
		Integer executeDMLForMap = 0;
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("roleId", roleId);
		try {
			//删除现有菜单
			String sql = "delete from t_role_menu where roleId = :roleId";
			executeDMLForMap = publicService.executeDMLForMap(sql, param);
			//重新赋予菜单
			List<Object[]> bathList = new ArrayList<Object[]>();
			String[] menuIds = checkValue.split(",");
			for(String menuId:menuIds){
				bathList.add(new String[]{roleId,menuId});
			}
			String bathsql = "insert into t_role_menu(roleId, menuId) values(?,?)";
			publicService.batchUpdate(bathsql, bathList);
			executeDMLForMap = 1;
		} catch (Exception e) {
			System.out.println("角色赋予菜单出错！");
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

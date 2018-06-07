$(function(){//主函数
	getRoleList();//获取角色列表
});

/**
 * 获取角色列表
 */
function getRoleList(){
	$.post(ctx +"/roleMageGetRoleList",function(data){
		var html = "";
		$(data).each(function(i, obj){
			html += "<tr>" +
						"<td>"+(i+1)+"</td>"+
						"<td>"+obj.roleName+"</td>"+
						"<td>"+obj.descrption+"</td>"+
						"<td>" +
							"<button type='button' onclick='delRole("+obj.id+")' class='btn btn-danger btn-xs'>删除</button>" +
							"<button type='button' onclick='getRoleById("+obj.id+")' class='btn btn-warning btn-xs'>编辑</button>" +
							"<button type='button' onclick='role_menu("+obj.id+")' class='btn btn-success btn-xs'>权限</button>" +
						"</td>"
					"</tr>";
		});
		$('#role_list_table tbody').html(html);
	});
}

/**
 * 删除角色
 */
function delRole(id){
	var index = layer.msg("确定要删除？", {
	    time: 0, 
	    shade:0.3,
	    btn: ['确定', '取消'],
	    btn1:function(){
	    	$.post(ctx +"/roleManageDelRole",{
	    		"id":id
	    	},function(data){
	    		layer.close(index);
	    		if(data == 1){
	    			layer.msg('操作成功');
	    			getRoleList();
	    		}else{
	    			layer.msg('操作失败');
	    		}
	    	})
	    },
	    btn2:function(){
	    	layer.close(index);
	    }
    });
}

/**
 * 添加角色信息
 */
var addWindow;
function addrole(){
	$('#roleDiv input').val("")
	var $addUser = $('#roleDiv');
	addWindow = layer.open({
      type: 1,
      title:"添加角色",
      area: ['400px', '300px'],
      shadeClose: false, //点击遮罩关闭
      content: $addUser,
      btn: ['提交', '取消'],
	  btn1: function(){
		  addRoleSubmit();
	    //return false 开启该代码可禁止点击该按钮关闭
	  },
	  btn2:function(){
		  layer.close(addWindow);
	  }
    });
}

/**
 * 提交添加角色信息
 */
function addRoleSubmit(){
	var roleName = $('#roleName').val();
	var descrption = $('#descrption').val();
	var flag = role_yz(roleName, descrption);
	if(!flag)return;
	$.post(ctx +"/roleManageAddRoleSubmit",{
		"roleName":roleName,
		"descrption":descrption
	},function(data){
		if(data == "操作成功"){
			layer.close(addWindow);
			getRoleList();
		}
		layer.msg(data);
	})
}

/**
 * 根据id获取角色信息
 */
var addWindow;
function getRoleById(id){
	$.post(ctx +"/roleManageGetRoleInfo",{
		"id":id
	},function(data){
		console.log(data);
		$('#id').val(data[0].id);
		$('#roleName').val(data[0].roleName);
		$('#descrption').val(data[0].descrption);
		var $roleDiv = $('#roleDiv');
		addWindow = layer.open({
	      type: 1,
	      title:"修改角色信息",
	      area: ['400px', '300px'],
	      shadeClose: false, //点击遮罩关闭
	      content: $roleDiv,
	      btn: ['提交', '取消'],
		  btn1: function(){
			  updateRoleSubmit();
		  },
		  btn2:function(){
			  layer.close(addWindow);
		  }
	    });
	});
}

/**
 * 提交修改角色信息
 */
function updateRoleSubmit(){
	var id = $('#id').val();
	var roleName = $('#roleName').val();
	var descrption = $('#descrption').val();
	var flag = role_yz(roleName, descrption);
	if(!flag)return;
	$.post(ctx +"/updateRoleSubmit",{
		"id":id,
		"roleName":roleName,
		"descrption":descrption
	},function(data){
		if(data == 1){
			layer.close(addWindow);
			layer.msg("操作成功");
		}else{
			layer.msg("操作失败");
		}
		
	})
}

/**
 * 验证角色表单信息
 */
function role_yz(roleName, descrption){
	var flag = true;
	if(roleName == ""){
		layer.msg('角色名称不能为空！', function(){});
		return false;
	}
	return flag;
}

/**
 * 获取菜单
 */
var role_menu_div;
function role_menu(roleId){
	$.post(ctx +"/roleManageGetMenuList",{
			"roleId":roleId
			},function(data){
		var html = "";
		$(data.list).each(function(i, obj){
			html += "<div class='checkbox'>" +
					"<lable>";
			var flag = yzPermistion(obj.id, data.permistion);
			if(flag){
				html += "<input type='checkbox' checked='checked' name='role_menu_select' value='"+obj.id+"'>"+obj.name+"";
			}else{
				html += "<input type='checkbox' name='role_menu_select' value='"+obj.id+"'>"+obj.name+"";
			}
			html += "</lable>" +
					"</div>";
		});
		$('#role_menu').html(html);
		//弹窗
		var $role_menu = $('#role_menu');
		role_menu_div = layer.open({
	      type: 1,
	      title:"角色菜单设置",
	      area: ['400px', '400px'],
	      shadeClose: false, //点击遮罩关闭
	      content: $role_menu,
	      btn: ['提交', '取消'],
		  btn1: function(){
			  submitRoleMenu(roleId);
		    //return false 开启该代码可禁止点击该按钮关闭
		  },
		  btn2:function(){
			  layer.close(role_menu_div);
		  }
	    });
	});
}

/**
 * 提交角色菜单功能数据
 */
function submitRoleMenu(roleId){
	var checkValue = "";
	var checks = document.getElementsByName("role_menu_select");
	if(checks&&checks.length>0){
		for(var i=0;i<checks.length;i++){
			if(checks[i].checked){
				if(checkValue.length>0)checkValue+=",";
				checkValue+=checks[i].value;
			}
		}
	}
	//提交选中角色数组
	$.post(ctx +"/submitRoleMenu",{
		"roleId":roleId,
		"checkValue":checkValue
	},function(data){
		if(data == 1){
			layer.close(role_menu_div);
			layer.msg("操作成功");
		}else{
			layer.msg("操作失败");
		}
		
	})
}

/**
 * 判断是否拥有该菜单功能（回显功能）
 */
function yzPermistion(menuId, list){
	var flag = false;
	for(var i=0; i<list.length; i++){
		if(menuId == list[i].menuId){
			return true;
		}
	}
	return flag;
}
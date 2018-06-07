var count = 0;
var datatable;
$(function(){//主函数
	getUserList(getUserName(), 0, 10);//获取用户列表
	pagePading();
});

//分页
function pagePading(){
	datatable=$('#simplePaging').extendPagination({
        totalCount: count,
        showCount: 10,
        limit: 1,
        callback: function (start, end) {
            getUserList(getUserName(), start, end);
        }
    });
}

/**
 * 搜索
 */
function search(){
	getUserList(getUserName(), 0, 10);
	pagePading();
}

function getUserName(){
	return $('#searchName').val();
}

/**
 * 获取用户列表
 * userName(用户名)
 * start(分页起始位置)
 * end(分页结束位置)
 */
function getUserList(userName, start, end){
	$.ajaxSetup({async:false}); 
	$.post(ctx +"/userList",{
			"userName":userName,
			"start":start,
			"end":end
		},function(data){
		html = "";
		$(data.list).each(function(i, obj){
			html += "<tr>" +
						"<td>"+(i+1)+"</td>" +
						"<td>"+obj.userName+"</td>" +
						"<td>"+obj.realName+"</td>" +
						"<td>"+obj.tel+"</td>" +
						"<td id='zt_"+obj.id+"'>"+(obj.isDisable==0?"正常":"禁用")+"</td>" +
						"<td>" +
						"<button type='button' onclick='delUser("+obj.id+")' class='btn btn-danger btn-xs user_list_table_btn'>删除</button>" +
						"<button type='button' onclick='get_user_role("+obj.id+")' class='btn btn-success btn-xs user_list_table_btn'>角色</button>" +
						"<button type='button' onclick='getUserInfo("+obj.id+")' class='btn btn-warning btn-xs user_list_table_btn'>编辑</button>" +
						"<button type='button' id='is_"+obj.id+"' onclick='changeBtn("+obj.id+")' class='btn btn-"+(obj.isDisable==0?"danger":"success")+" btn-xs user_list_table_btn'>"+(obj.isDisable==0?"禁用":"启用")+"</button>" +
						"</td>" +
					"</tr>";
		});
		$('#user_list_table tbody').html(html);
		count = Math.ceil((data.count[0].count)/10);
	})
}

/**
 * 删除用户
 */
function delUser(id){
	var index = layer.msg("确定要删除？", {
	    time: 0, 
	    shade:0.3,
	    btn: ['确定', '取消'],
	    btn1:function(){
	    	$.post(ctx +"/userManageDelUser",{
	    		"id":id
	    	},function(data){
	    		layer.close(index);
	    		if(data == 1){
	    			layer.msg('操作成功');
	    			getUserList(getUserName(), 0, 10);//获取用户列表
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
 * 禁用或启用
 */
function changeBtn(id){
	var btnId = "is_"+id;
	var ztId = "zt_"+id;
	var is = 0;
	if($("#"+btnId+"").html() == "禁用"){
		is = 1
	}else if($("#"+btnId+"").html() == "启用"){
		is = 0
		
	}
	var index = layer.msg((is==1?"确定要禁用？":"确定要启用？"), {
	    time: 0, 
	    shade:0.3,
	    btn: ['确定', '取消'],
	    btn1:function(){
	    	$.post(ctx +"/userManageChangeBtn",{
	    		"id":id,
	    		"is":is
	    	},function(data){
	    		layer.close(index);
	    		if(data == 1){
	    			layer.msg('操作成功');
	    			$("#"+btnId+"").removeClass("btn-danger");
	    			$("#"+btnId+"").removeClass("btn-success");
	    			if(is == 0){
	    				$("#"+btnId+"").addClass("btn-danger");
	    				$("#"+btnId+"").html("禁用");
	    				$("#"+ztId+"").html("启用");
	    			}else if(is ==1){
	    				$("#"+btnId+"").addClass("btn-success");
	    				$("#"+btnId+"").html("启用");
	    				$("#"+ztId+"").html("禁用");
	    			}
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
 * 添加用户
 */
var addWindow;
function addUser(){
	$('#addUserForm input').val("")
	var $addUser = $('#addUserDiv');
	addWindow = layer.open({
      type: 1,
      title:"添加用户",
      area: ['400px', '420px'],
      shadeClose: false, //点击遮罩关闭
      content: $addUser,
      btn: ['提交', '取消'],
	  btn1: function(){
		  addUserSubmit();
	    //return false 开启该代码可禁止点击该按钮关闭
	  },
	  btn2:function(){
		  layer.close(addWindow);
	  }
    });
}

/**
 * 提交添加用户数据
 */
function addUserSubmit(){
	var userName = $('#userName').val();
	var userPwd = $('#userPwd').val();
	var realName = $('#realName').val();
	var phone = $('#phone').val();
	var flag = user_yz(userName, userPwd, realName, phone);
	if(!flag)return;
	$.post(ctx +"/addUserSubmit",{
		"userName":userName,
		"userPwd":userPwd,
		"realName":realName,
		"phone":phone
	},function(data){
		if(data == "操作成功"){
			layer.close(addWindow);
		}
		layer.msg(data);
	})
}


/**
 * 根据ID获取用户信息
 */
var updateWindow;
function getUserInfo(id){
	$.post(ctx +"/userManageGetUserInfo",{
		"id":id
	},function(data){
		$('#userId').val(data[0].id);
		$('#userName').val(data[0].userName);
		$('#userName').attr("readonly","readonly")//将input元素设置为readonly
		$('#userPwd').val(data[0].userPwd);
		$('#userPwd').attr("readonly","readonly")//将input元素设置为readonly
		$('#realName').val(data[0].realName);
		$('#phone').val(data[0].tel);
		var $addUser = $('#addUserDiv');
		updateWindow = layer.open({
	      type: 1,
	      title:"修改用户信息",
	      area: ['400px', '420px'],
	      shadeClose: false, //点击遮罩关闭
	      content: $addUser,
	      btn: ['提交', '取消'],
		  btn1: function(){
			  updateUserSubmit();
		  },
		  btn2:function(){
			  layer.close(updateWindow);
		  }
	    });
	});
	
}

/**
 * 提交更新用户数据
 */
function updateUserSubmit(){
	var userId = $('#userId').val();
	var realName = $('#realName').val();
	var phone = $('#phone').val();
	var flag = user_yz(userName, userPwd, realName, phone);
	if(!flag)return;
	$.post(ctx +"/updateUserSubmit",{
		"userId":userId,
		"realName":realName,
		"phone":phone
	},function(data){
		if(data == 1){
			layer.close(updateWindow);
			layer.msg("操作成功");
		}else{
			layer.msg("操作失败");
		}
		
	})
}


/**
 * 验证用户表单信息
 */
function user_yz(userName, userPwd, realName, phone){
	var flag = true;
	if(userName == ""){
		layer.msg('账号不能为空！', function(){});
		return false;
	}
	if(userPwd == ""){
		layer.msg('密码不能为空！', function(){});
		return false;
	}
	if(realName == ""){
		layer.msg('姓名不能为空！', function(){});
		return false;
	}
	if(phone != ""){
		var myreg=/^[1][3,4,5,7,8][0-9]{9}$/;
		if (!myreg.test(phone)) {  
			layer.msg('联系电话不正确！', function(){});
            return false;  
        }  
	}else{
		layer.msg('联系电话不能为空！', function(){});
		return false;
	}
	return flag;
}

/**
 * 设置用户拥有角色
 */
var roleAlert;
function get_user_role(userId){
	$.post(ctx +"/userManageGetRoleList",{
			"userId":userId
			},function(data){
		var html = "";
		$(data.list).each(function(i, obj){
			html += "<div class='checkbox'>" +
					"<lable>";
			var flag = yzPermistion(obj.id, data.permistion);
			if(flag){
				html += "<input type='checkbox' checked='checked' name='user_role_select' value='"+obj.id+"'>"+obj.roleName+"";
			}else{
				html += "<input type='checkbox' name='user_role_select' value='"+obj.id+"'>"+obj.roleName+"";
			}
			html += "</lable>" +
					"</div>";
		});
		$('#user_role_alert').html(html);
		//弹窗
		var $user_role_alert = $('#user_role_alert');
		roleAlert = layer.open({
	      type: 1,
	      title:"用户角色设置",
	      area: ['400px', '200px'],
	      shadeClose: false, //点击遮罩关闭
	      content: $user_role_alert,
	      btn: ['提交', '取消'],
		  btn1: function(){
			  submitUserRole(userId);
		    //return false 开启该代码可禁止点击该按钮关闭
		  },
		  btn2:function(){
			  layer.close(roleAlert);
		  }
	    });
	});
}

/**
 * 判断是否拥有该角色（回显功能）
 */
function yzPermistion(roleId, list){
	var flag = false;
	for(var i=0; i<list.length; i++){
		if(roleId == list[i].roleId){
			return true;
		}
	}
	return flag;
}

/**
 * 提交用户角色功能数据
 */
function submitUserRole(userId){
	var checkValue = "";
	var checks = document.getElementsByName("user_role_select");
	if(checks&&checks.length>0){
		for(var i=0;i<checks.length;i++){
			if(checks[i].checked){
				if(checkValue.length>0)checkValue+=",";
				checkValue+=checks[i].value;
			}
		}
	}
	//提交选中角色数组
	$.post(ctx +"/submitUserRole",{
		"userId":userId,
		"checkValue":checkValue
	},function(data){
		if(data == 1){
			layer.close(roleAlert);
			layer.msg("操作成功");
		}else{
			layer.msg("操作失败");
		}
		
	})
}

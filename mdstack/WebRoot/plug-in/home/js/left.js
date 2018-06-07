$(function(){//主函数
	getMenu();//加载菜单
});

/**
 * 获取菜单
 */
function getMenu(){
	var userInfo = getNowUserInfo();
	$.post(ctx+"/login/getMenu",{
		"userId":userInfo.userId
	},function(data){
		var html = "";
		$(data).each(function(i, obj){
			if(obj.px < 10){
				html += "<li><a id='menu_"+obj.id+"' onclick='selectMenuChangeBackGroundColor("+obj.id+")' href='"+obj.url+"'>"+obj.name+"</a></li>";
			}else{
				html += "<li><a id='menu_"+obj.id+"' target='weilcome' onclick='selectMenuChangeBackGroundColor("+obj.id+")' href='"+obj.url+"'>"+obj.name+"</a></li>";
			}
		});
		$('#main_menu_ul ul').append(html);
	});
}

/**
 * 获取当前登录用户信息
 */
function getNowUserInfo(){
	var userInfo;
	$.ajaxSetup({async:false}); 
	$.post(ctx +"/login/getNowUserInfo",function(data){
		userInfo =  data;
	})
	$('.header_info a span').eq(0).html(userInfo.userName);
	return userInfo;
}

/**
 * 改变选中菜单背景色
 */
function selectMenuChangeBackGroundColor(id){
	var id = "menu_"+id;
	$('#main_menu_ul ul').removeClass('active');
	$("#"+id+"").addClass('active');
}
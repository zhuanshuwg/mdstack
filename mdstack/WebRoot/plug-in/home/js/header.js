/**
 * 注销登录
 */
function layout(){
	var index = layer.msg("确定要退出吗？", {
	    time: 0, 
	    shade:0.3,
	    btn: ['确定', '取消'],
	    btn1:function(){
	    	$.ajax({
			    type:"POST",
			    url: ctx +"/login/layout",
			    async:false,//同步：意思是当有返回值以后才会进行后面的js程序。
			    success:function(msg){
			        if (msg) {//根据返回值进行跳转
			            window.location.href = ctx +"/login/layout";
			        }
			    }
		    });
	    },
	    btn2:function(){
	    	layer.close(index);
	    }
    });
}
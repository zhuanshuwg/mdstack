package mdstack.taxi.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import mdstack.taxi.service.TaxiServerListener;

@Controller
@RequestMapping("taxiController")
public class TaxiController{
	
	@RequestMapping("takeTaxi")
	public @ResponseBody
	Map<String, Object> userList(HttpServletRequest request, String userName){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("message", "司机已经接单，请等待司机来接您！");
		map.put("driverName", "刘师傅");
		map.put("driverPhone", "18801069061");
		map.put("driverNumber", "蒙L888888");
		System.out.println("接收到请求");
		return map;
	}
	
	public static void main(String[] args) {
		new TaxiServerListener().start();
	}
}

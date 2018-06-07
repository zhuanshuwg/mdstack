package mdstack.app.controller;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import mdstack.login.service.PublicService;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("appController")
public class AppController {
	
	@Autowired
	private PublicService publicService;
	
	private Map<String, Object> tokenMap = new HashMap<String, Object>();
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	
	/**
	 * App用户登录
	 * @param request
	 * @param userId
	 * @param userName
	 * @param userSex
	 * @param userAge
	 * @param userPhoto
	 * @param userType
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("login")
	public @ResponseBody
	Map<String, Object> login(HttpServletRequest request, String userId, String userName, String userSex, String userAge, String userPhoto, String userType, String passWord) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		try {
			JSONObject jsStr = getParam(request);
			System.out.println(jsStr);
			if(jsStr.getString("userId") != null){
				userId = jsStr.getString("userId");
			}else{
				userId = getUUID();
			}
			
			String token = prusTokenById(userId);
			param.put("token", token);
			userName = jsStr.getString("userName");
			userSex = jsStr.getString("userSex");
			userAge = jsStr.getString("userAge");
			if(jsStr.getString("userPhoto") != null){
				userPhoto = jsStr.getString("userPhoto");
			}else{
				userPhoto = "http://p0.qhimg.com/t0195b8900d3acf2510.jpg";
			}
			userType = jsStr.getString("userType");

			System.out.println("用户："+userName+"登录成功");
			if(userId != null && userName != null && userSex != null){
				param.put("userId", userId);
				//查看该用户类型
				String select_user_sql = "select * from ";
				if(userType.equals("qq")){
					select_user_sql += "app_user_qq where userId = :userId";
				}else if(userType.equals("weixin")){
					select_user_sql += "app_user_weixin where userId = :userId";
				}else if(userType.equals("phone")){
					passWord = jsStr.getString("passWord");
					param.put("passWord", passWord);
					select_user_sql += "app_user_phone where userId = :userId";
				}
				List<Map<String, Object>> list = publicService.findMapListForJdbcMapper(select_user_sql, param);
				//判断该用户是否已经注册，如果已经注册，直接获取该用户相关信息，如果第一次登录 则视为注册，先进行用户信息保存
				if(list.size() == 0){
					//新用户
					param.put("userName", userName);
					param.put("userSex", userSex);
					param.put("userType", userType);
					param.put("userAge", userAge);
					param.put("lastLoginTime", df.format(new Date()));
					//通过URL获取该用户头像
					String path = request.getRealPath("/")+"upload/userPhoto/"+userId+"/"+userId+".jpg";
					String headRealPath = "/upload/userPhoto/"+userId+"/"+userId+".jpg";
					param.put("headRealPath", headRealPath);
					//储存头像
					File file = new File(path); 
					System.out.println(file.getParent());
					if(!file.getParentFile().exists()){
						file.getParentFile().mkdirs();  
					}
					downloadPicture(userPhoto,path);  
					param.put("userPhoto", path);
					String insert_user_sql ="";
					if(userType.equals("phone")){
						insert_user_sql += "INSERT INTO app_user_phone (userId, userType, userName, userSex, userAge, headPhoto, token, lastLoginTime, passWord, headRealPath) "
								+ "VALUES (:userId, :userType, :userName, :userSex, :userAge, :userPhoto, :token, :lastLoginTime, :passWord, :headRealPath)";
					}else if(userType.equals("qq")){
						insert_user_sql += "INSERT INTO app_user_qq (userId, userType, userName, userSex, userAge, headPhoto, token, lastLoginTime, headRealPath) "
								+ "VALUES (:userId, :userType, :userName, :userSex, :userAge, :userPhoto, :token, :lastLoginTime, :headRealPath)";
					}else if(userType.equals("weixin")){
						insert_user_sql += "INSERT INTO app_user_weixin (userId, userType, userName, userSex, userAge, headPhoto, token, lastLoginTime, headRealPath) "
								+ "VALUES (:userId, :userType, :userName, :userSex, :userAge, :userPhoto, :token, :lastLoginTime, :headRealPath)";
					}
					publicService.executeDMLForMap(insert_user_sql, param);
					map.put("userId", userId);
					map.put("friendsNum", 0);//好友数量
					map.put("followNum", 0);//关注人数
					map.put("fansNum", 0);//粉丝人数
					map.put("groupNum", 0);//群组数量
					map.put("isMember", "false");//是否为会员
					map.put("autograph", "");//个性签名
					map.put("message", 0);//返回状态
					map.put("token", token);//令牌
				}else{
					//更新token
					String update_token_sql = "UPDATE ";
					if(userType.equals("phone")){
						update_token_sql += "app_user_phone set token = :token where userId = :userId";
					}else if(userType.equals("qq")){
						update_token_sql += "app_user_qq set token = :token where userId = :userId";
					}else if(userType.equals("weixin")){
						update_token_sql += "app_user_weixin set token = :token where userId = :userId";
					}
					publicService.executeDMLForMap(update_token_sql, param);
					//老用户查询该用户相关信息并返回数据
					String select_userInfo_sql = "";
					map.put("userId", userId);
					map.put("friendsNum", 10);//好友数量
					map.put("followNum", 20);//关注人数
					map.put("fansNum", 30);//是否为会员
					map.put("groupNum", 40);//个性签名
					map.put("isMember", "false");//是否为会员
					map.put("autograph", "");//个性签名
					map.put("message", 0);//返回状态
					map.put("token", token);//令牌
				}
			}
		} catch (Exception e) {
			map.put("message", 1);
			return map;
		}
		System.out.println("登录返回状态"+map);
		return map;
	}
	
	@RequestMapping("twoLanding")
	public @ResponseBody
	Map<String, Object> twoLanding(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		JSONObject jsStr = getParam(request);
		System.out.println(jsStr);
		if(jsStr.getString("userId") != null){
			String userId = jsStr.getString("userId");
			String userType = jsStr.getString("userType");
			String token = jsStr.getString("token");
			param.put("userId", userId);
			param.put("token", token);
			String select_userInfo_sql = "select * from ";
			if(userType.equals("phone")){
				select_userInfo_sql += "app_user_phone where userId = :userId and token = :token";
			}else if(userType.equals("qq")){
				select_userInfo_sql += "app_user_qq where userId = :userId and token = :token";
			}else if(userType.equals("weixin")){
				select_userInfo_sql += "app_user_weixin where userId = :userId and token = :token";
			}
			List<Map<String, Object>> list = publicService.findMapListForJdbcMapper(select_userInfo_sql, param);
			if(list.size() == 0){
				map.put("message", 1);
				return map;
			}else{
				map.put("userId", userId);
				map.put("friendsNum", 0);//好友数量
				map.put("followNum", 0);//关注人数
				map.put("fansNum", 0);//粉丝人数
				map.put("groupNum", 0);//群组数量
				map.put("isMember", "false");//是否为会员
				map.put("autograph", list.get(0).get("autograph"));//个性签名
				map.put("headPhoto", list.get(0).get("headRealPath"));//返回状态
				map.put("userName", list.get(0).get("userName"));//返回状态
				map.put("message", 0);//返回状态
			}
		}else{
			map.put("message", "二次登陆id获取失败！");
		}
		return map;
	}
	
	/**
	 * 更新App当前位置
	 * @param request
	 * @param userId
	 * @param userType
	 * @param latitude伟度
	 * @param longitude经度
	 * @return
	 */
	@RequestMapping("updatePosition")
	public @ResponseBody
	Map<String, Object> updatePosition(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		try {
			JSONObject jsStr = getParam(request);
			System.out.println(jsStr);
			String userType = jsStr.getString("userType");
			String userId = jsStr.getString("userId");
			String x = jsStr.getString("longitude");
			String y = jsStr.getString("latitude");
			param.put("userId", userId);
			param.put("userType", userType);
			param.put("x", x);
			param.put("y", y);
			String dw_sql = "UPDATE ";
			if(userType.equals("qq")){
				dw_sql += "app_user_qq set x=:x, y=:y ";
			}else if(userType.equals("weixin")){
				dw_sql += "app_user_weixin set x=:x, y=:y ";
			}else if(userType.equals("phone")){
				dw_sql += "app_user_phone set x=:x, y=:y ";
			}
			dw_sql += " where userId = :userId";
			publicService.executeDMLForMap(dw_sql, param);
		} catch (Exception e) {
			System.out.println("更新当前位置出错");
			map.put("message", 1);
		}
		map.put("message", 0);
		return map;
	}
	
	public static void main(String[] args) throws IOException {
		String aa = getToken();
		System.out.println( aa);
	}
	
	/**
	 * 生成Token
	 * @return
	 */
	public static String getToken(){
	    UUID uuid=UUID.randomUUID();
	    String str = uuid.toString()+UUID.randomUUID()+UUID.randomUUID()+UUID.randomUUID()+UUID.randomUUID(); 
	    String uuidStr=str.replace("-", "");
	    return uuidStr;
	  }
	
	/**
	 * 根据id生成Token
	 * @param userId
	 * @return uuidStr
	 */
	public String prusTokenById(String userId) {
		if(userId == null){
			userId = getUUID();
		}
		UUID uuid=UUID.randomUUID();
	    String str = DigestUtils.md5Hex(userId)+uuid.toString()+UUID.randomUUID()+UUID.randomUUID()+UUID.randomUUID()+UUID.randomUUID(); 
	    String uuidStr=str.replace("-", "");
	    tokenMap.put(userId, uuidStr);
	    System.out.println("userId==="+uuidStr);
	    System.out.println(tokenMap);
		return uuidStr;
	}
	
	/**
	 * 验证该用户Token是否有效
	 * @param userId
	 * @param Token
	 * @return
	 */
	public boolean verificationToken(String userId, String Token){
		for (String key : tokenMap.keySet()) {
			if(key.equals(userId)){
				if(tokenMap.get(userId).equals(Token)){
					return true;
				}
			}
		} 
		return false;
	}
	
	/**
	 * 手机注册id
	 * @return
	 */
	public static String getUUID(){
	    UUID uuid=UUID.randomUUID();
	    String str = uuid.toString(); 
	    String uuidStr=str.replace("-", "");
	    return uuidStr;
	  }
	
	/**
	 * 链接url下载图片
	 * @param urlList
	 * @param path
	 */
    private static void downloadPicture(String urlList,String path) {  
        URL url = null;  
        try {  
            url = new URL(urlList);  
            DataInputStream dataInputStream = new DataInputStream(url.openStream());  
  
            FileOutputStream fileOutputStream = new FileOutputStream(new File(path));  
            ByteArrayOutputStream output = new ByteArrayOutputStream();  
  
            byte[] buffer = new byte[1024];  
            int length;  
  
            while ((length = dataInputStream.read(buffer)) > 0) {  
                output.write(buffer, 0, length);  
            }  
            fileOutputStream.write(output.toByteArray());  
            dataInputStream.close();  
            fileOutputStream.close();  
        } catch (MalformedURLException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }  
	
    /**
     * 获取前台传参
     * @param request
     * @return
     */
	public JSONObject getParam(HttpServletRequest request){
		JSONObject jsStr = new JSONObject();
		try {
			BufferedReader br = request.getReader();
			jsStr = JSONObject.fromObject(br.readLine());
		} catch (Exception e) {
			System.out.println("获取参数出错!");
		}
		return jsStr;
	}
	
	
	

	public PublicService getPublicService() {
		return publicService;
	}

	public void setPublicService(PublicService publicService) {
		this.publicService = publicService;
	}
	
	
	
	
	
}

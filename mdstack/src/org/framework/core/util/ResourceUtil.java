package org.framework.core.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;


/**
 * 项目参数工具类
 * 
 */
public class ResourceUtil {

	private static ResourceBundle bundle_sys = java.util.ResourceBundle.getBundle("sysconfig");
	
	private static ResourceBundle bundle_db = java.util.ResourceBundle.getBundle("dbconfig");
	/**
	 * 
	 * @param config_file 配置文件
	 * @param key 主键
	 * @return
	 */
	public static String get(String config_file, String key){
		String value = "";
		if(config_file.equals("sysconfig")) value = bundle_sys.getString(key);
		if(config_file.equals("dbconfig")) value = bundle_db.getString(key);
		return value;
	}
	
	
	@SuppressWarnings("static-access")
	public static void reLoadConfigFile(String config_file){
		if(config_file.equals("sysconfig")){
			bundle_sys.clearCache();
			bundle_sys = java.util.ResourceBundle.getBundle("sysconfig");
		}
		if(config_file.equals("dbconfig")){
			bundle_db.clearCache();
			bundle_db = java.util.ResourceBundle.getBundle("sysconfig");
		}
	}

	/**
	 * 获取session定义名称
	 * 
	 * @return
	 */
	public static final String getSessionattachmenttitle(String sessionName) {
		return bundle_sys.getString(sessionName);
	}
	

	
	
	/**
	 * 获得请求路径
	 * 
	 * @param request
	 * @return
	 */
	public static String getRequestPath(HttpServletRequest request) {
		String requestPath = request.getRequestURI() + "?" + request.getQueryString();
		if (requestPath.indexOf("&") > -1) {// 去掉其他参数
			requestPath = requestPath.substring(0, requestPath.indexOf("&"));
		}
		requestPath = requestPath.substring(request.getContextPath().length() + 1);// 去掉项目路径
		return requestPath;
	}

	/**
	 * 获取配置文件参数
	 * 
	 * @param name
	 * @return
	 */
	public static final String getConfigByName(String name) {
		return bundle_sys.getString(name);
	}

	/**
	 * 获取配置文件参数
	 * 
	 * @param name
	 * @return
	 */
	public static final Map<Object, Object> getConfigMap(String path) {
		ResourceBundle bundle = ResourceBundle.getBundle(path);
		Set set = bundle.keySet();
		return oConvertUtils.SetToMap(set);
	}

	
	
	public static String getSysPath() {
		String path = Thread.currentThread().getContextClassLoader().getResource("").toString();
		String temp = path.replaceFirst("file:/", "").replaceFirst("WEB-INF/classes/", "");
		String separator = System.getProperty("file.separator");
		String resultPath = temp.replaceAll("/", separator + separator).replaceAll("%20", " ");
		return resultPath;
	}

	/**
	 * 获取项目根目录
	 * 
	 * @return
	 */
	public static String getPorjectPath() {
		String nowpath; // 当前tomcat的bin目录的路径 如
						// D:\java\software\apache-tomcat-6.0.14\bin
		String tempdir;
		nowpath = System.getProperty("user.dir");
		tempdir = nowpath.replace("bin", "webapps"); // 把bin 文件夹变到 webapps文件里面
		tempdir += "\\"; // 拼成D:\java\software\apache-tomcat-6.0.14\webapps\sz_pro
		return tempdir;
	}

	public static String getClassPath() {
		String path = Thread.currentThread().getContextClassLoader().getResource("").toString();
		String temp = path.replaceFirst("file:/", "");
		String separator = System.getProperty("file.separator");
		String resultPath = temp.replaceAll("/", separator + separator);
		return resultPath;
	}

	public static String getSystempPath() {
		return System.getProperty("java.io.tmpdir");
	}

	public static String getSeparator() {
		return System.getProperty("file.separator");
	}

	public static String getParameter(String field) {
		HttpServletRequest request = ContextHolderUtils.getRequest();
		return request.getParameter(field);
	}

	/**
	 * 获取数据库类型
	 * 
	 * @return
	 */
	public static final String getJdbcUrl() {
		return bundle_db.getString("jdbc.url").toLowerCase();
	}
	
	/**
	 *修改配置文件(与流量有关)
	 * @param request
	 * @param pa
	 * @param se
	 * @param fs
	 */
	public final void writeFLow(HttpServletRequest request,String pa,String se,String fs){
		String path = this.getClass().getClassLoader().getResource("").toString();
		if (path.startsWith("file")) {
			path=path.substring(6);
		}
		path+="sysconfig.properties";
		fs=fs.replace(",", "");
		fs=String.valueOf(Integer.parseInt(fs)*1024*1024);
		Properties prop = new Properties();
		try {
			InputStream fis = new FileInputStream(path);
			prop.load(fis);
			fis.close();
			OutputStream fos = new FileOutputStream(path); 
			prop.setProperty("pcap.packet.limit", pa);
			prop.setProperty("pcap.session.limit", se);
			prop.setProperty("pcap.size.limit", fs);
			prop.store(fos, "");
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	/**
	 * 修改配置文件(与日志有关)
	 * @param request
	 */
	public final void writeLogs(HttpServletRequest request,String radior,String maxSize){
		String path = this.getClass().getClassLoader().getResource("").toString();
		if (path.startsWith("file")) {
			path=path.substring(6);
		}
		path+="sysconfig.properties";
		if (radior.equals("r1")) {
			radior="1";
		}else {
			radior="0";
		}
		try {
			Properties prop = new Properties();
			InputStream fis = new FileInputStream(path);
			prop.load(fis);
			fis.close();
			OutputStream fos = new FileOutputStream(path);
			prop.setProperty("is.show.all.fields", radior);
			prop.setProperty("export.max.size", maxSize);
			prop.store(fos, "");
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

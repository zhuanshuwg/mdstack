package org.framework.core.common.dao.elasticsearch;

import org.framework.core.util.ResourceUtil;

import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;
import io.searchbox.client.http.JestHttpClient;

/**
 * 单例模式获取JestClient
 * @author HouqiZhang
 *
 */
public class SingletonJestClient {	
	
	public static JestHttpClient jestClient;
		
	private SingletonJestClient(){
		
	}
	private static class JestClientHolder{
		
		static SingletonJestClient instance = new SingletonJestClient();
		
	}
	
	public static SingletonJestClient getInstance(){
		
		return JestClientHolder.instance;
		
	}
	
	public static JestHttpClient getJestClient(){
		
		if (jestClient == null) {
			
			String connectionUrl = ResourceUtil.get("sysconfig",
					"elasticsearch.url");
			
			HttpClientConfig clientConfig = new HttpClientConfig.Builder(connectionUrl)
			        .readTimeout(300000)		//等待数据超时时间:5分钟
			        .connTimeout(60000)         //连接请求超时时间：60秒
			        .defaultMaxTotalConnectionPerRoute(200)
			        .maxTotalConnection(400)
					.multiThreaded(true).build();
			
			JestClientFactory factory = new JestClientFactory();
			
			factory.setHttpClientConfig(clientConfig);
			
			jestClient = (JestHttpClient)factory.getObject();
			
		}
		
		return jestClient;
	}
}
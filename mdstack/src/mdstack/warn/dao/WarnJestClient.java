package mdstack.warn.dao;

import org.framework.core.util.ResourceUtil;

import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;
import io.searchbox.client.http.JestHttpClient;

public class WarnJestClient {	
	
	public static JestHttpClient jestClient;
	
	public static String  indexName;
		
	private WarnJestClient(){
		
	}
	private static class JestClientHolder{
		
		static WarnJestClient instance = new WarnJestClient();
		
	}
	
	public static WarnJestClient getInstance(){
		
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
	public static String getIndexName(){
		
	    indexName=ResourceUtil.get("sysconfig","elasticsearch.index.warn.prefix");
	    
		return indexName;
		
	}
}
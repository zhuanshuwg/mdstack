package mdstack.warn.dao;

import io.searchbox.action.Action;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.indices.IndicesExists;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

@Repository("warnDao")
public class WarnDao {

	private Logger logger = Logger.getLogger(WarnDao.class);
	
	
	
	/**
	 * 索引是否存在
	 */
	public boolean indexExists(String index){
		if(index!=null){
			IndicesExists indicesExists = new IndicesExists.Builder(index).build();
			
			JestResult result = this.getJestResult(indicesExists);
			
			return Boolean.valueOf(result.getValue("ok").toString());
		}
		return false;
		
	}
	
	
	/**
	 * 获取JestResult
	 * @param action
	 * @return
	 */
	public JestResult getJestResult(Action action){
		JestResult result = null;
		JestClient client = null;
		try {
			client = this.getJestClient();
			result = client.execute(action);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}finally{
			//client.shutdownClient();
		}
		if (result.isSucceeded() == false){
			result = null;
		}
		return result;
	}

	/**
	 * Get JestClient
	 * @return
	 */
	public JestClient getJestClient() {
		return WarnJestClient.getInstance().getJestClient();
	}
    public String getIndexName(){
    	return WarnJestClient.getIndexName();
    }
}
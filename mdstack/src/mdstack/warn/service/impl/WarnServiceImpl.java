package mdstack.warn.service.impl;

import io.searchbox.client.JestClient;
import io.searchbox.indices.CreateIndex;
import mdstack.warn.service.WarnService;

import org.framework.core.common.service.impl.CommonServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("warnService")
@Transactional
public class WarnServiceImpl extends CommonServiceImpl implements WarnService{

	
	@Override
	public void createIndex(String indexName,JestClient client) {
		int a=0;
		try {
			
			client.execute(new CreateIndex.Builder(indexName).build());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



}

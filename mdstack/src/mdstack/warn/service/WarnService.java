package mdstack.warn.service;

import io.searchbox.client.JestClient;

import org.framework.core.common.service.CommonService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("warnService")
@Transactional
public interface WarnService extends CommonService{


/**
 * 创建索引
 * @param indexName
 * @param client 
 */
public void createIndex(String indexName, JestClient client);
}

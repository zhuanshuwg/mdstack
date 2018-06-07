package org.framework.core.statement;

import java.io.IOException;
import java.util.Map;
/**
 * 动态语句组装接口
 * @author wangguan
 *
 */
public interface DynamicStatementBuilder {
	
	public Map<String,String> getHqls();
	public Map<String,String> getSqls();
	public void init() throws IOException;

}

package org.framework.core.common.dao;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.framework.core.common.exception.BusinessException;
import org.framework.core.statement.DynamicStatementBuilder;
import org.framework.core.statement.Statement;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;

public class BaseDao implements InitializingBean{
	
	private static final Logger log = Logger.getLogger(BaseDao.class);
	
	/**
	 * 动态模板缓存
	 */
	protected Map<String, Statement> templateCache;
	/**
	 * 动态语句组装器
	 */
	protected DynamicStatementBuilder dynamicStatementBuilder;

	public DynamicStatementBuilder getDynamicStatementBuilder() {
		return dynamicStatementBuilder;
	}
	
	@Autowired
	@Qualifier("dynamicStatementBuilder")
	public void setDynamicStatementBuilder(
			DynamicStatementBuilder dynamicStatementBuilder) {
		this.dynamicStatementBuilder = dynamicStatementBuilder;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		templateCache = new HashMap<String, Statement>();
		if(this.dynamicStatementBuilder == null){
			log.error("dynamicStatementBuilder初始化失败！");
		}
		dynamicStatementBuilder.init();
		Map<String,String> hqls = dynamicStatementBuilder.getHqls();
		Map<String,String> sqls = dynamicStatementBuilder.getSqls();
		Configuration configuration = new Configuration();
		configuration.setClassicCompatible(true);
		configuration.setNumberFormat("#");
		StringTemplateLoader stringLoader = new StringTemplateLoader();
		
		for(Entry<String, String> entry : hqls.entrySet()){
			stringLoader.putTemplate(entry.getKey(), entry.getValue());
			templateCache.put(entry.getKey(), new Statement(Statement.Type.HQL,new Template(entry.getKey(),new StringReader(entry.getValue()),configuration)));
		}
		for(Entry<String, String> entry : sqls.entrySet()){
			stringLoader.putTemplate(entry.getKey(), entry.getValue());
			templateCache.put(entry.getKey(), new Statement(Statement.Type.SQL,new Template(entry.getKey(),new StringReader(entry.getValue()),configuration)));
		}
		configuration.setTemplateLoader(stringLoader);
		
	}
	
	/**
	 * 根据语句模板和参数处理，返回结果语句
	 * @param statementId
	 * @param parameters
	 * @return
	 */
	protected String getHqlStatement(String statementId ,Map<String, ?> parameters){
		Statement statement = templateCache.get(statementId);
		String hql = "";
		if(statement.getType().equals(Statement.Type.HQL)){
			StringWriter stringWriter = new StringWriter();
			try {
				statement.getTemplate().process(parameters, stringWriter);
			} catch (Exception e) {
				log.error(e.getMessage());
			}
			hql = stringWriter.toString();
		}else{
			throw new BusinessException(statementId + ":对应的语句不是HQL语句");
		}
		return hql;
	}
	
	/**
	 * 根据语句模板和参数处理，返回结果语句
	 * @param statementId
	 * @param parameters
	 * @return
	 */
	protected String getSqlStatement(String statementId ,Map<String, ?> parameters){
		Statement statement = templateCache.get(statementId);
		String sql = "";
		if(statement!=null){
			if(statement.getType().equals(Statement.Type.SQL)){
				StringWriter stringWriter = new StringWriter();
				try {
					statement.getTemplate().process(parameters, stringWriter);
				} catch (Exception e) {
					log.error(e.getMessage());
				}
				sql = stringWriter.toString();
			}else{
				throw new BusinessException(statementId + ":对应的语句不是SQL语句");
			}
		}else{
			sql=statementId;
		}
		
		
		return sql;
	}

}

package org.framework.core.common.service.impl;

import java.io.Serializable;
import java.sql.Connection;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.framework.core.common.dao.ICommonDao;
import org.framework.core.common.dao.elasticsearch.ElasticsearchDao;
import org.framework.core.common.dao.jdbc.JdbcDao;
import org.framework.core.common.dto.datatable.SortInfo;
import org.framework.core.common.model.common.Pagination;
import org.framework.core.common.model.common.SessionInfo;
import org.framework.core.common.service.CommonService;
import org.framework.core.util.ContextHolderUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mdstack.log.entity.Log;

/**
 * 通用service
 * 
 * @author wangguan
 * @version v1.0
 */
@Service("commonService")
@Transactional
public class CommonServiceImpl implements CommonService {
	
	public Connection getConnection(){
		return jdbcDao.getConnection();
	}
	
	/************* Hiberante 操作方法 ******************/
	public <T> void save(T entity){
		this.commonDao.save(entity);
	}

	public <T> void saveOrUpdate(T entity){
		this.commonDao.saveOrUpdate(entity);
	}

	public <T> void updateEntity(T entity){
		this.commonDao.updateEntity(entity);
	}

	public <T> void batchSave(List<T> entities){
		this.commonDao.batchSave(entities);
	}

	public <T> void delete(T entity){
		this.commonDao.delete(entity);
	}

	public <T> void deleteEntityById(Class<T> entityClass, Serializable id){
		this.commonDao.deleteEntityById(entityClass, id);
	}

	public <T> void deleteAllEntities(Collection<T> entities){
		this.commonDao.deleteAllEntities(entities);
	}

	public <T> T getEntityById(Class<T> entityClass, Serializable id){
		return this.commonDao.getEntityById(entityClass, id);
	}

	public <T> T getUniqueByProperty(Class<T> entityClass, String propertyName,
			Object value){
		return this.commonDao.getUniqueByProperty(entityClass, propertyName, value);
	}

	public <T> List<T> loadAll(final Class<T> entityClass){
		return this.commonDao.loadAll(entityClass);
	}
	
	public <T> List<T> loadAll(final Class<T> entityClass, String propertyName, boolean isAsc){
		return this.commonDao.loadAll(entityClass, propertyName, isAsc);
	}
	
	public <T> List<T> getListByProperty(Class<T> entityClass, String propertyName, Object value) {
		return this.commonDao.getListByProperty(entityClass, propertyName, value);
	}

	/************* Spring Jdbc 操作方法 ******************/

	public Integer executeDMLForMap(String sql, Map<String, Object> parameters){
		return this.jdbcDao.executeDMLForMap(sql, parameters);
	}
	
	public int[] batchUpdate(final String sql, List<Object[]> batch){
		return this.jdbcDao.batchUpdate(sql, batch);
	}
	
	public int[] batchUpdate(final String sql, Map<String,?>[] batchValues){
		return this.jdbcDao.batchUpdate(sql, batchValues);
	}

	@Override
	public Map<String,Object> findMapForJdbcMapper(final String statementId,
			Map<String, Object> parameters) {
		return jdbcDao.findMapForJdbcMapper(statementId, parameters);
	}

	@Override
	public Map<String,Object> findMapForJdbc(final String sql, Map<String, Object> parameters) {
		return this.jdbcDao.findMapForJdbc(sql, parameters);
	}

	@Override
	public <T> T findBeanForJdbcMapper(final String statementId,
			Class<T> entityClass, Map<String, Object> parameters) {
		return this.jdbcDao.findBeanForJdbcMapper(statementId, entityClass,
				parameters);
	}

	@Override
	public <T> T findBeanForJdbc(final String sql, Class<T> entityClass,
			Map<String, Object> parameters) {
		return this.jdbcDao.findBeanForJdbc(sql, entityClass, parameters);
	}

	@Override
	public List<Map<String, Object>> findMapListForJdbcMapper(
			final String statementId, Map<String, Object> parameters) {
		return this.jdbcDao.findMapListForJdbcMapper(statementId, parameters);
	}

	@Override
	public List<Map<String, Object>> findMapListForJdbc(final String sql,
			Map<String, Object> parameters) {
		return this.jdbcDao.findMapListForJdbc(sql, parameters);
	}

	@Override
	public <T> List<T> findBeanListForJdbcMapper(final String statementId,
			Class<T> clazz, Map<String, Object> parameters) {
		return this.jdbcDao.findBeanListForJdbcMapper(statementId, clazz, parameters);
	}

	@Override
	public <T> List<T> findBeanListForJdbc(final String sql, Class<T> clazz,
			Map<String, Object> parameters) {
		return this.jdbcDao.findBeanListForJdbc(sql, clazz, parameters);
	}

	@Override
	public <T> Pagination<T> findPaginationForBeanList(final String statementId,
			Class<T> clazz, Map<String, Object> parameters,int start,int length) {
		return this.jdbcDao.findPaginationForBeanList(statementId, clazz, parameters, start, length);
	}

	@Override
	public Pagination<Map<String,Object>> findPaginationForMapList(final String statementId,
			Map<String,Object> parameters,int start,int length, String [][] sort) {
		return this.jdbcDao.findPaginationForMapList(statementId, parameters, start, length, sort);
	}
	
	@Override
	public Pagination<Map<String,Object>> findPaginationForMapList1(final String statementId,
			Map<String,Object> parameters,int start,int length,SortInfo[] sortInfo) {
		return this.jdbcDao.findPaginationForMapList1(statementId, parameters, start, length, sortInfo);
	}
	@Override
	public Pagination<Map<String,Object>> findPaginationForMapList2(final String statementId,
			Map<String,Object> parameters,int start,int length,SortInfo[] sortInfo) {
		return this.jdbcDao.findPaginationForMapList2(statementId, parameters, start, length, sortInfo);
	}
	/*---------------------------------elasticsearch 操作方法------------------------------*/
	
	/*-------------------------------- 日志操作 ------------------------------------------*/
	 
	public void saveLog(String log_type,String description){
		
		Log log = new Log();
//		log.setCreatime(new Date());
//		log.setLogType(log_type);
//		log.setDescription(description);
		
		String creator = "";
		HttpSession session = ContextHolderUtils.getSession();
		if(session != null){
			SessionInfo sessionInfo = (SessionInfo) session.getAttribute("SESSION_INFO");
			if(sessionInfo!=null){
//				creator = sessionInfo.getUser().getUserName();
			}
		}
//		log.setCreator(creator);
		
		commonDao.save(log);
		
	}

	/*---------------------------------初始化全局字段---------------------------------*/

	public ICommonDao commonDao = null;
	public JdbcDao jdbcDao = null;
	public ElasticsearchDao elasticsearchDao = null;
	
	public ElasticsearchDao getElasticsearchDao() {
		return elasticsearchDao;
	}
	@Autowired
	public void setElasticsearchDao(ElasticsearchDao elasticsearchDao) {
		this.elasticsearchDao = elasticsearchDao;
	}

	public ICommonDao getCommonDao() {
		return commonDao;
	}

	@Autowired
	public void setCommonDao(ICommonDao commonDao) {
		this.commonDao = commonDao;
	}

	public JdbcDao getJdbcDao() {
		return jdbcDao;
	}

	@Autowired
	public void setJdbcDao(JdbcDao jdbcDao) {
		this.jdbcDao = jdbcDao;
	}

}

package org.framework.core.common.service;

import java.io.Serializable;
import java.sql.Connection;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.framework.core.common.dto.datatable.SortInfo;
import org.framework.core.common.model.common.Pagination;

/**
 * 通用service接口
 * 
 * @author wangguan
 * @version v1.0
 */
public interface CommonService {
	
	public Connection getConnection();

	/************* Hiberante 操作方法 ******************/
	public <T> void save(T entity);

	public <T> void saveOrUpdate(T entity);

	public <T> void updateEntity(T entity);

	public <T> void batchSave(List<T> entitys);

	public <T> void delete(T entity);

	public <T> void deleteEntityById(Class<T> entityClass, Serializable id);

	public <T> void deleteAllEntities(Collection<T> entities);

	public <T> T getEntityById(Class<T> entityClass, Serializable id);

	public <T> T getUniqueByProperty(Class<T> entityClass, String propertyName,
			Object value);

	public <T> List<T> loadAll(final Class<T> entityClass);
	
	public <T> List<T> loadAll(final Class<T> entityClass, String propertyName, boolean isAsc);
	
	public <T> List<T> getListByProperty(Class<T> entityClass, String propertyName, Object value);

	/************* Spring Jdbc 操作方法 ******************/

	
	public Integer executeDMLForMap(String sql, Map<String, Object> parameters);
	
	public int[] batchUpdate(final String sql, List<Object[]> batch);
	
	public int[] batchUpdate(final String sql, Map<String,?>[] batchValues);
	
	public Map<String, Object> findMapForJdbcMapper(final String statementId,
			Map<String, Object> parameters);

	public Map<String, Object> findMapForJdbc(final String sql,
			Map<String, Object> parameters);

	public <T> T findBeanForJdbcMapper(final String statementId,
			Class<T> entityClass, Map<String, Object> parameters);

	public <T> T findBeanForJdbc(final String sql, Class<T> entityClass,
			Map<String, Object> parameters);

	public List<Map<String, Object>> findMapListForJdbcMapper(
			final String statementId, Map<String, Object> parameters);

	public List<Map<String, Object>> findMapListForJdbc(final String sql,
			Map<String, Object> parameters);

	public <T> List<T> findBeanListForJdbcMapper(final String statementId,
			Class<T> clazz, Map<String, Object> parameters);

	public <T> List<T> findBeanListForJdbc(final String sql, Class<T> clazz,
			Map<String, Object> parameters);

	public <T> Pagination<T> findPaginationForBeanList(final String statementId,
			Class<T> clazz, Map<String, Object> parameters, int start,
			int length);

	public Pagination<Map<String,Object>> findPaginationForMapList(final String statementId,
			Map<String, Object> parameters, int start, int length, String[][] sort);
	
	
	public Pagination<Map<String,Object>> findPaginationForMapList1(final String statementId,
			Map<String, Object> parameters, int start, int length, SortInfo[] sprtinfo);
	
	public Pagination<Map<String,Object>> findPaginationForMapList2(final String statementId,
			Map<String, Object> parameters, int start, int length, SortInfo[] sprtinfo);
	
	/*---------------------------------elasticsearch 操作方法------------------------------*/
	
	public void saveLog(String log_type,String description);
	
}

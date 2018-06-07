package org.framework.core.common.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.springframework.util.Assert;


/**
 * 通用dao接口
 * @author wangguan
 * @version v1.0
 */
public interface ICommonDao {
	
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
	
	public <T> List<T> getListByProperty(Class<T> entityClass, String propertyName, Object value) ;
	
}


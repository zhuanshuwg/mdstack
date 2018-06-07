package org.framework.core.common.dao.impl;

/**
 * 公共扩展方法
 */
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.framework.core.common.dao.ICommonDao;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
/**
 * 通用dao
 * @author wangguan
 * @version v1.0
 */
@Repository("commonDao")
public class CommonDao implements ICommonDao {
	/**
	 * 初始化Log4j的一个实例
	 */
	private static final Logger logger = Logger.getLogger(CommonDao.class);
	/**
	 * 注入一个sessionFactory属性,并注入到父类(HibernateDaoSupport)
	 * **/
	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;

	public Session getSession() {
		// 事务必须是开启的(Required)，否则获取不到
		return sessionFactory.getCurrentSession();
	}
	
	
	/**
	 * 根据传入的实体持久化对象
	 */
	public <T> void save(T entity) {
		
		getSession().save(entity);
		getSession().flush();
			
	}

	/**
	 * 根据传入的实体添加或更新对象
	 * 
	 * @param <T>
	 * 
	 * @param entity
	 */

	public <T> void saveOrUpdate(T entity) {
		
		getSession().saveOrUpdate(entity);
		getSession().flush();
			
	}

	public <T> void updateEntity(T entity) {

		getSession().update(entity);
		getSession().flush();

	}

	/**
	 * 批量保存数据
	 * @param <T>
	 * @param entitys 要持久化的临时实体对象集合
	 */
	public <T> void batchSave(List<T> entitys) {
		for (int i=0; i<entitys.size();i++) {
			getSession().save(entitys.get(i));
			if (i % 20 == 0) {
				//20个对象后才清理缓存，写入数据库
				getSession().flush();
				getSession().clear();
			}
			
		}
	}

	/**
	 * 根据传入的实体删除对象
	 */
	public <T> void delete(T entity) {
			getSession().delete(entity);
			getSession().flush();
		
	}

	/**
	 * 根据主键删除指定的实体
	 * 
	 * @param <T>
	 * @param pojo
	 */
	public <T> void deleteEntityById(Class<T> entityName, Serializable id) {
		delete(getEntityById(entityName, id));
		getSession().flush();
	}

	public <T> void deleteAllEntities(Collection<T> entities){
		for (Object entity : entities) {
			getSession().delete(entity);
			getSession().flush();
		}
	}

	public <T> T getEntityById(Class<T> entityClass, Serializable id){
		return (T) getSession().get(entityClass, id);
	}

	public <T> T getUniqueByProperty(Class<T> entityClass, String propertyName,
			Object value){
		Assert.hasText(propertyName);
		return (T) createCriteria(entityClass, Restrictions.eq(propertyName, value)).uniqueResult();
	}

	public <T> List<T> loadAll(final Class<T> entityClass) {
		Criteria criteria = createCriteria(entityClass);
		return criteria.list();
	}
	
	public <T> List<T> loadAll(final Class<T> entityClass, String propertyName, boolean isAsc) {
		Criteria criteria = createCriteria(entityClass);
		if(isAsc){
			criteria = criteria.addOrder(Order.asc(propertyName));
		}else{
			criteria = criteria.addOrder(Order.desc(propertyName));
		}
		return criteria.list();
	}
	
	/**
	 * 按属性查找对象列表.
	 */
	public <T> List<T> getListByProperty(Class<T> entityClass, String propertyName, Object value) {
		Assert.hasText(propertyName);
		return (List<T>) createCriteria(entityClass, Restrictions.eq(propertyName, value)).list();
	}
	
	/*--------------------------------------------------------------------*/
	
	/**
	 * 创建Criteria对象带属性比较
	 * 
	 * @param <T>
	 * @param entityClass
	 * @param criterions
	 * @return
	 */
	private <T> Criteria createCriteria(Class<T> entityClass, Criterion... criterions) {
		Criteria criteria = getSession().createCriteria(entityClass);
		for (Criterion c : criterions) {
			criteria.add(c);
		}
		return criteria;
	}
	
	/**
	 * 创建单一Criteria对象
	 * 
	 * @param <T>
	 * @param entityClass
	 * @param criterions
	 * @return
	 */
	private <T> Criteria createCriteria(Class<T> entityClass) {
		Criteria criteria = getSession().createCriteria(entityClass);
		return criteria;
	}
}

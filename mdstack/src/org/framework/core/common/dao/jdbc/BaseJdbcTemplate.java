package org.framework.core.common.dao.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.framework.core.common.dao.BaseDao;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.util.Assert;

/**
 * jdbc 模版
 * 
 * @author wangguan
 * @version v1.0
 */
@SuppressWarnings( {"unchecked", "deprecation" ,"rawtypes"} )
public class BaseJdbcTemplate extends BaseDao {

	protected final Logger logger = Logger.getLogger(getClass());

	protected SimpleJdbcTemplate jdbcTemplate;
	protected SimpleJdbcInsert simpleJdbcInsert;
	protected Connection conn;
	
	public Connection getConnection(){
		return conn;
	}

	public BaseJdbcTemplate(DataSource dataSource) {
		try {
			conn = dataSource.getConnection();
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		}
		jdbcTemplate = new SimpleJdbcTemplate(dataSource);
		simpleJdbcInsert = new SimpleJdbcInsert(dataSource);
	}

	public <T> List<T> findBeanListForJdbcMapper(final String statementId,
			Class<T> clazz, Map parameters) {
		return this.findBeanListForJdbc(this.getSqlStatement(statementId, parameters),
				clazz, parameters);
	}

	public <T> T findBeanForJdbcMapper(final String statementId, Class<T> clazz,
			Map parameters) {
		return this.findBeanForJdbc(this.getSqlStatement(statementId, parameters),
				clazz, parameters);
	}

	public List<Map<String, Object>> findMapListForJdbcMapper(final String statementId,
			Map parameters) {
		return this.findMapListForJdbc(this.getSqlStatement(statementId, parameters),
				parameters);
	}

	public Map<String, Object> findMapForJdbcMapper(final String statementId, Map parameters) {
		return this.findMapForJdbc(this.getSqlStatement(statementId, parameters),
				parameters);
	}
	
	

	/**
	 * 根据sql语句，返回对象集合
	 * 
	 * @param sql语句
	 *            (参数用冒号加参数名，例如select * from tb where id=:id)
	 * @param clazz类型
	 * @param parameters参数集合
	 *            (key为参数名，value为参数值)
	 * @return bean对象集合
	 */
	public <T> List<T> findBeanListForJdbc(final String sql, Class<T> clazz,
			Map parameters) {
		try {
			Assert.hasText(sql, "sql语句不正确!");
			Assert.notNull(clazz, "集合中对象类型不能为空!");
			if (parameters != null) {
				return jdbcTemplate.query(sql, resultBeanMapper(clazz),
						parameters);
			} else {
				return jdbcTemplate.query(sql, resultBeanMapper(clazz));
			}
		} catch (Exception e) {
			logger.error(sql.toString(), e);
			return null;
		}
	}

	/**
	 * 根据sql语句，返回对象
	 * 
	 * @param sql语句
	 *            (参数用冒号加参数名，例如select * from tb where id=:id)
	 * @param clazz类型
	 * @param parameters参数集合
	 *            (key为参数名，value为参数值)
	 * @return bean对象
	 */
	public <T> T findBeanForJdbc(final String sql, Class<T> clazz, Map parameters) {
		try {
			Assert.hasText(sql, "sql语句不正确!");
			Assert.notNull(clazz, "集合中对象类型不能为空!");
			if (parameters != null) {
				return (T) jdbcTemplate.queryForObject(sql,
						resultBeanMapper(clazz), parameters);
			} else {
				return (T) jdbcTemplate.queryForObject(sql,
						resultBeanMapper(clazz));
			}
		} catch (Exception e) {
			return null;
		}
	}

	

	/**
	 * 根据sql语句，返回Map对象,对于某些项目来说，没有准备Bean对象，则可以使用Map代替Key为字段名,value为值
	 * 
	 * @param sql语句
	 *            (参数用冒号加参数名，例如select count(*) from tb where id=:id)
	 * @param parameters参数集合
	 *            (key为参数名，value为参数值)
	 * @return bean对象
	 */
	public Map<String, Object> findMapForJdbc(final String sql, Map parameters) {
		try {
			Assert.hasText(sql, "sql语句不正确!");
			if (parameters != null) {
				return jdbcTemplate.queryForMap(sql, parameters);
			} else {
				return jdbcTemplate.queryForMap(sql);
			}
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 根据sql语句，返回Map对象集合
	 * 
	 * @see findForMap
	 * @param sql语句
	 *            (参数用冒号加参数名，例如select count(*) from tb where id=:id)
	 * @param parameters参数集合
	 *            (key为参数名，value为参数值)
	 * @return bean对象
	 */
	public List<Map<String, Object>> findMapListForJdbc(final String sql, Map parameters) {
		try {
			Assert.hasText(sql, "sql语句不正确!");
			if (parameters != null) {
				return jdbcTemplate.queryForList(sql, parameters);
			} else {
				return jdbcTemplate.queryForList(sql);
			}
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * 根据sql语句，返回数值型返回结果
	 * 
	 * @param sql语句
	 *            (参数用冒号加参数名，例如select count(*) from tb where id=:id)
	 * @param parameters参数集合
	 *            (key为参数名，value为参数值)
	 * @return bean对象
	 */
	public long findLong(final String sql, Map parameters) {
		try {
			Assert.hasText(sql, "sql语句不正确!");
			if (parameters != null) {
				return jdbcTemplate.queryForLong(sql, parameters);
			} else {
				return jdbcTemplate.queryForLong(sql);
			}
		} catch (Exception e) {
			logger.error(sql.toString(), e);
			return 0;
		}
	}

	/**
	 * 根据sql语句，返回数值型返回结果
	 * 
	 * @param sql语句
	 *            (参数用冒号加参数名，例如select count(*) from tb where id=:id)
	 * @param parameters参数集合
	 *            (key为参数名，value为参数值)
	 * @return bean对象
	 */
	public int findInt(final String sql, Map parameters) {
		try {
			Assert.hasText(sql, "sql语句不正确!");
			if (parameters != null) {
				return jdbcTemplate.queryForInt(sql, parameters);
			} else {
				return jdbcTemplate.queryForInt(sql);
			}
		} catch (Exception e) {
			logger.error(sql.toString(), e);
			return 0;
		}
	}

	/**
	 * 执行insert，update，delete等操作<br>
	 * 例如insert into users (name,login_name,password)
	 * values(:name,:loginName,:password)<br>
	 * 参数用冒号,参数为bean的属性名
	 * 
	 * @param sql
	 * @param bean
	 */
	public int executeDMLForBean(final String sql, Object bean) {
		Assert.hasText(sql, "sql语句不正确!");
		if (bean != null) {
			return jdbcTemplate.update(sql, paramBeanMapper(bean));
		} else {
			return jdbcTemplate.update(sql);
		}
	}

	/**
	 * 执行insert，update，delete等操作<br>
	 * 例如insert into users (name,login_name,password)
	 * values(:name,:login_name,:password)<br>
	 * 参数用冒号,参数为Map的key名
	 * 
	 * @param sql
	 * @param parameters
	 */
	public int executeDMLForMap(final String sql, Map parameters) {
		Assert.hasText(sql, "sql语句不正确!");
		if (parameters != null) {
			return jdbcTemplate.update(sql, parameters);
		} else {
			return jdbcTemplate.update(sql);
		}
	}
	
	public int[] batchUpdate(final String sql, Map<String,?>[] batchValues) {
		int[] updateCounts = jdbcTemplate.batchUpdate(sql, batchValues);
		return updateCounts;
	}

	/*
	 * 批量处理操作 例如：update t_actor set first_name = :firstName, last_name =
	 * :lastName where id = :id 参数用冒号
	 */
	public int[] batchUpdate(final String sql, List<Object[]> batch) {
		int[] updateCounts = jdbcTemplate.batchUpdate(sql, batch);
		return updateCounts;
	}

	protected ParameterizedBeanPropertyRowMapper resultBeanMapper(Class clazz) {
		return ParameterizedBeanPropertyRowMapper.newInstance(clazz);
	}

	protected BeanPropertySqlParameterSource paramBeanMapper(Object object) {
		return new BeanPropertySqlParameterSource(object);
	}
}

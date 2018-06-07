package org.framework.core.common.dao.jdbc;

import io.searchbox.core.search.sort.Sort;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.poi.hssf.record.formula.functions.T;
import org.elasticsearch.common.util.ObjectArray;
import org.framework.core.common.dto.datatable.SortInfo;
import org.framework.core.common.model.common.Pagination;
import org.framework.core.util.MyBeanUtils;
import org.framework.core.util.ResourceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

@Repository("jdbcDao")
@SuppressWarnings( {"unchecked" ,"hiding","rawtypes"} )
public class JdbcDao extends BaseJdbcTemplate{
	
	/**
	 * 数据库类型
	 */
	public static final String DATABSE_TYPE_MYSQL ="mysql";
	public static final String DATABSE_TYPE_POSTGRE ="postgresql";
	public static final String DATABSE_TYPE_ORACLE ="oracle";
	/**
	 * 分页SQL
	 */
	public static final String MYSQL_SQL = "select * from ( {0}) sel_tab00 limit {1},{2}";         //mysql
	public static final String POSTGRE_SQL = "select * from ( {0}) sel_tab00 {3} limit {2} offset {1}";//postgresql
	public static final String ORACLE_SQL = "select * from (select row_.*,rownum rownum_ from ({0}) row_ where rownum <= {1}) where rownum_>{2}"; //oracle
	
	public static final String SQL_COUNT_PREFIX = "select count(*) ";
	
	@Autowired
	public JdbcDao(DataSource dataSource) {
		super(dataSource);
	}

	public <T> Pagination<T> findPaginationForBeanList(final String statementId,
			Class<T> clazz, Map parameters, int start, int length) {

		String statement = this.getSqlStatement(statementId, parameters);
		String sql_count = createPageSqlCount(statement);
		String sql = createPageSql(statement, start, length);
		int count = this.findInt(sql_count, parameters);

		List<T> resultList = new ArrayList<T>();
		resultList = this.findBeanListForJdbc(sql, clazz, parameters);

		Pagination<T> p = new Pagination<T>();
		p.setStart(start);
		p.setLength(length);
		p.setCount(count);
		p.setResultList(resultList);

		return p;
	}
	
	public  Pagination<Map<String,Object>> findPaginationForMapList(final String statementId,
			Map parameters, int start, int length, String [][] sort) {

		String statement = this.getSqlStatement(statementId, parameters);
		String sql_count = createPageSqlCount(statement);
		String sql = createPageSql(statement, start, length);
		int count = this.findInt(sql_count, parameters);

		List<Map<String,Object>> resultList = new ArrayList<Map<String,Object>>();
		resultList = this.findMapListForJdbc(sql, parameters);

		Pagination<Map<String,Object>> p = new Pagination<Map<String,Object>>();
		p.setStart(start);
		p.setLength(length);
		p.setCount(count);
		p.setResultList(resultList);

		return p;
	}
	
	public  Pagination<Map<String,Object>> findPaginationForMapList1(final String statementId,
			Map parameters, int start, int length, SortInfo[] sortinfo) {

		String statement = this.getSqlStatement(statementId, parameters);
		String sql_count = createPageSqlCount(statement);
		String sql = createPageSql1(statement, start, length, sortinfo);
		int count = this.findInt(sql_count, parameters);

		List<Map<String,Object>> resultList = new ArrayList<Map<String,Object>>();
		resultList = this.findMapListForJdbc(sql, parameters);

		Pagination<Map<String,Object>> p = new Pagination<Map<String,Object>>();
		p.setStart(start);
		p.setLength(length);
		p.setCount(count);
		p.setResultList(resultList);

		return p;
	}
	public  Pagination<Map<String,Object>> findPaginationForMapList2(final String statementId,
			Map parameters, int start, int length, SortInfo[] sortinfo) {

		String statement = this.getSqlStatement(statementId, parameters);
		String sql_count = "select count(*) from ("+statementId+") tab";
		String sql = createPageSql1(statement, start, length, sortinfo);
		int count = this.findInt(sql_count, parameters);

		List<Map<String,Object>> resultList = new ArrayList<Map<String,Object>>();
		resultList = this.findMapListForJdbc(sql, parameters);

		Pagination<Map<String,Object>> p = new Pagination<Map<String,Object>>();
		p.setStart(start);
		p.setLength(length);
		p.setCount(count);
		p.setResultList(resultList);

		return p;
	}
	
	public  Pagination<Map<String,Object>> findPaginationForMapList1(final String statementId,
			Map parameters, int start, int length, ObjectArray array[]) {

		String statement = this.getSqlStatement(statementId, parameters);
		String sql_count = createPageSqlCount(statement);
		String sql = createPageSql(statement, start, length);
		int count = this.findInt(sql_count, parameters);

		List<Map<String,Object>> resultList = new ArrayList<Map<String,Object>>();
		resultList = this.findMapListForJdbc(sql, parameters);

		Pagination<Map<String,Object>> p = new Pagination<Map<String,Object>>();
		p.setStart(start);
		p.setLength(length);
		p.setCount(count);
		p.setResultList(resultList);

		return p;
	}
	
	
	

	/**
	 * 按照数据库类型，封装SQL
	 */
	public static String createPageSql(String sql, int start, int length){
		int beginNum = start;
		String[] sqlParam = new String[4];
		sqlParam[0] = sql;
		sqlParam[1] = beginNum+"";
		sqlParam[2] = length+"";
		sqlParam[3] = "";
		if(ResourceUtil.getJdbcUrl().indexOf(DATABSE_TYPE_MYSQL)!=-1){
			sql = MessageFormat.format(MYSQL_SQL, sqlParam);
		}else if(ResourceUtil.getJdbcUrl().indexOf(DATABSE_TYPE_POSTGRE)!=-1){
			sql = MessageFormat.format(POSTGRE_SQL, sqlParam);
		}else if(ResourceUtil.getJdbcUrl().indexOf(DATABSE_TYPE_ORACLE)!=-1){
			int beginIndex = start;
			int endIndex = beginIndex+length;
			sqlParam[2] = beginIndex+"";
			sqlParam[1] = endIndex+"";
			sql = MessageFormat.format(ORACLE_SQL, sqlParam);
		}
		return sql;
	}
	
	public static String createPageSql1(String sql, int start, int length, SortInfo[] sortinfo){
		int beginNum = start;
		String[] sqlParam = new String[4];
		sqlParam[0] = sql;
		sqlParam[1] = beginNum+"";
		sqlParam[2] = length+"";
		sqlParam[3] = " order by 1 asc ";//默认  排序
		if (sortinfo.length>1 || sortinfo[0].getColumnId()!=0) {
			String or = "order by ";
			String by = "";
			sqlParam[3] = "";
			for (int i = 0; i < sortinfo.length; i++) {
				String s = "";
				if (sortinfo.length>1) {
					s=",";
				}
				if (i==(sortinfo.length-1)) {
					s="";
				}
				by += sortinfo[i].getColumnId() + " " + sortinfo[i].getSortOrder() + s;
			}
			sqlParam[3] = or + by;
		}
		if(ResourceUtil.getJdbcUrl().indexOf(DATABSE_TYPE_MYSQL)!=-1){
			sql = MessageFormat.format(MYSQL_SQL, sqlParam);
		}else if(ResourceUtil.getJdbcUrl().indexOf(DATABSE_TYPE_POSTGRE)!=-1){
			sql = MessageFormat.format(POSTGRE_SQL, sqlParam);
		}else if(ResourceUtil.getJdbcUrl().indexOf(DATABSE_TYPE_ORACLE)!=-1){
			int beginIndex = start;
			int endIndex = beginIndex+length;
			sqlParam[2] = beginIndex+"";
			sqlParam[1] = endIndex+"";
			sql = MessageFormat.format(ORACLE_SQL, sqlParam);
		}
		return sql;
	}
	
	public static String createPageSqlCount(String sql){
		String sql_sub_from;
		String sql_lower = sql.toLowerCase();
		int from_index = sql_lower.indexOf("from");
		int order_by_index = sql_lower.indexOf("order by");
		if(order_by_index>=0){
			sql_sub_from = sql.substring(from_index,order_by_index);
		}else{
			sql_sub_from = sql.substring(from_index);
		}
		return SQL_COUNT_PREFIX + sql_sub_from;
		
	}
	
	public static void main(String[] args){
		
	}
}

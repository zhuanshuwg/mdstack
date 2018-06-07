package org.framework.core.common.model.common;

import java.io.Serializable;
import java.util.List;

public class Pagination<T> implements Serializable {

	private static final long serialVersionUID = -344553680827645057L;
	
	private String draw; // 用于返回给客户端的标识
	
	private Integer start;//分页起点
	private Integer length;//每页显示行数
	private Integer count;//总数
	private List<T> resultList; //分页结果列表
	
	public Pagination() {}
	
	/**
	 * 使用DataTables 分页
	 * @param count 总数
	 * @param resultList 结果集
	 */
	public void setDataTables(Integer count, List<T> resultList) {
		this.count = count;
		this.resultList = resultList;
	}
	
	/**
	 * 使用自定义分页，非DataTables
	 * @param draw 标识，可为空
	 * @param start 分页起点
	 * @param length 每页数量
	 * @param count 总数
	 * @param resultList 结果集
	 */
	public void setNotDataTables(String draw, Integer start, Integer length, Integer count, List<T> resultList) {
		this.draw = draw;
		this.start = start;
		this.length = length;
		this.count = count;
		this.resultList = resultList;
	}


	public Integer getStart() {
		return start;
	}
	public void setStart(Integer start) {
		this.start = start;
	}
	public Integer getLength() {
		return length;
	}
	public void setLength(Integer length) {
		this.length = length;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count2) {
		this.count = count2;
	}
	public List<T> getResultList() {
		return resultList;
	}
	public void setResultList(List<T> resultList) {
		this.resultList = resultList;
	}
	public String getDraw() {
		return draw;
	}
	public void setDraw(String draw) {
		this.draw = draw;
	}
	
}

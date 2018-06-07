package org.framework.core.common.dao.elasticsearch;

import io.searchbox.client.JestResult;

import java.io.Serializable;
/**
 * elasticsearch 分页封装
 * @author HouqiZhang
 *
 */
public class EsPagination implements Serializable {

	private static final long serialVersionUID = -919794851085454183L;
	
	private Integer from;
	private Integer size;
	private Long total;
	private JestResult result;
	
	private String sortField;
	private String order;
	
	public Integer getFrom() {
		return from;
	}
	public void setFrom(Integer from) {
		this.from = from;
	}
	public Integer getSize() {
		return size;
	}
	public void setSize(Integer size) {
		this.size = size;
	}
	public Long getTotal() {
		return total;
	}
	public void setTotal(Long total) {
		this.total = total;
	}
	public JestResult getResult() {
		return result;
	}
	public void setResult(JestResult result) {
		this.result = result;
	}
	public String getSortField() {
		return sortField;
	}
	public void setSortField(String sortField) {
		this.sortField = sortField;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
}

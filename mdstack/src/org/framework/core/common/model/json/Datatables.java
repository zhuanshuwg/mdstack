package org.framework.core.common.model.json;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.framework.core.common.model.common.Pagination;

/**
 * datatable的分页排序，用于解析分页排序参数以及返回分页内容
 * @author wangguan
 */
@SuppressWarnings("rawtypes")
public class Datatables {

	private String draw; // 标识，必须
	private int start;  // 起始位置
	private int length;  // 一页显示的条数
	private Integer recordsTotal;  // 总条数，必须
	private Integer recordsFiltered;  // 总条数，必须
	private List data;  // 数据，必须
	
	private String orderColumn; // 排序的列
	private String orderDir; // 排序的方式
	
	
	private String [][] sort; // 排序的列
	
	
	public Datatables(HttpServletRequest request) {
		this.draw = request.getParameter("draw"); // 相当于表示，返回值中必须提供
		this.start =  Integer.parseInt(request.getParameter("start")); // 从第几行开始
		this.length =  Integer.parseInt(5+""); // 一页的条数
		this.orderColumn =  request.getParameter("columns["+request.getParameter("order[0][column]")+"][data]"); // 需要排序的列
		this.orderDir =  request.getParameter("order[0][dir]"); // 列排序的方式
		
		if(orderColumn != null){
			String [][] sort = new String [1][2];
			sort[0][0] = orderColumn;
			if(orderDir != null){
				sort[0][1] = orderDir;
			}
			this.sort = sort;
		}
	}
	
	/**
	 * 添加数据
	 */
	public void setPagination(Pagination pagination) {
		this.recordsTotal = pagination.getCount(); // 总条数，必须
		this.recordsFiltered = pagination.getCount(); // 总条数，必须
		this.data = pagination.getResultList(); // 数据，必须
	}
	
	// ------ GET / SET 方法 ------
	
	/**
	 * 客户端 与 服务器端 的标识
	 */
	public String getDraw() {
		return draw;
	}

	/**
	 * 起始条数
	 */
	public int getStart() {
		return start;
	}
	/**
	 * 起始条数
	 */
	public void setStart(int start) {
		this.start = start;
	}

	/**
	 * 一页显示的条数
	 */
	public int getLength() {
		return length;
	}
	/**
	 * 一页显示的条数
	 */
	public void setLength(int length) {
		this.length = length;
	}

	/**
	 * 总条数
	 */
	public Integer getRecordsTotal() {
		return recordsTotal;
	}
	/**
	 * 总条数
	 */
	public void setRecordsTotal(Integer recordsTotal) {
		this.recordsTotal = recordsTotal;
	}

	/**
	 * 过滤后的总条数
	 */
	public Integer getRecordsFiltered() {
		return recordsFiltered;
	}
	/**
	 * 过滤后的总条数
	 */
	public void setRecordsFiltered(Integer recordsFiltered) {
		this.recordsFiltered = recordsFiltered;
	}

	/**
	 * 结果集
	 */
	public List getData() {
		return data;
	}
	/**
	 * 结果集
	 */
	public void setData(List data) {
		this.data = data;
	}

	public String getOrderColumn() {
		return orderColumn;
	}
	public void setOrderColumn(String orderColumn) {
		this.orderColumn = orderColumn;
	}

	public String getOrderDir() {
		return orderDir;
	}
	public void setOrderDir(String orderDir) {
		this.orderDir = orderDir;
	}
	
	/**
	 * 排序
	 */
	public String[][] getSort() {
		return sort;
	}

	@Override
	public String toString() {
		return "DatatablePaging [draw=" + draw + ", start=" + start
				+ ", length=" + length + ", recordsTotal=" + recordsTotal
				+ ", recordsFiltered=" + recordsFiltered + ", data=" + data
				+ ", orderColumn=" + orderColumn + ", orderDir=" + orderDir
				+ "]";
	}

}

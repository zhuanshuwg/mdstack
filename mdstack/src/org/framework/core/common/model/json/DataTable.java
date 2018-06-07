package org.framework.core.common.model.json;

import java.util.List;
import java.util.Map;

public class DataTable {
	
	private Object extend;
	
	private Integer sEcho;
	/*
	 * Total records, before filtering (i.e. the total number of records in the
	 * database)
	 */
	private Integer iTotalRecords;
	/*
	 * Total records, after filtering (i.e. the total number of records after
	 * filtering has been applied - not just the number of records being
	 * returned in this result set)
	 */
	private Integer iTotalDisplayRecords;
	/*
	 * The data in a 2D array. Note that you can change the name of this
	 * parameter with sAjaxDataProp
	 */
	private List<?> aaData;
	
	public DataTable(){}
	
	public DataTable(Integer iTotalRecords, Integer iTotalDisplayRecords,
			Integer sEcho, List<?> aaData) {
		this.iTotalRecords = iTotalRecords;
		this.iTotalDisplayRecords = iTotalDisplayRecords;
		this.sEcho = sEcho;
		this.aaData = aaData;
	}
	
	public Object getExtend() {
		return extend;
	}

	public void setExtend(Object extend) {
		this.extend = extend;
	}

	public Integer getsEcho() {
		return sEcho;
	}
	public void setsEcho(Integer sEcho) {
		this.sEcho = sEcho;
	}
	public Integer getiTotalRecords() {
		return iTotalRecords;
	}
	public void setiTotalRecords(Integer iTotalRecords) {
		this.iTotalRecords = iTotalRecords;
	}
	public Integer getiTotalDisplayRecords() {
		return iTotalDisplayRecords;
	}
	public void setiTotalDisplayRecords(Integer iTotalDisplayRecords) {
		this.iTotalDisplayRecords = iTotalDisplayRecords;
	}
	public List<?> getAaData() {
		return aaData;
	}
	public void setAaData(List<?> aaData) {
		this.aaData = aaData;
	}
}

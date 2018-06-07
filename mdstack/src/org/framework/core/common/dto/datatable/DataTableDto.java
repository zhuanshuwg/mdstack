package org.framework.core.common.dto.datatable;

import java.io.Serializable;
import java.text.MessageFormat;

import javax.servlet.http.HttpServletRequest;
/**
 * jquery datatable 请求传输对象封装
 * @author wangguan
 *
 */
public class DataTableDto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private HttpServletRequest request;
	
	/* Display start point in the current data set */
	private Integer iDisplayStart;
	
	/* Number of records that the table can display in the current draw. 
	 * It is expected that the number of records returned will be equal to this number, 
	 * unless the server has fewer records to return */
	private Integer iDisplayLength;
	
	/* Number of columns being displayed */
	private Integer iColumns;
	private ColumnInfo[] columns;
	
	/* Global search field */
	private String sSearch;
	
	/* True if the global filter should be treated as 
	 * a regular expression for advanced filtering,
	 *  false if not */
	private boolean bRegex;
	
	/* bool	bSearchable_(int)	
	 * Indicator for if a column is flagged as searchable or not on the client-side */
//	private boolean bSearchable_;
	
	/* string	sSearch_(int)	Individual column filter */
//	private String sSearch_;
	
	/*bool	bRegex_(int)	
	 * True if the individual column filter should be treated as a regular 
	 * expression for advanced filtering, false if not*/
//	private boolean bRegex_;
	/* bool	bSortable_(int)	
	 * Indicator for if a column is flagged as sortable or not on the client-side */
//	private boolean bSortable_;
	
	/* int	iSortingCols	Number of columns to sort on */
	private Integer iSortingCols;
	
	/* int	iSortCol_(int)	
	 * Column being sorted on (you will need to decode this number for your database) */
//	private Integer iSortCol_;
	private SortInfo[] sortCols;
	
	/* string	sSortDir_(int)	Direction to be sorted - "desc" or "asc". */
//	private String sSortDir_;
	
	/* string	mDataProp_(int)	
	 * The value specified by mDataProp for each column. 
	 * This can be useful for ensuring that the processing of
	 *  data is independent from the order of the columns. */
//	private String mData_;
	
	/* string	sEcho	Information for DataTables to use for rendering */
	private Integer sEcho;
	
	public void setHttpServletRequest(HttpServletRequest httpRequest){
		this.request = httpRequest;
	}
	
	public DataTableDto(HttpServletRequest request){
		this.request = request;
		
		this.sEcho = this.ParseIntParameter("sEcho");
		this.iDisplayStart = this.ParseIntParameter("iDisplayStart");
		this.iDisplayLength = this.ParseIntParameter("iDisplayLength");
		this.iSortingCols = this.ParseIntParameter("iSortingCols");
		
		this.sSearch = this.ParseStringParameter("sSearch");
		this.bRegex = this.ParseStringParameter("bRegex") == "true";
		
		int sortCount = iSortingCols;
		this.sortCols = new SortInfo[sortCount];
		MessageFormat formatter = new MessageFormat("");
		for(int i=0; i<sortCount; i++){
			SortInfo sortInfo = new SortInfo();
			sortInfo.setColumnId(this.ParseIntParameter(formatter.format("iSortCol_{0}", i)));
			if (this.ParseStringParameter(formatter.format("sSortDir_{0}", i)).equals("desc")) {
				sortInfo.setSortOrder(SortDirection.asc);
			} else {
				sortInfo.setSortOrder(SortDirection.desc);
			}
			this.sortCols[i] = sortInfo;
		}
		
		int columnCount = this.ParseIntParameter("iColumns");
		this.columns = new ColumnInfo[columnCount];
		
		for(int i=0; i<columnCount; i++){
			ColumnInfo col = new ColumnInfo();
			col.setName(this.ParseStringParameter(formatter.format("mDataProp_{0}", i)));
			col.setSortable(this.ParseBooleanParameter(formatter.format("bSortable_{0}", i)));
			col.setSearchable(this.ParseBooleanParameter(formatter.format("bSearchable_{0}", i)));
			col.setSearch(this.ParseStringParameter(formatter.format("sSearch_{0}", i)));
			col.setRegex(this.ParseStringParameter(formatter.format("bRegex_{0}", i)) == "true");
			columns[i] = col;
		}
		
	}
	
	private Integer ParseIntParameter(String name) // 解析为整数
	{
		Integer result = 0;
		String parameter = this.request.getParameter(name);
		if (parameter != null) {
			result = Integer.parseInt(parameter);
		}
		return result;
	}

	private String ParseStringParameter(String name) // 解析为字符串
	{
		return this.request.getParameter(name);
	}

	private Boolean ParseBooleanParameter(String name) // 解析为布尔类型
	{
		Boolean result = false;
		String parameter = this.request.getParameter(name);
		if (parameter != null) {
			result = Boolean.parseBoolean(parameter);
		}
		return result;
	}

	public Integer getiDisplayStart() {
		return iDisplayStart;
	}

	public void setiDisplayStart(Integer iDisplayStart) {
		this.iDisplayStart = iDisplayStart;
	}

	public Integer getiDisplayLength() {
		return iDisplayLength;
	}

	public void setiDisplayLength(Integer iDisplayLength) {
		this.iDisplayLength = iDisplayLength;
	}

	public Integer getiColumns() {
		return iColumns;
	}

	public void setiColumns(Integer iColumns) {
		this.iColumns = iColumns;
	}

	public ColumnInfo[] getColumns() {
		return columns;
	}

	public void setColumns(ColumnInfo[] columns) {
		this.columns = columns;
	}

	public String getsSearch() {
		return sSearch;
	}

	public void setsSearch(String sSearch) {
		this.sSearch = sSearch;
	}

	public boolean isbRegex() {
		return bRegex;
	}

	public void setbRegex(boolean bRegex) {
		this.bRegex = bRegex;
	}

	public Integer getiSortingCols() {
		return iSortingCols;
	}

	public void setiSortingCols(Integer iSortingCols) {
		this.iSortingCols = iSortingCols;
	}

	public SortInfo[] getSortCols() {
		return sortCols;
	}

	public void setSortCols(SortInfo[] sortCols) {
		this.sortCols = sortCols;
	}

	public Integer getsEcho() {
		return sEcho;
	}

	public void setsEcho(Integer sEcho) {
		this.sEcho = sEcho;
	}
}

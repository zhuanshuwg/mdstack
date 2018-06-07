package org.framework.core.common.dao.elasticsearch;

import static org.elasticsearch.search.builder.SearchSourceBuilder.highlight;
import static org.elasticsearch.search.facet.FacetBuilders.statisticalFacet;
import static org.elasticsearch.search.facet.FacetBuilders.termsFacet;
import io.searchbox.action.Action;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.client.http.JestHttpClient;
import io.searchbox.core.Count;
import io.searchbox.core.Search;
import io.searchbox.core.Search.Builder;
import io.searchbox.core.SearchScroll;
import io.searchbox.indices.IndicesExists;
import io.searchbox.indices.Stats;
import io.searchbox.indices.aliases.GetAliases;
import io.searchbox.indices.mapping.GetMapping;
import io.searchbox.params.Parameters;
import io.searchbox.params.SearchType;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.index.query.FilteredQueryBuilder;
import org.elasticsearch.index.query.IdsQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.index.query.QueryStringQueryBuilder.Operator;
import org.elasticsearch.index.query.RangeFilterBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.facet.terms.TermsFacet.ComparatorType;
import org.elasticsearch.search.sort.SortOrder;
import org.framework.core.util.ContextHolderUtils;
import org.framework.core.util.DataUtils;
import org.framework.core.util.ResourceUtil;
import org.framework.core.util.StringUtil;
import org.springframework.stereotype.Repository;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;


@Repository("elasticsearchDao")
public class ElasticsearchDao {

	private Logger log = Logger.getLogger(ElasticsearchDao.class);
	
	/**
	 * 趋势分析
	 * @return
	 */
	public List<JsonElement> trendAnalysis(String searchCond, Long sTime, Long eTime,String field,SortOrder order){
		
		String size_str = ResourceUtil.get("sysconfig","elasticsearch.integral.analysis.size");
		int istart = 0;
		int isize = Integer.valueOf(size_str);
		
		List<JsonElement> valueList = new ArrayList<JsonElement>();
		
		List<Map<String,Long>> sl = DataUtils.ESRange(sTime, eTime);
		
		if(order == SortOrder.ASC){
			 Collections.reverse(sl);
		}
		
		for (Map<String, Long> es : sl) {
			long stime = es.get("stime"), etime = es.get("etime");
			String index  = this.getIndex(es.get("index_long"));
			if(!indexExists(index)) continue;//索引不存在跳过，继续循环
			
			JestResult result = this.getResult(index,order,searchCond, stime, etime, field,istart,isize);
			
			if (result != null && result.getJsonObject() != null) {
				JsonElement hits = result.getJsonObject().get("hits");
				if (hits != null && hits.getAsJsonObject() != null) {
					JsonElement total = hits.getAsJsonObject().get("total");
					long _total = Integer.valueOf(total.toString());
					JsonElement _hits = hits.getAsJsonObject().get("hits");
					if (_hits != null && _hits.getAsJsonArray() != null) {
						for (JsonElement o : _hits.getAsJsonArray()){
							if(o!=null && o.getAsJsonObject()!=null){
								JsonElement fields = o.getAsJsonObject().get("_source");
								if(fields != null && fields.getAsJsonObject().get(field) != null)  {
									valueList.add(fields.getAsJsonObject().get(field));
								}
							}
						}
						if (_hits.getAsJsonArray().size() == isize) {
							break;
						} else {
							isize -= _hits.getAsJsonArray().size();
							if (istart - _total < 0)istart = 0;else istart -= _total;
						}
					}
				}
			}
		}
		return valueList;
	}
	
	
	/**
	 * 积分分析
	 * @return
	 */
	public Map<String,Object> integralAnalysis(String searchCond, Long sTime, Long eTime,String field){
		
		String size_str = ResourceUtil.get("sysconfig","elasticsearch.integral.analysis.size");
		int istart = 0;
		int isize = Integer.valueOf(size_str);//每次取100条数据
		
		int rtnTotal = 0;
		
		List<JsonElement> valueList = new ArrayList<JsonElement>();
		for (Map<String, Long> es : DataUtils.ESRange(sTime, eTime)) {
			long stime = es.get("stime"), etime = es.get("etime");
			String index  = this.getIndex(es.get("index_long"));
			if(!indexExists(index)) continue;//索引不存在跳过，继续循环
			
			JestResult result = this.getResult(index,SortOrder.DESC,searchCond, stime, etime, field,istart,isize);
			if (result != null && result.getJsonObject() != null) {
				JsonElement hits = result.getJsonObject().get("hits");
				if (hits != null && hits.getAsJsonObject() != null) {
					JsonElement total = hits.getAsJsonObject().get("total");
					long _total = Integer.valueOf(total.toString());
					JsonElement _hits = hits.getAsJsonObject().get("hits");
					if (_hits != null && _hits.getAsJsonArray() != null) {
						for (JsonElement o : _hits.getAsJsonArray()){
							if(o!=null && o.getAsJsonObject()!=null){
								JsonElement fields = o.getAsJsonObject().get("_source");
								if(fields != null && fields.getAsJsonObject().get(field) != null)  {
									valueList.add(fields.getAsJsonObject().get(field));
								}
							}
						} rtnTotal += _hits.getAsJsonArray().size();
						if (_hits.getAsJsonArray().size() == isize) {
							break;
						} else {
							isize -= _hits.getAsJsonArray().size();
							if (istart - _total < 0)istart = 0;else istart -= _total;
						}
					}
				}
			}
		}
		
		Map<String,Object> rtnMap = new HashMap<String,Object>();
		rtnMap.put("jeList", valueList);
		rtnMap.put("size", rtnTotal);
		return rtnMap;
	}
	
	private JestResult getResult(String index,SortOrder order,String searchCond, Long sTime, 
			Long eTime,String field,int start,int size){
		String defalut_field = ResourceUtil.get("sysconfig","query_string.default_field");
		
		//搜索条件为空，默认为*
		if(searchCond == null || StringUtil.getStringLen(searchCond)==0)searchCond = "*";
		searchCond = this.appendCond(searchCond);
		JestResult result = null;
		// StringQuery查询
		QueryStringQueryBuilder qsqb = QueryBuilders.queryString(searchCond);
		qsqb.defaultOperator(Operator.AND).defaultField(defalut_field);// .analyzer("DEFAULT");

		// RangeFilter过滤
		RangeFilterBuilder rfb = FilterBuilders.rangeFilter("@timestamp")
				.from(sTime).to(eTime).includeUpper(false);

		// FilteredQuery查询
		FilteredQueryBuilder query = QueryBuilders.filteredQuery(qsqb, rfb);
		
		SearchSourceBuilder ssb = new SearchSourceBuilder().query(query);
		ssb.from(start).size(size).sort("@timestamp", order).fetchSource(field, null);
		Builder searchBuilder = new Search.Builder(ssb.toString());
		result = this.getJestResult(searchBuilder.addIndex(index).build());
		return result;
	}
		
	public JestResult statAnalysis(String searchCond, Long sTime, Long eTime,String field){
		searchCond = this.appendCond(searchCond);
		String defalut_field = ResourceUtil.get("sysconfig","query_string.default_field");
		
		//搜索条件为空，默认为*
		if(searchCond == null || StringUtil.getStringLen(searchCond)==0)searchCond = "*";
		
		String index = "";
		long stime = 0L,etime = 0L;
		
		for (Map<String, Long> es : DataUtils.ESRange(sTime, eTime)) {
			stime = es.get("stime");
			etime = es.get("etime");
			index  = this.getIndex(es.get("index_long"));
			if(indexExists(index)) break;//索引存在,跳出循环
		}
		
		JestResult result = null;
		// StringQuery查询
		QueryStringQueryBuilder qsqb = QueryBuilders.queryString(searchCond);
		qsqb.defaultOperator(Operator.AND).defaultField(defalut_field);// .analyzer("DEFAULT");

		// RangeFilter过滤
		RangeFilterBuilder rfb = FilterBuilders.rangeFilter("@timestamp")
				.from(stime).to(etime).includeUpper(false);

		// FilteredQuery查询
		FilteredQueryBuilder query = QueryBuilders.filteredQuery(qsqb, rfb);
		
		SearchSourceBuilder ssb = new SearchSourceBuilder().query(query);
		ssb.size(0).facet(statisticalFacet("statfacet").field(field));
		Builder searchBuilder = new Search.Builder(ssb.toString());
		result = this.getJestResult(searchBuilder.addIndex(index).build());
		return result;
	}
	/**
	 * 术语分析
	 * @param searchCond
	 * @param sTime
	 * @param eTime
	 * @param field
	 * @return
	 */
	public Map<String,Object> termsAnalysis(String searchCond, Long sTime, Long eTime,String field){
		searchCond = this.appendCond(searchCond);
		Map<String,Object> rtnMap = new HashMap<String,Object>();
		
		String defalut_field = ResourceUtil.get("sysconfig","query_string.default_field");
		
		int size = 25;
		String size_str = ResourceUtil.get("sysconfig","elasticsearch.facet.termsfacet.size");
		size = Integer.valueOf(size_str);
		
		//搜索条件为空，默认为*
		if(searchCond == null || StringUtil.getStringLen(searchCond)==0)searchCond = "*";
		
		String index = "";
		long stime = 0L,etime = 0L;
		for (Map<String, Long> es : DataUtils.ESRange(sTime, eTime)) {
			stime = es.get("stime");
			etime = es.get("etime");
			index  = this.getIndex(es.get("index_long"));
			if(indexExists(index)) break;//索引存在,跳出循环
		}
		
		JestResult result = null;
		// StringQuery查询
		QueryStringQueryBuilder qsqb = QueryBuilders.queryString(searchCond);
		qsqb.defaultOperator(Operator.AND).defaultField(defalut_field);// .analyzer("DEFAULT");

		// RangeFilter过滤
		RangeFilterBuilder rfb = FilterBuilders.rangeFilter("@timestamp")
				.from(stime).to(etime).includeUpper(false);
		
		rtnMap.put("date", DataUtils.date2Str(new Date(stime), new SimpleDateFormat("yyyy-MM-dd")));

		// FilteredQuery查询
		FilteredQueryBuilder query = QueryBuilders.filteredQuery(qsqb, rfb);
		
		SearchSourceBuilder ssb = new SearchSourceBuilder().query(query);
		ssb.size(0).facet(termsFacet("termsfacet").field(field).size(size).order(ComparatorType.COUNT));
		Builder searchBuilder = new Search.Builder(ssb.toString());
		result = this.getJestResult(searchBuilder.addIndex(index).build());
		
		rtnMap.put("jestResult", result);
		
		return rtnMap;
	}
	
	public JestResult termsAnalysisForCurrentPage(String searchCond,
			Long sTime, Long eTime, String field, int start, int size) {
		searchCond = this.appendCond(searchCond);
		String defalut_field = ResourceUtil.get("sysconfig","query_string.default_field");
		//搜索条件为空，默认为*
		if(searchCond == null || StringUtil.getStringLen(searchCond)==0)searchCond = "*";
		String index = "";
		long stime = 0L,etime = 0L;
		for (Map<String, Long> es : DataUtils.ESRange(sTime, eTime)) {
			stime = es.get("stime");
			etime = es.get("etime");
			index  = this.getIndex(es.get("index_long"));
			if(indexExists(index)) break;//索引存在,跳出循环
		}
		JestResult result = null;
		// StringQuery查询
		QueryStringQueryBuilder qsqb = QueryBuilders.queryString(searchCond);
		qsqb.defaultOperator(Operator.AND).defaultField(defalut_field);// .analyzer("DEFAULT");
		// RangeFilter过滤
		RangeFilterBuilder rfb = FilterBuilders.rangeFilter("@timestamp")
				.from(stime).to(etime).includeUpper(false);
		// FilteredQuery查询
		FilteredQueryBuilder query = QueryBuilders.filteredQuery(qsqb, rfb);
		
		SearchSourceBuilder ssb = new SearchSourceBuilder().query(query);
		ssb.size(0).facet(termsFacet("termsfacet").field(field).size(size).order(ComparatorType.COUNT));
		Builder searchBuilder = new Search.Builder(ssb.toString());
		result = this.getJestResult(searchBuilder.addIndex(index).build());
		return result;
	}
		
	/*public List<Map<String,JsonObject>> search(String searchCond, Long sTime, Long eTime,
			List<String> fields, int start, int size) {*/
		public List<Map<String,JsonObject>> search(String searchCond, Long sTime, Long eTime,
				String[] fields, int start, int size) {
		
		List<Map<String,JsonObject>> jeList = new ArrayList<Map<String,JsonObject>>();
		//搜索条件为空，默认为*
		if(searchCond == null || StringUtil.getStringLen(searchCond)==0)searchCond = "*";
		int istart = start;
		int isize = size;
		for (Map<String, Long> es : DataUtils.ESRange(sTime, eTime)) {
			long stime = es.get("stime");
			long etime = es.get("etime");
			String index  = this.getIndex(es.get("index_long"));
			if(!indexExists(index)) continue;//索引不存在跳过，继续循环
			JestResult result = this.searchByIndex(index,searchCond, stime, etime, fields, istart, isize);
			//解析hits>>hits>>(_source,highlight)
			if (result != null && result.getJsonObject() != null) {
				JsonElement hits = result.getJsonObject().get("hits");
				if (hits != null && hits.getAsJsonObject() != null) {
					JsonElement total = hits.getAsJsonObject().get("total");
					long _total = Integer.valueOf(total.toString());
					JsonElement _hits = hits.getAsJsonObject().get("hits");
					if (_hits != null && _hits.getAsJsonArray() != null) {
						for (JsonElement o : _hits.getAsJsonArray()){
							if(o!=null && o.getAsJsonObject()!=null){
								Map<String,JsonObject> s_h = new HashMap<String,JsonObject>();
								s_h.put("hit", o.getAsJsonObject());
								JsonElement source = o.getAsJsonObject().get("_source");
								if(source != null) s_h.put("_source", source.getAsJsonObject());
								JsonElement highlight = o.getAsJsonObject().get("highlight");
								if(highlight != null) s_h.put("highlight", highlight.getAsJsonObject());
								jeList.add(s_h);
							}
						}
						if (_hits.getAsJsonArray().size() == isize) {
							break;
						} else {
							isize -= _hits.getAsJsonArray().size();
							if (istart - _total < 0)istart = 0;else istart -= _total;
						}
					}
				}
			}
		}
		return jeList;
	}
		
		public List<Map<String,JsonObject>> search_(String searchCond, Long sTime, Long eTime,
				String[] fields, int start, int size) {
		
		List<Map<String,JsonObject>> jeList = new ArrayList<Map<String,JsonObject>>();
		//搜索条件为空，默认为*
		if(searchCond == null || StringUtil.getStringLen(searchCond)==0)searchCond = "*";
		int istart = start;
		int isize = size;
		for (Map<String, Long> es : DataUtils.ESRange(sTime, eTime)) {
			long stime = es.get("stime");
			long etime = es.get("etime");
			String index  = this.getIndex(es.get("index_long"));
			if(!indexExists(index)) continue;//索引不存在跳过，继续循环
			JestResult result = this.searchByIndex_(index,searchCond, stime, etime, fields, istart, isize);
			//解析hits>>hits>>(_source,highlight)
			if (result != null && result.getJsonObject() != null) {
				JsonElement hits = result.getJsonObject().get("hits");
				if (hits != null && hits.getAsJsonObject() != null) {
					JsonElement total = hits.getAsJsonObject().get("total");
					long _total = Integer.valueOf(total.toString());
					JsonElement _hits = hits.getAsJsonObject().get("hits");
					if (_hits != null && _hits.getAsJsonArray() != null) {
						for (JsonElement o : _hits.getAsJsonArray()){
							if(o!=null && o.getAsJsonObject()!=null){
								Map<String,JsonObject> s_h = new HashMap<String,JsonObject>();
								s_h.put("hit", o.getAsJsonObject());
								JsonElement source = o.getAsJsonObject().get("_source");
								if(source != null) s_h.put("_source", source.getAsJsonObject());
								JsonElement highlight = o.getAsJsonObject().get("highlight");
								if(highlight != null) s_h.put("highlight", highlight.getAsJsonObject());
								jeList.add(s_h);
							}
						}
						if (_hits.getAsJsonArray().size() == isize) {
							break;
						} else {
							isize -= _hits.getAsJsonArray().size();
							if (istart - _total < 0)istart = 0;else istart -= _total;
						}
					}
				}
			}
		}
		return jeList;
	}
	
	/**
	 * 依据单个索引查询，切时间范围也在索引(yyyy-MM-dd 00:00:00 -- yyyy-MM-dd 23:59:59)内
	 * @return
	 */
	private JestResult searchByIndex(String index, String searchCond, Long sTime,
			Long eTime, String[] fields, int start, int size) {
		searchCond = this.appendCond(searchCond);
		String defalut_field = ResourceUtil.get("sysconfig","query_string.default_field");

		JestResult result = null;
		// StringQuery查询
		QueryStringQueryBuilder qsqb = QueryBuilders.queryString(searchCond);
		qsqb.defaultOperator(Operator.AND).defaultField(defalut_field);// .analyzer("DEFAULT");

		// RangeFilter过滤
		RangeFilterBuilder rfb = FilterBuilders.rangeFilter("@timestamp")
				.from(sTime).to(eTime).includeUpper(false);

		// FilteredQuery查询
		FilteredQueryBuilder query = QueryBuilders.filteredQuery(qsqb, rfb);
		
		SearchSourceBuilder ssb = new SearchSourceBuilder().query(query);
		ssb.from(start).size(size).sort("@timestamp", SortOrder.DESC).fetchSource(fields, null)
		.explain(true).highlight(highlight().field("msg.message", 9999, 0).order("score")
				.tagsSchema("styled").preTags("<span class='hl_f'>").postTags("</span>"));
		Builder searchBuilder = new Search.Builder(ssb.toString());
		result = this.getJestResult(searchBuilder.addIndex(index).build());
		return result;
	}
	
	private JestResult searchByIndex_(String index, String searchCond, Long sTime,
			Long eTime, String[] fields, int start, int size) {
		searchCond = this.appendCond(searchCond);
		String defalut_field = ResourceUtil.get("sysconfig","query_string.default_field");

		JestResult result = null;
		// StringQuery查询
		QueryStringQueryBuilder qsqb = QueryBuilders.queryString(searchCond);
		qsqb.defaultOperator(Operator.AND).defaultField(defalut_field);// .analyzer("DEFAULT");

		// RangeFilter过滤
		RangeFilterBuilder rfb = FilterBuilders.rangeFilter("@timestamp")
				.from(sTime).to(eTime).includeUpper(false);

		// FilteredQuery查询
		FilteredQueryBuilder query = QueryBuilders.filteredQuery(qsqb, rfb);
		
		SearchSourceBuilder ssb = new SearchSourceBuilder().query(query);
		ssb.from(start).size(size).sort("@timestamp", SortOrder.ASC).fetchSource(fields, null)
		.explain(true).highlight(highlight().field("msg.message", 9999, 0).order("score")
				.tagsSchema("styled").preTags("<span class='hl_f'>").postTags("</span>"));
		Builder searchBuilder = new Search.Builder(ssb.toString());
		result = this.getJestResult(searchBuilder.addIndex(index).build());
		return result;
	}
	
	private String appendCond(String conds){
		HttpSession session = ContextHolderUtils.getSession();
		String logConds = (String)session.getAttribute("LOG_CONDS");
		if(logConds != null && !"".equals(logConds)){
			String split_sign = ResourceUtil.get("sysconfig", "condition.split.sign");
			String _split = getSplitSign(split_sign);
			logConds = logConds.replace(_split, " ");
			
			conds += " AND " + logConds;
		}
		return conds;
	}
	
	private String getSplitSign(String sign){
		String _split = "";
		if(sign!=null && sign.length()>0)
			for(int i=0;i<sign.length();i++)
				_split += "\\" + sign.substring(i, i+1);
		return _split;
	}
	
	/**
	 * 在时间范围[sTime, eTime]内，获取满足条件q_str的文档数
	 * @param searchCond
	 * @param sTime
	 * @param eTime
	 */
	public long count(String searchCond,Long sTime,Long eTime){
		long cnt = 0;
		for(Map<String,Long> seTime : DataUtils.ESRange(sTime, eTime)){
			cnt += this.countByDay(searchCond, seTime.get("stime"), seTime.get("etime"),seTime.get("index_long"));
		}
		return cnt;
	}
	
	public static double countAll(){
		String logindexpre = ResourceUtil.get("sysconfig",  "elasticsearch.index.prefix");
		Count count = new Count.Builder().addIndex(logindexpre + "-*").build();
		JestResult result = getJestResult(count);
		return Double.valueOf(result.getValue("count").toString());
	}
	
	/**
	 * 在时间范围[sTime, eTime]内，获取满足条件q_str的文档数
	 * 
	 * @param searchCond
	 * @param sTime
	 * @param eTime
	 */
	private long countByDay(String searchCond, Long sTime, Long eTime, Long indexLong) {
		searchCond = this.appendCond(searchCond);
		String defalut_field = ResourceUtil.get("sysconfig","query_string.default_field");
		
		long cnt = 0;
		String index = this.getIndex(indexLong);
		if (!indexExists(index)) return cnt;

		if (searchCond == null || StringUtil.getStringLen(searchCond) == 0)
			searchCond = "*";
		// StringQuery查询
		QueryStringQueryBuilder qsqb = QueryBuilders.queryString(searchCond);
		qsqb.defaultOperator(Operator.AND).defaultField(defalut_field);// .analyzer("DEFAULT");

		// RangeFilter过滤
		RangeFilterBuilder rfb = FilterBuilders.rangeFilter("@timestamp")
				.from(sTime).to(eTime).includeUpper(false);

		// FilteredQuery查询
		FilteredQueryBuilder query = QueryBuilders.filteredQuery(qsqb, rfb);

		SearchSourceBuilder ssb = new SearchSourceBuilder().query(query);
		Builder searchBuilder = new Search.Builder(ssb.toString());
		searchBuilder.setSearchType(io.searchbox.params.SearchType.COUNT);
		
		
		JestResult result = this.getJestResult(searchBuilder.addIndex(
				index).build());
		if (result != null && result.getJsonObject() != null) {
			JsonElement hits = result.getJsonObject().get("hits");
			if (hits != null && hits.getAsJsonObject() != null) {
				JsonElement total = hits.getAsJsonObject().get("total");
				if (total != null) {
					long _count = Long.valueOf(total.toString());
					cnt += _count;
				}
			}
		}
		return cnt;
	}
	
	/**
	 * 索引是否存在
	 */
	public static boolean indexExists(String index){
		if(index!=null){
			IndicesExists indicesExists = new IndicesExists.Builder(index).build();
			JestResult result = getJestResult(indicesExists);
			return Boolean.valueOf(result.getValue("ok").toString());
		}
		return false;
		
	}
	
	
	/**
	 * 根据索引，获取stats结果
	 * @param index
	 * @return
	 */
	public JestResult getStatsResult(String index){
		Stats stats = new Stats.Builder().addIndex(index).build();
		return getJestResult(stats);
	}
	
	/**
	 * 根据索引，获取stats结果
	 * @param index
	 * @return
	 */
	public JestResult getAliasesResult(String index){
		GetAliases aliases = new GetAliases.Builder().addIndex(index).build();
		return getJestResult(aliases);
	}
	
	/**
	 * 根据索引，获取mapping结果
	 * @param index
	 * @return
	 */
	public JestResult getMappingResult(String[] indices){
		GetMapping mapping = null;
		for(String index : indices){
			mapping = new GetMapping.Builder().addIndex(index).build();
		}
		return getJestResult(mapping);
	}
	
	
	/**
	 * 获取索引
	 * @param index_long
	 * @return
	 */
	public String getIndex(Long index_long){
		String index_prefix = ResourceUtil.get("sysconfig","elasticsearch.index.prefix");
		String index_pattern = "yyyy.MM.dd";
		return index_prefix+"-" + DataUtils.formatDate(new Date(index_long), index_pattern);
	}
	
	
	
	/**
	 * 
	 * @param stime 起始时间
	 * @param etime 结束时间
	 * @param limit 最多包含多少个时间间隔的索引；默认0，表示全部
	 * @return
	 */
	public static List<String> getIndexList(Long sTime, Long eTime, int limit){
		String index_prefix = ResourceUtil.get("sysconfig","elasticsearch.index.prefix");
		List<String> indexList = new ArrayList<String>();
		
		//索引名格式 datagroup-yyyy.MM.dd
		String index_pattern = "yyyy.MM.dd";
		//索引之间时间间隔: 天
		int step_time = 1;
		int times = 0 ;
		
		long cTime = eTime;
		while(cTime >= sTime){
			indexList.add(index_prefix+"-" + DataUtils.formatDate(new Date(cTime), index_pattern));
			++times;
			if(limit >0 && times>=limit) break;
			cTime -= 1000L*24*60*60*step_time;
		}
		
		if(limit==0 || times<limit){
			String sIndex = index_prefix+"-" + DataUtils.formatDate(new Date(sTime), index_pattern);
			if(!indexList.contains(sIndex)){
				indexList.add(sIndex);
			}
		}
		return indexList;
	}
	
	public JsonObject searchById(String index, String id) {
		
		JestResult result = null;
		IdsQueryBuilder iqb = QueryBuilders.idsQuery().ids(id);
		SearchSourceBuilder ssb = new SearchSourceBuilder().query(iqb);
		Builder searchBuilder = new Search.Builder(ssb.toString());
		result = this.getJestResult(searchBuilder.addIndex(index).build());
		
		if (result != null && result.getJsonObject() != null) {
			JsonElement hits = result.getJsonObject().get("hits");
			if (hits != null && hits.getAsJsonObject() != null) {
				JsonElement _hits = hits.getAsJsonObject().get("hits");
				if (_hits != null && _hits.getAsJsonArray() != null) {
					JsonElement _hits_0 = _hits.getAsJsonArray().get(0);
					if(_hits_0!=null){
						return _hits_0.getAsJsonObject().get("_source").getAsJsonObject();
					}
				}
			}
		}
		return null;
	}
	
	public static void main(String[] args){
		
	}
	
	/**
	 * 获取JestResult
	 * @param action
	 * @return
	 */
	public static JestResult getJestResult(Action action){
		JestResult result = null;
		JestHttpClient client = null;
		try {
			client = getJestClient();
			result = client.execute(action);
		} catch (Exception e) {
			//log.error(e.getMessage(), e);
		}finally{
			//client.shutdownClient();
		}
		return result;
	}

	/**
	 * Get JestClient
	 * @return
	 */
	public static JestHttpClient getJestClient() {
		return SingletonJestClient.getInstance().getJestClient();
	}
	
	
	/*---------------------------------------------------------------*/
	
	
	
	/**
	 * 在时间范围[sTime, eTime]内，获取满足条件q_str的文档数
	 * @param searchCond
	 * @param sTime
	 * @param eTime
	 */
	public Map<String,Object> countForDataInterface(String searchCond,String[] fieldList,Long sTime,Long eTime,Integer size){
		
		String defalut_field = ResourceUtil.get("sysconfig","query_string.default_field");
		if(searchCond == null || StringUtil.getStringLen(searchCond)==0)searchCond = "*";
		JestResult result = null;
		// StringQuery查询
		QueryStringQueryBuilder qsqb = QueryBuilders.queryString(searchCond);
		qsqb.defaultOperator(Operator.AND).defaultField(defalut_field);// .analyzer("DEFAULT");

		// RangeFilter过滤
		RangeFilterBuilder rfb = FilterBuilders.rangeFilter("@timestamp")
				.from(sTime).to(eTime).includeUpper(false);

		// FilteredQuery查询
		FilteredQueryBuilder query = QueryBuilders.filteredQuery(qsqb, rfb);
		
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.fetchSource(fieldList, null).query(query);
		Search search = new Search.Builder(searchSourceBuilder.toString())
				.addIndex("_all")
				.setParameter(Parameters.SEARCH_TYPE, SearchType.SCAN)
				.setParameter(Parameters.SIZE, size)
				.setParameter(Parameters.SCROLL, "5m")
				.build();
		result = this.getJestResult(search);
		
		String scrollId = result.getJsonObject().get("_scroll_id").getAsString();
		JsonElement hits = result.getJsonObject().get("hits");
		long total = hits.getAsJsonObject().get("total").getAsLong();
		
		Map<String,Object> rtnMap = new HashMap<String,Object>();
		rtnMap.put("scrollId", scrollId);
		rtnMap.put("total", total);
		return rtnMap;
	}
	
	/**
	 * Scroll 方式搜索
	 * @param scrollId
	 * @return
	 */
	public JestResult searchForDataInterface(String scrollId){
		JestResult result = null;
		SearchScroll scroll = new SearchScroll.Builder(scrollId, "5m").build();
		result = this.getJestResult(scroll);
		return result;
	}
	
}
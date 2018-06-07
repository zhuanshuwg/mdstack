package mdstack.warn.controllor;

import io.searchbox.client.JestResult;
import io.searchbox.client.http.JestHttpClient;
import io.searchbox.core.Count;
import io.searchbox.core.Search;
import io.searchbox.core.Search.Builder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import mdstack.warn.dao.WarnDao;
import mdstack.warn.entity.QueryBean;
import mdstack.warn.service.WarnService;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.elasticsearch.index.query.BoolFilterBuilder;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.index.query.FilteredQueryBuilder;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder.Operator;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.framework.core.common.dto.datatable.DataTableDto;
import org.framework.core.common.model.json.DataTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

@Controller
@RequestMapping("esWarn")
public class EsControllor {

	private static final Logger logger = Logger.getLogger(EsControllor.class);

	@RequestMapping(value = "warn_page")
	public String warnPage() {

		return "warn/esList";
	}

	/**
	 * 报警列表
	 * 
	 * @param request
	 * @param bean
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "esList")
	@ResponseBody
	public DataTable warnList(HttpServletRequest request, QueryBean bean,Model model) {
		

		String indexName = warnDao.getIndexName();
		String type = StringUtils.isNotEmpty(bean.getLog_source()) ? bean
				.getLog_source() : "flow";
		DataTable dt = new DataTable();
		DataTableDto dtd = new DataTableDto(request);
		// delIndex(indexName,type);
		// createIndex();
		try {
			
			Map<String, Object> map = getJsonData(indexName, type,
					dtd.getiDisplayStart(), dtd.getiDisplayLength(), bean);
			List<Object> list = (List<Object>) map.get("infoList");

			if (map != null && list != null) {
				dt.setsEcho(dtd.getsEcho());
				dt.setiTotalDisplayRecords((Integer) map.get("totalRecords"));
				dt.setiTotalRecords((Integer) map.get("totalRecords"));
				dt.setAaData(list);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dt;
	}

	/**
	 * 流量基本信息(暂时不用)
	 * 
	 * @return
	 */
	@RequestMapping(value = "flow")
	@ResponseBody
/*	public List<Object> flowBaseInfo(HttpServletRequest request) {

		List<Object> list = new ArrayList<Object>();
		String id = StringUtils.isNotEmpty(request.getParameter("id")) ? request
				.getParameter("id") : "140804";
		String indexId = StringUtils
				.isNotEmpty(request.getParameter("indexId")) ? request
				.getParameter("indexId") : "140804";

		// MatchAllQueryBuilder query = QueryBuilders.matchAllQuery();
		IdsQueryBuilder query = QueryBuilders.idsQuery().ids(id);
		String indexName = "sessions-" + indexId;

		SearchSourceBuilder ssb = new SearchSourceBuilder().query(query).sort(
				"end_time", SortOrder.DESC);
		Builder searchBuilder = new Search.Builder(ssb.toString());
		Search search = searchBuilder.addIndex(indexName).addType("session")
				.build();
		JestResult result = flowDao.getJestResult(search);
		JsonArray data = result.getJsonObject().get("hits").getAsJsonObject()
				.get("hits").getAsJsonArray();
		for (int i = 0; i < data.size(); i++) {
			JsonObject field = data.get(i).getAsJsonObject();
			JsonObject source = field.get("_source").getAsJsonObject();
			source.addProperty("id", field.get("_id").getAsString());
			list.add(source);
		}
		// JsonObject source = field.get("_source").getAsJsonObject();
		// data;
		return list;
	}*/

	/**
	 * 获取json结构数据
	 */
	private Map<String, Object> getJsonData(String indexName, String type,
			int pageNo, int pageSize, QueryBean bean) {

		Map<String, Object> infoMap = new HashMap<String, Object>();
		List<Object> list = new ArrayList<Object>();
		if (!warnDao.indexExists(indexName))
			return null;
		int totalRecords = 0;
		JsonArray data = new JsonArray();
		// MatchAllQueryBuilder query = QueryBuilders.matchAllQuery();
		try {
			FilteredQueryBuilder query = makeConditions(bean);
			
			SearchSourceBuilder ssb = new SearchSourceBuilder()
					.query(query).from(pageNo).size(pageSize)
					.sort("end_time", SortOrder.DESC);// 分页 排序
			Builder searchBuilder = new Search.Builder(ssb.toString());
			Search search = searchBuilder.addIndex(indexName).addType(type)
					.build();
			JestResult result = warnDao.getJestResult(search);
			if (result!=null) {
				totalRecords = result.getJsonObject().get("hits").getAsJsonObject().get("total").getAsInt();
				data = result.getJsonObject().get("hits")
						.getAsJsonObject().get("hits").getAsJsonArray();
			}
			for (int i = 0; i < data.size(); i++) {

				Map<String, Object> map = new HashMap<String, Object>();
				JsonObject field = data.get(i).getAsJsonObject();
				JsonObject source = field.get("_source").getAsJsonObject();
				 source.addProperty("id", field.get("_id").getAsString());
				 String id=source.get("id").getAsString();
				 
				map.put("id", id);
				map.put("start_time",
						source.has("start_time") ? source.get("start_time")
								.getAsString() : "");
				map.put("end_time",
						source.has("end_time") ? source.get("end_time")
								.getAsString() : "");
				map.put("alarm",
						source.has("alarm") ? String.valueOf(source
								.get("alarm").getAsJsonObject()) : "");
				map.put("data",
						source.has("data") ? String.valueOf(source.get("data")
								.getAsJsonObject()) : "");
				// map.put("alarm_cnt", source.get("alarm_cnt").getAsString());
				// map.put("source", source.get("source").getAsString());
				map.put("src_ip", source.has("src_ip") ? source.get("src_ip")
						.getAsString() : "");
				map.put("src_port",
						source.has("src_port") ? source.get("src_port")
								.getAsString() : "");
				map.put("dst_ip", source.has("dst_ip") ? source.get("dst_ip")
						.getAsString() : "");
				map.put("dst_port",
						source.has("dst_port") ? source.get("dst_port")
								.getAsString() : "");
				map.put("proto", source.has("proto") ? source.get("proto")
						.getAsString() : "");
				map.put("log_source",
						source.has("log_source") ? source.get("log_source")
								.getAsString() : "");
				map.put("hostname",
						source.has("hostname") ? source.get("hostname")
								.getAsString() : "");
				// map.put("es_id", source.get("es_id").getAsString());
				map.put("bk_wt", source.has("bk_wt") ? source.get("bk_wt")
						.getAsString() : "");
				map.put("detect_time",
						source.has("detect_time") ? source.get("detect_time")
								.getAsString() : "");
				list.add(map);
			}

		} catch (Exception e) {

			logger.error(e.getMessage(), e);
		}
		infoMap.put("infoList", list);
		infoMap.put("totalRecords", totalRecords);
		return infoMap;
	}

	/**
	 * 生成查询条件
	 * 
	 */
	public FilteredQueryBuilder makeConditions(QueryBean bean) {
		boolean flag=false;
		BoolFilterBuilder bf = FilterBuilders.boolFilter();
		BoolQueryBuilder sq = QueryBuilders.boolQuery();
		if (StringUtils.isNotEmpty(bean.getBk_wt())) {
			sq.must(QueryBuilders.queryString("bk_wt:"+bean.getBk_wt()).defaultField("bk_wt").defaultOperator(Operator.AND));
		}
		if (StringUtils.isNotEmpty(bean.getDst_ip())) {

			if (bean.getDst_ip().contains("*")) {
				sq.must(QueryBuilders.wildcardQuery("dst_ip", bean.getDst_ip()));
			} else {
				sq.must(QueryBuilders.queryString("dst_ip:"+bean.getDst_ip()).defaultField("dst_ip").defaultOperator(Operator.AND));
			
			}
		}

		if (StringUtils.isNotEmpty(bean.getSrc_ip())) {
			if (bean.getSrc_ip().contains("*")) {
				sq.must(QueryBuilders.wildcardQuery("src_ip", bean.getSrc_ip()));
			} else {
				sq.must(QueryBuilders.queryString("src_ip:"+bean.getSrc_ip()).defaultField("src_ip").defaultOperator(Operator.AND));
				
			}
		}
		if (StringUtils.isNotEmpty(bean.getSrc_port())) {
			sq.must(QueryBuilders.queryString("src_port:"+bean.getSrc_port()).defaultField("src_port").defaultOperator(Operator.AND));
			
		}
		if (StringUtils.isNotEmpty(bean.getDst_port())) {
			sq.must(QueryBuilders.queryString("dst_port:"+bean.getDst_port()).defaultField("dst_port").defaultOperator(Operator.AND));
			
		}
		if (StringUtils.isNotEmpty(bean.getProto())) {
			sq.must(QueryBuilders.queryString("proto:"+bean.getProto()).defaultField("proto").defaultOperator(Operator.AND));
			
		}
		if (StringUtils.isNotEmpty(bean.getWarn_type())) {
			// bf.must(FilterBuilders.termFilter("warnType",
			// bean.getWarn_type()));
			String warn_type = "alarm." + bean.getWarn_type();
			bf.must(FilterBuilders.existsFilter(warn_type));
			flag=true;
		}

		if (StringUtils.isNotEmpty(bean.getWarn_value())) {
			sq.must(QueryBuilders.queryString(bean.getWarn_value()));
		}
		if (!flag) {
			bf = null;
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
		long s_time = 0;
		long e_time = 0;

		try {
			e_time = df.parse(df.format(new Date())).getTime();
			if (StringUtils.isNotEmpty(bean.getS_time())) {
				s_time = df.parse(bean.getS_time()).getTime();
			} else {
				s_time = e_time - 60 * 60 * 1000;
			}
			if (StringUtils.isNotEmpty(bean.getE_time())) {
				e_time = df.parse(bean.getE_time()).getTime();
			}
		} catch (ParseException e) {

			logger.error(e.getMessage(), e);
		}
		RangeQueryBuilder qb = QueryBuilders.rangeQuery("end_time");
        sq.must(qb);
		qb.from(s_time).to(e_time).includeLower(false).includeUpper(false);
		FilteredQueryBuilder fqb = QueryBuilders.filteredQuery(sq, bf);
		return fqb;

	}
	/**
	 * 数据字典
	 * @return
	 */
	private WarnService warnService;

	private static WarnDao warnDao;

	public WarnService getWarnService() {
		return warnService;
	}

	@Autowired
	public void setWarnService(WarnService warnService) {
		this.warnService = warnService;
	}

	public WarnDao getWarnDao() {
		return warnDao;
	}

	@Autowired
	public void setWarnDao(WarnDao warnDao) {
		this.warnDao = warnDao;
	}


}

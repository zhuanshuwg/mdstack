package org.framework.tag.core;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.framework.core.util.oConvertUtils;


public class BaseTag extends TagSupport {

	private static final long serialVersionUID = 7696219105145188404L;
	protected String type = "default";// 加载类型
	protected String ctx = "";//上下文环境路径

	public void setType(String type) {
		this.type = type;
	}
	public void setCtx(String ctx){
		this.ctx = ctx;
	}

	@Override
	public int doStartTag() throws JspException {
		return EVAL_PAGE;
	}

	@Override
	public int doEndTag() throws JspException {
		try {
			JspWriter out = this.pageContext.getOut();
			StringBuffer sb = new StringBuffer();

			String types[] = type.split(",");
			
			//模板样式 切勿修改
			if(oConvertUtils.isIn("SmartAdmin.css", types)){
				/*<!-- Basic Styles -->*/
				sb.append("<link href=\"" + ctx + "/plug-in/thirdparty/SmartAdmin/css/bootstrap.min.css\" rel=\"stylesheet\">");
				sb.append("<link href=\"" + ctx + "/plug-in/thirdparty/SmartAdmin/css/font-awesome.min.css\" rel=\"stylesheet\">");
				/*<!-- SmartAdmin Styles : Caution! DO NOT change the order -->*/
				sb.append("<link href=\"" + ctx + "/plug-in/thirdparty/SmartAdmin/css/smartadmin-production-plugins.min.css\" rel=\"stylesheet\">");
				sb.append("<link href=\"" + ctx + "/plug-in/thirdparty/SmartAdmin/css/smartadmin-production.min.css\" rel=\"stylesheet\">");
				sb.append("<link href=\"" + ctx + "/plug-in/thirdparty/SmartAdmin/css/smartadmin-skins.min.css\" rel=\"stylesheet\">");
				/*<!-- SmartAdmin RTL Support  -->*/
				sb.append("<link href=\"" + ctx + "/plug-in/thirdparty/SmartAdmin/css/smartadmin-rtl.min.css\" rel=\"stylesheet\">");
				
				sb.append("<link href=\"" + ctx + "/plug-in/thirdparty/SmartAdmin/css/icons.css\" rel=\"stylesheet\">");
				/*<!-- Demo purpose only: goes with demo.js, you can delete this css when designing your own WebApp -->*/
				sb.append("<link href=\"" + ctx + "/plug-in/thirdparty/SmartAdmin/css/demo.min.css\" rel=\"stylesheet\">");
			
			}
			//拖拽组件样式  切勿修改
			if(oConvertUtils.isIn("gridstack.css", types)){
				/*<!-- gridstack 核心文件 -->*/
				sb.append("<link href=\"" + ctx + "/plug-in/thirdparty/gridstack/css/gridstack.css\" rel=\"stylesheet\">");
				/*gridstack自定义css  覆盖源码css样式*/
				sb.append("<link href=\"" + ctx + "/plug-in/thirdparty/gridstack/css/gridstack.pro.css\" rel=\"stylesheet\">");
			}
			if(oConvertUtils.isIn("jstree.css", types)){
				/*<!-- jstree -->*/
				sb.append("<link href=\"" + ctx + "/plug-in/thirdparty/jstree/dist/themes/default/style.css\" rel=\"stylesheet\">");
			}
			if(oConvertUtils.isIn("loading.css", types)){
				/*加载信息遮罩层*/
				sb.append("<link href=\"" + ctx + "/plug-in/busi/common/css/loading.css\" rel=\"stylesheet\">");
			}
			if(oConvertUtils.isIn("common.css", types)){
				sb.append("<link href=\"" + ctx + "/plug-in/busi/common/css/common.css\" rel=\"stylesheet\">");
			}
			if(oConvertUtils.isIn("My97DatePicker.css", types)){
				/*日历选择插件*/
				sb.append("<link href=\"" + ctx + "/plug-in/thirdparty/My97DatePicker/skin/WdatePicker.css\" rel=\"stylesheet\">");
			}
			if(oConvertUtils.isIn("dataTable.css", types)){
				/*加载信息遮罩层*/
				sb.append("<link href=\"" + ctx + "/plug-in/thirdparty/dataTable-1.10/css/jquery.dataTables.css\" rel=\"stylesheet\">");
			}
			if (oConvertUtils.isIn("custom.css", types)){
				/*<!-- We recommend you use "your_style.css" to override SmartAdmin
			     specific styles this will also ensure you retrain your customization with each SmartAdmin update.-->*/
				sb.append("<link href=\"" + ctx + "/plug-in/thirdparty/SmartAdmin/css/custom.css\" rel=\"stylesheet\">");
			}
			/**
			 * jquery.js
			 */
			if (oConvertUtils.isIn("jquery.js", types)) {
				sb.append("<script type=\"text/javascript\" src=\"" + ctx + "/plug-in/thirdparty/SmartAdmin/js/libs/jquery-2.1.1.min.js\"></script>");
				sb.append("<script type=\"text/javascript\" src=\"" + ctx + "/plug-in/thirdparty/SmartAdmin/js/libs/jquery-ui-1.10.3.min.js\"></script>");
			}
			/**
			 * 前端  SmartAdmin js文件
			 * */
			if (oConvertUtils.isIn("SmartAdmin.js", types)) {
				/*<!-- IMPORTANT: APP CONFIG -->*/ 
				sb.append("<script type=\"text/javascript\" src=\"" + ctx + "/plug-in/thirdparty/SmartAdmin/js/app.config.js\"></script>");
				/*<!-- JS TOUCH : include this plugin for mobile drag / drop touch events--> */
				sb.append("<script type=\"text/javascript\" src=\"" + ctx + "/plug-in/thirdparty/SmartAdmin/js/plugin/jquery-touch/jquery.ui.touch-punch.min.js\"></script>");
				/*<!-- BOOTSTRAP JS --> */
				sb.append("<script type=\"text/javascript\" src=\"" + ctx + "/plug-in/thirdparty/SmartAdmin/js/bootstrap/bootstrap.min.js\"></script>");
				/*<!-- CUSTOM NOTIFICATION --> */
				sb.append("<script type=\"text/javascript\" src=\"" + ctx + "/plug-in/thirdparty/SmartAdmin/js/notification/SmartNotification.min.js\"></script>");
				/*<!-- JARVIS WIDGETS -->*/
				sb.append("<script type=\"text/javascript\" src=\"" + ctx + "/plug-in/thirdparty/SmartAdmin/js/smartwidgets/jarvis.widget.min.js\"></script>");
				/*<!-- SPARKLINES --> */
				sb.append("<script type=\"text/javascript\" src=\"" + ctx + "/plug-in/thirdparty/SmartAdmin/js/plugin/sparkline/jquery.sparkline.min.js\"></script>");
				/*<!-- browser msie issue fix -->*/ 
				sb.append("<script type=\"text/javascript\" src=\"" + ctx + "/plug-in/thirdparty/SmartAdmin/js/plugin/msie-fix/jquery.mb.browser.min.js\"></script>");
				/*<!-- FastClick: For mobile devices -->*/ 
				sb.append("<script type=\"text/javascript\" src=\"" + ctx + "/plug-in/thirdparty/SmartAdmin/js/plugin/fastclick/fastclick.min.js\"></script>");
				/*<!-- Demo purpose only -->*/ 
				sb.append("<script type=\"text/javascript\" src=\"" + ctx + "/plug-in/thirdparty/SmartAdmin/js/demo.min.js\"></script>");
				/*<!-- MAIN APP JS FILE -->*/
				sb.append("<script type=\"text/javascript\" src=\"" + ctx + "/plug-in/thirdparty/SmartAdmin/js/app.min.js\"></script>");
				/*<!-- ENHANCEMENT PLUGINS : NOT A REQUIREMENT --> 
				<!-- Voice command : plugin -->*/ 
				sb.append("<script type=\"text/javascript\" src=\"" + ctx + "/plug-in/thirdparty/SmartAdmin/js/speech/voicecommand.min.js\"></script>");
				/*<!-- SmartChat UI : plugin -->*/ 
				sb.append("<script type=\"text/javascript\" src=\"" + ctx + "/plug-in/thirdparty/SmartAdmin/js/smart-chat-ui/smart.chat.ui.min.js\"></script>");
				sb.append("<script type=\"text/javascript\" src=\"" + ctx + "/plug-in/thirdparty/SmartAdmin/js/smart-chat-ui/smart.chat.manager.min.js\"></script>");
				/*<!-- PAGE RELATED PLUGIN(S) --> */
				sb.append("<script type=\"text/javascript\" src=\"" + ctx + "/plug-in/thirdparty/SmartAdmin/js/plugin/delete-table-row/delete-table-row.min.js\"></script>");
				sb.append("<script type=\"text/javascript\" src=\"" + ctx + "/plug-in/thirdparty/SmartAdmin/js/plugin/summernote/summernote.min.js\"></script>");
			}
			if(oConvertUtils.isIn("gridstack.js", types)){
				sb.append("<script type=\"text/javascript\" src=\"" + ctx + "/plug-in/thirdparty/gridstack/js/lodash.js\"></script>");
				sb.append("<script type=\"text/javascript\" src=\"" + ctx + "/plug-in/thirdparty/gridstack/js/gridstack.js\"></script>");
				sb.append("<script type=\"text/javascript\" src=\"" + ctx + "/plug-in/thirdparty/gridstack/js/gridstackPro.js\"></script>");
			}
			if(oConvertUtils.isIn("jstree.js", types)){
				sb.append("<script type=\"text/javascript\" src=\"" + ctx + "/plug-in/thirdparty/jstree/dist/jstree.js\"></script>");
			}
			if(oConvertUtils.isIn("lhgdialog.js", types)){
				sb.append("<script type=\"text/javascript\" src=\"" + ctx + "/plug-in/thirdparty/lhgdialog/lhgdialog.js?self=true&skin=discuz\"></script>");
			}
			if(oConvertUtils.isIn("My97DatePicker.js", types)){
				sb.append("<script type=\"text/javascript\" src=\"" + ctx + "/plug-in/thirdparty/My97DatePicker/WdatePicker.js\"></script>");
			}
			if(oConvertUtils.isIn("twbsPagination.js", types)){
				sb.append("<script type=\"text/javascript\" src=\"" + ctx + "/plug-in/thirdparty/jquery.twbsPagination/jquery.twbsPagination.js\"></script>");
			}
			if(oConvertUtils.isIn("dataTable.js", types)){
				sb.append("<script type=\"text/javascript\" src=\"" + ctx + "/plug-in/thirdparty/dataTable-1.10/js/jquery.dataTables.min.js?self=true&skin=discuz\"></script>");
				sb.append("<script type=\"text/javascript\" src=\"" + ctx + "/plug-in/thirdparty/dataTable-1.10/js/dataTables.bootstrap.min.js?self=true&skin=discuz\"></script>");
				sb.append("<script type=\"text/javascript\" src=\"" + ctx + "/plug-in/thirdparty/dataTable-1.10/js/dataTables.colVis.min.js?self=true&skin=discuz\"></script>");
				sb.append("<script type=\"text/javascript\" src=\"" + ctx + "/plug-in/thirdparty/dataTable-1.10/js/datatables.responsive.min.js?self=true&skin=discuz\"></script>");
				sb.append("<script type=\"text/javascript\" src=\"" + ctx + "/plug-in/thirdparty/dataTable-1.10/js/dataTables.tableTools.min.js?self=true&skin=discuz\"></script>");
				sb.append("<script type=\"text/javascript\" src=\"" + ctx + "/plug-in/thirdparty/dataTable-1.10/js/defined.dataTables.js?self=true&skin=discuz\"></script>");
			}
			/**
			 * 自定义js  css 文件
			 * 
			 * */
			if(oConvertUtils.isIn("common.js", types)){
				sb.append("<script type=\"text/javascript\" src=\"" + ctx + "/plug-in/busi/common/js/common.js\"></script>");
			}
			
			if(oConvertUtils.isIn("echartsPro.js", types)){
				sb.append("<script type=\"text/javascript\" src=\"" + ctx + "/plug-in/thirdparty/echarts3.0/echartsPro.js\"></script>");
			}
			
			out.print(sb.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return EVAL_PAGE;
	}

}

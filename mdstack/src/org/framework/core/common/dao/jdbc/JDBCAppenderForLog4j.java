package org.framework.core.common.dao.jdbc;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.jdbc.JDBCAppender;
import org.apache.log4j.spi.LoggingEvent;
import org.framework.core.util.DataUtils;

public class JDBCAppenderForLog4j extends JDBCAppender {

	@Override
	protected String getLogStatement(LoggingEvent event) {
		StringBuffer sql = new StringBuffer("");
		String description = "";
		if (event != null && event.getRenderedMessage() != null) {
			description = event.getLevel() + ": " + event.getRenderedMessage();
			description = description.replaceAll("'", " ")
					.replaceAll("\"", " ");
		}
		sql.append("insert into t_log (log_type,description,creatime) values(");
		sql.append("'running_log',").append("'" + description + "',");
		sql.append("'"
				+ DataUtils.date2Str(new Date(), new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss")) + "'");
		sql.append(")");

		return sql.toString();
	}
}


package org.framework.core.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

/**
 * @author wangguan
 *
 */
public class ApplicationListener implements ServletContextListener {
	
	//private static final Logger log = Logger.getLogger(ApplicationListener.class);

	
	@Override
	public void contextDestroyed(ServletContextEvent event) {
		//log.info("系统关闭！");
	}

	
	@Override
	public void contextInitialized(ServletContextEvent event) {
		//log.info("系统开启！");
	}

}

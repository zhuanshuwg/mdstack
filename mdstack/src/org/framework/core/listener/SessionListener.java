package org.framework.core.listener;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * 
 * @author wangguan
 * 
 */
public class SessionListener implements HttpSessionListener {
	
	private static int onLineUsers = 0 ;
		
	public void sessionCreated(HttpSessionEvent event) {
		
		onLineUsers++;
		HttpSession session = event.getSession();
		ServletContext application = session.getServletContext();
		// 在application范围，由HashMap保存所有的session
		@SuppressWarnings("unchecked")
		Map<String, HttpSession> sessions = (HashMap<String, HttpSession>) application
				.getAttribute("SESSIONS");
		if (sessions == null) {
			sessions = new HashMap<String, HttpSession>();
			application.setAttribute("SESSIONS", sessions);
		}
		
	}

	public void sessionDestroyed(HttpSessionEvent event) {
		
		if(onLineUsers > 0) onLineUsers--;
		HttpSession session = event.getSession();
		ServletContext application = session.getServletContext();
		@SuppressWarnings("unchecked")
		Map<String, HttpSession> sessions = (HashMap<String, HttpSession>) application.getAttribute("SESSIONS");
		if (sessions != null) sessions.remove(session.getId());
	}
	
	public static int getOnLineUsers(){
		return onLineUsers;
	}

}

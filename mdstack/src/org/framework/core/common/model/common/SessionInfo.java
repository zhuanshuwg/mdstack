package org.framework.core.common.model.common;

import java.io.Serializable;

import mdstack.user.entity.User;




public class SessionInfo implements Serializable {

	private static final long serialVersionUID = -820132057816853332L;
	private User user;
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}

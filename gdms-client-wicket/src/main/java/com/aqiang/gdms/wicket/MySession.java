package com.aqiang.gdms.wicket;

import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.request.Request;

import com.aqiang.bsms.entities.College;
import com.aqiang.bsms.entities.User;

public class MySession extends WebSession {
	private static final long serialVersionUID = 1L;
	private String userType;
	private User user;
	private College college;

	public MySession(Request request) {
		super(request);
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public College getCollege() {
		return college;
	}

	public void setCollege(College college) {
		this.college = college;
	}

}

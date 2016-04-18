package com.aqiang.gdms.wicket.behavior;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;

import com.aqiang.bsms.entities.UserType;

public class TeacherBehavior extends Behavior {

	private static final long serialVersionUID = 1L;
	private String userType;

	public TeacherBehavior(String userType) {
		super();
		this.userType = userType;
	}

	@Override
	public void onConfigure(Component component) {
		if (!UserType.TEACHER.equals(userType)) {
			component.setVisible(false);
		}
	}
}

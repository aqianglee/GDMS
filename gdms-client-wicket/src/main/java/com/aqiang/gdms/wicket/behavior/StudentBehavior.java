package com.aqiang.gdms.wicket.behavior;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;

import com.aqiang.bsms.entities.UserType;

public class StudentBehavior extends Behavior {

	private static final long serialVersionUID = 1L;
	private String userType;

	public StudentBehavior(String userType) {
		super();
		this.userType = userType;
	}

	@Override
	public void onConfigure(Component component) {
		if (!UserType.STUDENT.equals(userType)) {
			component.setVisible(false);
		}
	}
}

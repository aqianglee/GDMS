package com.aqiang.gdms.wicket.behavior;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;

import com.aqiang.bsms.entities.UserType;

public class SystemManagerBehavior extends Behavior {

	private static final long serialVersionUID = 1L;
	private String userType;

	public SystemManagerBehavior(String userType) {
		super();
		this.userType = userType;
	}

	@Override
	public void onConfigure(Component component) {
		if (!UserType.SYSTEM_MANAGER.equals(userType)) {
			component.setVisible(false);
		}
	}
}

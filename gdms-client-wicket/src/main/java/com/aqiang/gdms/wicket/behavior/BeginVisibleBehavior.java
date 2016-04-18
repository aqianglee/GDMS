package com.aqiang.gdms.wicket.behavior;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;

import com.aqiang.bsms.entities.Event;
import com.aqiang.bsms.entities.WorkflowStatus;

public class BeginVisibleBehavior extends Behavior {
	private static final long serialVersionUID = 1L;
	private Event current;

	public BeginVisibleBehavior(Event current) {
		super();
		this.current = current;
	}

	@Override
	public void onConfigure(Component component) {
		if (current == null || !current.getWorkFlowStatus().equals(WorkflowStatus.BEGIN)) {
			component.setVisible(false);
		}
	}
}

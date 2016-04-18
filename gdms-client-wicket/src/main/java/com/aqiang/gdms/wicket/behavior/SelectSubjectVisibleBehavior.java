package com.aqiang.gdms.wicket.behavior;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;

import com.aqiang.bsms.entities.Event;
import com.aqiang.bsms.entities.WorkflowStatus;

public class SelectSubjectVisibleBehavior extends Behavior {
	private static final long serialVersionUID = 1L;
	private Event current;

	public SelectSubjectVisibleBehavior(Event current) {
		super();
		this.current = current;
	}

	@Override
	public void onConfigure(Component component) {
		if (current == null || !current.getWorkFlowStatus().equals(WorkflowStatus.SELECT_SUBJECT)) {
			component.setVisible(false);
		}
	}
}

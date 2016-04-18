package com.aqiang.gdms.wicket.behavior;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;

import com.aqiang.bsms.entities.Event;
import com.aqiang.bsms.entities.WorkflowStatus;

public class ApplySubjectVisibleBehavior extends Behavior {

	private static final long serialVersionUID = 1L;
	private Event current;

	public ApplySubjectVisibleBehavior(Event current) {
		super();
		this.current = current;
	}

	@Override
	public void onConfigure(Component component) {
		if (current == null || !current.getWorkFlowStatus().equals(WorkflowStatus.APPLY_SUBJECT)) {
			component.setVisible(false);
		}
	}
}

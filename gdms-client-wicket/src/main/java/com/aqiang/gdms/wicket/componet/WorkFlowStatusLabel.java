package com.aqiang.gdms.wicket.componet;

public class WorkFlowStatusLabel extends ExtensibleLabel{
	private static final long serialVersionUID = 1L;

	public WorkFlowStatusLabel(String id) {
		super(id);
	}

	@Override
	protected CharSequence getDisplay() {
		String workFlowStatus = (String) getDefaultModelObject();
		if(workFlowStatus == null) {
			return "";
		}
		return getString(workFlowStatus.toUpperCase());
	}
	
}

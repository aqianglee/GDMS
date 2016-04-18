package com.aqiang.gdms.wicket.componet;

public class JobTypeLabel extends ExtensibleLabel {
	private static final long serialVersionUID = 1L;

	public JobTypeLabel(String id) {
		super(id);
	}

	@Override
	protected CharSequence getDisplay() {
		String jobType = (String) getDefaultModelObject();
		if(jobType == null) {
			return "";
		}
		return getString(jobType.toUpperCase());
	}

}

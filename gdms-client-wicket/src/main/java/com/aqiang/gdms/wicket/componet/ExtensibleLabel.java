package com.aqiang.gdms.wicket.componet;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;

public abstract class ExtensibleLabel extends Label {
	private static final long serialVersionUID = 1L;

	public ExtensibleLabel(String id) {
		super(id);
	}

	public ExtensibleLabel(String id, IModel<?> model) {
		super(id, model);
	}

	@Override
	public void onComponentTagBody(MarkupStream markupStream, ComponentTag openTag) {
		replaceComponentTagBody(markupStream, openTag, getDisplay());
	}
	protected abstract CharSequence getDisplay();
}

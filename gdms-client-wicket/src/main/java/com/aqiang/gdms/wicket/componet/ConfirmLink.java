package com.aqiang.gdms.wicket.componet;

import org.apache.wicket.ajax.attributes.AjaxCallListener;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.model.IModel;

public abstract class ConfirmLink<T> extends AjaxLink<T> {
	private static final long serialVersionUID = 1L;

	public ConfirmLink(String id) {
		super(id);
	}

	public ConfirmLink(String id, IModel<T> model) {
		super(id, model);
	}

	@Override
	protected void updateAjaxAttributes(AjaxRequestAttributes attributes) {
		super.updateAjaxAttributes(attributes);
		AjaxCallListener ajaxCallListener = new AjaxCallListener();
		ajaxCallListener.onPrecondition("return confirm('" + getString(getMessageKey()) + "');");
		attributes.getAjaxCallListeners().add(ajaxCallListener);
	}

	protected abstract String getMessageKey();
}
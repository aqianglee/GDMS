package com.aqiang.gdms.wicket.componet;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.wicket.model.IModel;

public class DateLabel extends ExtensibleLabel {
	private String DATE_FORMAT = "yyyy-MM-dd";

	public DateLabel(String id) {
		super(id);
	}

	public DateLabel(String id, IModel<Date> dateModel) {
		super(id, dateModel);
	}

	public DateLabel(String id, String dateformat) {
		super(id);
		DATE_FORMAT = dateformat;
	}

	public DateLabel(String id, IModel<Date> dateModel, String dateformat) {
		super(id, dateModel);
		DATE_FORMAT = dateformat;
	}

	private static final long serialVersionUID = 1L;

	@Override
	protected CharSequence getDisplay() {
		Date date = (Date) getDefaultModelObject();
		if (date == null) {
			return "";
		}
		return new SimpleDateFormat(DATE_FORMAT).format(date);
	}

}

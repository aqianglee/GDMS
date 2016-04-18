package com.aqiang.gdms.wicket.componet;

import java.util.Arrays;

import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.IChoiceRenderer;

public class CommentDropDownChoice extends DropDownChoice<Boolean> {
	private static final long serialVersionUID = 1L;

	public CommentDropDownChoice(String id) {
		super(id);
		setChoices(Arrays.asList(Boolean.TRUE, Boolean.FALSE));
		setChoiceRenderer(new IChoiceRenderer<Boolean>() {
			private static final long serialVersionUID = 1L;

			@Override
			public Object getDisplayValue(Boolean object) {
				if (object.equals(Boolean.TRUE)) {
					return "通过";
				}
				return "未通过";
			}

			@Override
			public String getIdValue(Boolean object, int index) {
				return String.valueOf(index);
			}

		});
		setNullValid(true);
	}
}

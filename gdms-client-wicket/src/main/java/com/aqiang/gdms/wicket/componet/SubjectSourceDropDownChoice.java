package com.aqiang.gdms.wicket.componet;

import java.util.Arrays;
import java.util.List;

import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.LoadableDetachableModel;

import com.aqiang.bsms.entities.SubjectSource;

public class SubjectSourceDropDownChoice extends DropDownChoice<String> {
	private static final long serialVersionUID = 1L;

	public SubjectSourceDropDownChoice(String id) {
		super(id);
		setChoices(new LoadableDetachableModel<List<String>>() {
			private static final long serialVersionUID = 1L;

			@Override
			protected List<String> load() {
				return Arrays.asList(SubjectSource.SCIENTIFIC_RESEARCH, SubjectSource.PRODUCTION,
						SubjectSource.TEACHING, SubjectSource.OTHERS);
			}
		});
		setChoiceRenderer(new IChoiceRenderer<String>() {
			private static final long serialVersionUID = 1L;

			@Override
			public Object getDisplayValue(String object) {
				return getString(object.toString().toUpperCase());
			}

			@Override
			public String getIdValue(String object, int index) {
				return String.valueOf(index);
			}
		});
		setNullValid(true);
	}
}

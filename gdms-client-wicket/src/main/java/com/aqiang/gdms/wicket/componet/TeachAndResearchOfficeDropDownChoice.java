package com.aqiang.gdms.wicket.componet;

import java.util.List;

import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.IChoiceRenderer;

import com.aqiang.bsms.entities.TeachAndResearchOffice;

public class TeachAndResearchOfficeDropDownChoice extends DropDownChoice<TeachAndResearchOffice> {

	private static final long serialVersionUID = 1L;

	public TeachAndResearchOfficeDropDownChoice(String id, List<TeachAndResearchOffice> teachAndResearchOffices) {
		super(id, teachAndResearchOffices);
		setChoiceRenderer(new IChoiceRenderer<TeachAndResearchOffice>() {
			private static final long serialVersionUID = 1L;

			@Override
			public Object getDisplayValue(TeachAndResearchOffice object) {
				return object.getName();
			}

			@Override
			public String getIdValue(TeachAndResearchOffice object, int index) {
				return String.valueOf(index);
			}
		});
		setNullValid(true);
	}
}

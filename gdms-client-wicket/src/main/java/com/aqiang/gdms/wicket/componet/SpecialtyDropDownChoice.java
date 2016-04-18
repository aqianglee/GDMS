package com.aqiang.gdms.wicket.componet;

import java.util.List;

import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.IChoiceRenderer;

import com.aqiang.bsms.entities.Specialty;

public class SpecialtyDropDownChoice extends DropDownChoice<Specialty> {
	private static final long serialVersionUID = 1L;

	public SpecialtyDropDownChoice(String id, List<? extends Specialty> choices) {
		super(id, choices);
		setChoiceRenderer(new IChoiceRenderer<Specialty>() {
			private static final long serialVersionUID = 1L;

			@Override
			public Object getDisplayValue(Specialty object) {
				return object.getName();
			}

			@Override
			public String getIdValue(Specialty object, int index) {
				return String.valueOf(index);
			}
		});
		setNullValid(true);
	}

}

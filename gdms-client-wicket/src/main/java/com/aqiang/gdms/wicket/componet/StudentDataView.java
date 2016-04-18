package com.aqiang.gdms.wicket.componet;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.IDataProvider;

import com.aqiang.bsms.entities.Student;

public abstract class StudentDataView extends DataView<Student> {
	private static final long serialVersionUID = 1L;

	public StudentDataView(String id, IDataProvider<Student> dataProvider) {
		super(id, dataProvider);
	}

	@Override
	protected void populateItem(final Item<Student> item) {
		item.add(new AjaxLink<Void>("student") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				click(target, item);
			}
		}.add(new Label("compellation")));
	}

	protected abstract void click(AjaxRequestTarget target, Item<Student> item);

}

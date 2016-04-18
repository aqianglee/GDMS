package com.aqiang.gdms.wicket.page;

import java.util.Iterator;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.aqiang.bsms.entities.TeachAndResearchOffice;
import com.aqiang.bsms.service.TeachAndResearchOfficeService;
import com.aqiang.gdms.wicket.componet.ConfirmLink;

public class ManageTeachAndResearchOffice extends BorderPage {

	private static final long serialVersionUID = 1L;

	private IModel<List<TeachAndResearchOffice>> teachAndResearchOfficeListModel;
	@SpringBean
	private TeachAndResearchOfficeService teachAndResearchOfficeService;

	private WebMarkupContainer teachAndResearchOfficeTab;
	private TeachAndResearchOffice teachAndResearchOffice;
	private Form<TeachAndResearchOffice> editTeachAndResearchOffice;
	private FeedbackPanel errors;

	@Override
	protected void onDetach() {
		super.onDetach();
		teachAndResearchOfficeListModel.detach();
	}

	public ManageTeachAndResearchOffice() {

		teachAndResearchOfficeListModel = new LoadableDetachableModel<List<TeachAndResearchOffice>>() {
			private static final long serialVersionUID = 1L;

			@Override
			protected List<TeachAndResearchOffice> load() {
				return teachAndResearchOfficeService.getTeachAndResearchOfficeByCollege(college);
			}
		};

		add(createReturnLink());
		add(new AjaxLink<Void>("addTeachAndResearchOffice") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				teachAndResearchOffice = new TeachAndResearchOffice();
				teachAndResearchOffice.setCollege(college);
				target.appendJavaScript("$('#editTeachAndResearchOffice').modal()");
				target.add(editTeachAndResearchOffice);
			}
		});

		teachAndResearchOfficeTab = new WebMarkupContainer("teachAndResearchOfficeTab");
		teachAndResearchOfficeTab.setOutputMarkupId(true);
		add(teachAndResearchOfficeTab);
		IDataProvider<TeachAndResearchOffice> teachAndResearchOfficeDataProvider = new SortableDataProvider<TeachAndResearchOffice, Integer>() {
			private static final long serialVersionUID = 1L;

			@Override
			public Iterator<? extends TeachAndResearchOffice> iterator(long first, long count) {
				return teachAndResearchOfficeListModel.getObject().iterator();
			}

			@Override
			public long size() {
				return teachAndResearchOfficeListModel.getObject().size();
			}

			@Override
			public IModel<TeachAndResearchOffice> model(TeachAndResearchOffice teachAndResearchOffice) {
				return new CompoundPropertyModel<TeachAndResearchOffice>(teachAndResearchOffice);
			}
		};

		DataView<TeachAndResearchOffice> teachAndResearchOfficeItem = new DataView<TeachAndResearchOffice>("teachAndResearchOfficeItem", teachAndResearchOfficeDataProvider) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(final Item<TeachAndResearchOffice> item) {
				item.add(new Label("college.collegeName"));
				item.add(new Label("name"));
				item.add(new Label("number"));
				WebMarkupContainer operators = new WebMarkupContainer("operators");
				operators.add(new AjaxLink<Void>("edit") {
					private static final long serialVersionUID = 1L;

					@Override
					public void onClick(AjaxRequestTarget target) {
						ManageTeachAndResearchOffice.this.teachAndResearchOffice = item.getModelObject();
						target.appendJavaScript("$('#editTeachAndResearchOffice').modal()");
						target.add(editTeachAndResearchOffice);
					}
				});
				operators.add(new ConfirmLink<Void>("delete") {
					private static final long serialVersionUID = 1L;

					@Override
					protected String getMessageKey() {
						return "confirm.delete";
					}

					@Override
					public void onClick(AjaxRequestTarget target) {
						teachAndResearchOfficeService.deleteEntity(item.getModelObject().getId());
						target.add(teachAndResearchOfficeTab);
					}
				});
				item.add(operators);
			}
		};
		teachAndResearchOfficeTab.add(teachAndResearchOfficeItem);

		editTeachAndResearchOffice = new Form<TeachAndResearchOffice>("editSpecialty", new CompoundPropertyModel<TeachAndResearchOffice>(
				new PropertyModel<TeachAndResearchOffice>(this, "teachAndResearchOffice"))) {

			private static final long serialVersionUID = 1L;
			@Override
			protected void onSubmit() {
				teachAndResearchOfficeService.margeEntity(getModelObject());
			}
		};
		
		errors = new FeedbackPanel("errors");
		errors.setOutputMarkupId(true);
		add(errors);
		
		editTeachAndResearchOffice.setOutputMarkupId(true);
		add(editTeachAndResearchOffice);
		editTeachAndResearchOffice.add(new TextField<String>("name").setRequired(true));
		editTeachAndResearchOffice.add(new TextField<String>("number").setRequired(true));
		add(new AjaxSubmitLink("editTeachAndResearchOfficeSubmit", editTeachAndResearchOffice) {
			private static final long serialVersionUID = 1L;
			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				target.appendJavaScript("$('#editTeachAndResearchOffice').modal('hide');");
				target.add(teachAndResearchOfficeTab);
			}

			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
				target.add(errors);
			}
		});
	}
}

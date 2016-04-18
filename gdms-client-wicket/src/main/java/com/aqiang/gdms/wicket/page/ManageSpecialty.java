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

import com.aqiang.bsms.entities.Specialty;
import com.aqiang.bsms.service.SpecialtyService;
import com.aqiang.gdms.wicket.componet.ConfirmLink;

//TODO add test for this page
public class ManageSpecialty extends BorderPage {

	private static final long serialVersionUID = 1L;

	private IModel<List<Specialty>> specialtyListModel;
	@SpringBean
	private SpecialtyService specialtyServie;

	private WebMarkupContainer specialtyTab;
	private Specialty specialty;
	private Form<Specialty> editSpecialty;
	private FeedbackPanel errors;

	@Override
	protected void onDetach() {
		super.onDetach();
		specialtyListModel.detach();
	}

	public ManageSpecialty() {

		specialtyListModel = new LoadableDetachableModel<List<Specialty>>() {
			private static final long serialVersionUID = 1L;

			@Override
			protected List<Specialty> load() {
				return specialtyServie.getSpecialtiesByCollege(college);
			}
		};

		add(createReturnLink());
		add(new AjaxLink<Void>("addSpecialty") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				specialty = new Specialty();
				specialty.setCollege(college);
				target.appendJavaScript("$('#editSpecialty').modal()");
				target.add(editSpecialty);
			}
		});

		specialtyTab = new WebMarkupContainer("specialtyTab");
		specialtyTab.setOutputMarkupId(true);
		add(specialtyTab);
		IDataProvider<Specialty> specialtyDataProvider = new SortableDataProvider<Specialty, Integer>() {
			private static final long serialVersionUID = 1L;

			@Override
			public Iterator<? extends Specialty> iterator(long first, long count) {
				return specialtyListModel.getObject().iterator();
			}

			@Override
			public long size() {
				return specialtyListModel.getObject().size();
			}

			@Override
			public IModel<Specialty> model(Specialty object) {
				return new CompoundPropertyModel<Specialty>(object);
			}
		};

		DataView<Specialty> specialtyItem = new DataView<Specialty>(
				"specialtyItem", specialtyDataProvider) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(final Item<Specialty> item) {
				item.add(new Label("college.collegeName"));
				item.add(new Label("name"));
				item.add(new Label("num"));
				WebMarkupContainer operators = new WebMarkupContainer(
						"operators");
				operators.add(new AjaxLink<Void>("edit") {
					private static final long serialVersionUID = 1L;

					@Override
					public void onClick(AjaxRequestTarget target) {
						ManageSpecialty.this.specialty = item.getModelObject();
						target.appendJavaScript("$('#editSpecialty').modal()");
						target.add(editSpecialty);
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
						specialtyServie.deleteEntity(item.getModelObject()
								.getId());
						target.add(specialtyTab);
					}
				});
				item.add(operators);
			}
		};
		specialtyTab.add(specialtyItem);

		editSpecialty = new Form<Specialty>("editSpecialty",
				new CompoundPropertyModel<Specialty>(
						new PropertyModel<Specialty>(this, "specialty"))) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit() {
				specialtyServie.margeEntity(getModelObject());
			}
		};

		errors = new FeedbackPanel("errors");
		errors.setOutputMarkupId(true);
		add(errors);

		editSpecialty.setOutputMarkupId(true);
		add(editSpecialty);
		editSpecialty.add(new TextField<String>("name").setRequired(true));
		editSpecialty.add(new TextField<String>("num").setRequired(true));
		add(new AjaxSubmitLink("editSpecialtySubmit", editSpecialty) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				target.appendJavaScript("$('#editSpecialty').modal('hide');");
				target.add(specialtyTab);
			}

			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
				target.add(errors);
			}
		});
	}
}

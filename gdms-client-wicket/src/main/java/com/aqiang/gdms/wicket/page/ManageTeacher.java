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

import com.aqiang.bsms.entities.Teacher;
import com.aqiang.bsms.exception.UsernameHasUsedException;
import com.aqiang.bsms.service.TeacherService;
import com.aqiang.gdms.wicket.componet.ConfirmLink;
import com.aqiang.gdms.wicket.componet.JobTypeLabel;

public class ManageTeacher extends BorderPage {
	private static final long serialVersionUID = 1L;
	private WebMarkupContainer teacherTab;
	private DataView<Teacher> teacherItem;
	private IModel<List<Teacher>> teacherListModel;

	@SpringBean
	private TeacherService teacherService;
	private FeedbackPanel errors;

	@SuppressWarnings("unused")
	private Teacher teacher;
	private Form<Teacher> editTeacher;

	@Override
	protected void onDetach() {
		super.onDetach();
		teacherListModel.detach();
	}

	public ManageTeacher() {
		teacherListModel = new LoadableDetachableModel<List<Teacher>>() {
			private static final long serialVersionUID = 1L;

			@Override
			protected List<Teacher> load() {
				return teacherService.getTeachersByCollege(college);
			}
		};
		add(createReturnLink());
		teacherTab = new WebMarkupContainer("teacherTab");
		teacherTab.setOutputMarkupId(true);
		add(teacherTab);
		IDataProvider<Teacher> dataProvider = new SortableDataProvider<Teacher, Integer>() {
			private static final long serialVersionUID = 1L;

			@Override
			public Iterator<? extends Teacher> iterator(long first, long count) {
				return teacherListModel.getObject().iterator();
			}

			@Override
			public long size() {
				return teacherListModel.getObject().size();
			}

			@Override
			public IModel<Teacher> model(Teacher object) {
				return new CompoundPropertyModel<Teacher>(object);
			}
		};

		teacherItem = new DataView<Teacher>("teacherItem", dataProvider) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(final Item<Teacher> item) {
				item.add(new Label("college.collegeName"));
				item.add(new Label("compellation"));
				item.add(new Label("gender"));
				item.add(new JobTypeLabel("job"));
				item.add(new Label("phone"));
				item.add(new Label("researchArea"));

				WebMarkupContainer operators = new WebMarkupContainer("operators");
				operators.add(new AjaxLink<Void>("edit") {
					private static final long serialVersionUID = 1L;

					@Override
					public void onClick(AjaxRequestTarget target) {
						ManageTeacher.this.teacher = item.getModelObject();
						target.appendJavaScript("$('#editTeacher').modal()");
						target.add(editTeacher);
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
						teacherService.deleteEntity(item.getModelObject().getId());
						target.add(teacherTab);
					}
				});
				item.add(operators);
			}
		};
		teacherTab.add(teacherItem);

		add(new AjaxLink<Void>("addTeacher") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				ManageTeacher.this.teacher = new Teacher();
				target.appendJavaScript("$('#editTeacher').modal();");
				target.add(editTeacher);
			}
		});

		errors = new FeedbackPanel("errors");
		errors.setOutputMarkupId(true);
		add(errors);

		editTeacher = new Form<Teacher>("editTeacher", new CompoundPropertyModel<Teacher>(new PropertyModel<Teacher>(
				this, "teacher")));
		editTeacher.setOutputMarkupId(true);
		add(editTeacher);
		editTeacher.add(new TextField<String>("username").setRequired(true));
		editTeacher.add(new TextField<String>("compellation").setRequired(true));
		add(new AjaxSubmitLink("editTeacherSubmit", editTeacher) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				Teacher teacher = editTeacher.getModelObject();
				teacher.setCollege(college);
				boolean hasError = false;
				if (teacher.getId() != null) {
					teacherService.margeEntity(teacher);
				} else {
					try {
						teacherService.addNewTeacher(teacher);
					} catch (UsernameHasUsedException e) {
						error("用户名被使用！");
						target.add(errors);
						hasError = true;
					}
				}
				if (hasError == false) {
					target.add(teacherTab, errors);
					target.appendJavaScript("$('#editTeacher').modal('hide');");
				}
			}

			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
				target.add(errors);
			}
		});
	}

}

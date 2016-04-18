package com.aqiang.gdms.wicket.page;

import java.util.Iterator;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aqiang.bsms.entities.Event;
import com.aqiang.bsms.entities.Specialty;
import com.aqiang.bsms.entities.Student;
import com.aqiang.bsms.exception.UsernameHasUsedException;
import com.aqiang.bsms.service.EventService;
import com.aqiang.bsms.service.SpecialtyService;
import com.aqiang.bsms.service.StudentService;
import com.aqiang.gdms.wicket.componet.ConfirmLink;
import com.aqiang.gdms.wicket.componet.SpecialtyDropDownChoice;

public class ManageStudent extends BorderPage {
	private static final long serialVersionUID = 1L;
	private WebMarkupContainer studentTab;
	private DataView<Student> studentItem;
	private IModel<List<Student>> studentsModel;
	private FeedbackPanel errors;
	private Event currentEvent;

	protected Logger LOGGER = LoggerFactory.getLogger(ManageStudent.class);

	@SpringBean
	private StudentService studentService;
	@SpringBean
	private SpecialtyService specialtyService;
	@SpringBean
	private EventService eventService;
	protected Student student;
	private Form<Student> editStudent;

	@Override
	protected void onDetach() {
		super.onDetach();
		studentsModel.detach();
	}

	public ManageStudent() {
		studentsModel = new LoadableDetachableModel<List<Student>>() {
			private static final long serialVersionUID = 1L;

			@Override
			protected List<Student> load() {
				return studentService.getStudentsByCollegeAndCurrnetEvent(college, currentEvent);
			}
		};

		currentEvent = eventService.getCurrentEvent(college);

		add(createReturnLink());
		add(new AjaxLink<Void>("addStudent") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				Event event = eventService.getCurrentEvent(college);
				if (event != null) {
					ManageStudent.this.student = new Student();
					student.setCollege(college);
					student.setEvent(event);
					target.appendJavaScript("$('#editStudent').modal()");
					target.add(editStudent);
				} else {
					target.appendJavaScript("alert('还没有激活的毕设活动，请先创建毕设活动。');");
				}

			}
		});
		studentTab = new WebMarkupContainer("studentTab");
		studentTab.setOutputMarkupId(true);
		add(studentTab);
		IDataProvider<Student> studentsDataProvider = new SortableDataProvider<Student, Integer>() {
			private static final long serialVersionUID = 1L;

			@Override
			public Iterator<? extends Student> iterator(long first, long count) {
				return studentsModel.getObject().iterator();
			}

			@Override
			public long size() {
				return studentsModel.getObject().size();
			}

			@Override
			public IModel<Student> model(Student object) {
				return new CompoundPropertyModel<Student>(object);
			}
		};
		studentItem = new DataView<Student>("studentItem", studentsDataProvider) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(final Item<Student> item) {
				item.add(new Label("number"));
				item.add(new Label("compellation"));
				item.add(new Label("college.collegeName"));
				item.add(new Label("specialty.name"));
				item.add(new Label("event.year"));
				WebMarkupContainer operators = new WebMarkupContainer("operators");
				operators.add(new AjaxLink<Void>("edit") {
					private static final long serialVersionUID = 1L;

					@Override
					public void onClick(AjaxRequestTarget target) {
						ManageStudent.this.student = item.getModelObject();
						target.appendJavaScript("$('#editStudent').modal()");
						target.add(editStudent);
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
						studentService.deleteEntity(item.getModelObject().getId());
						target.add(studentTab);
					}
				});
				item.add(operators);
			}
		};
		studentTab.add(studentItem);
		errors = new FeedbackPanel("errors");
		errors.setOutputMarkupId(true);
		add(errors);

		editStudent = new Form<Student>("editStudent", new CompoundPropertyModel<Student>(new PropertyModel<Student>(
				this, "student")));
		editStudent.setOutputMarkupId(true);
		add(editStudent);
		List<Specialty> choices = specialtyService.getSpecialtiesByCollege(college);
		DropDownChoice<Specialty> specialtyChoice = new SpecialtyDropDownChoice("specialty", choices);
		editStudent.add(specialtyChoice.setRequired(true));
		editStudent.add(new TextField<String>("username").setRequired(true));
		editStudent.add(new TextField<String>("number").setRequired(true));
		editStudent.add(new TextField<String>("compellation").setRequired(true));
		add(new AjaxSubmitLink("editStudentSubmit", editStudent) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				Student model = editStudent.getModelObject();
				boolean hasError = false;
				if (model.getId() != null) {
					studentService.margeEntity(model);
				} else {
					try {
						studentService.addNewStudent(model);
					} catch (UsernameHasUsedException e) {
						error("用户名被使用！");
						hasError = true;
						target.add(errors);
					}
				}
				if (hasError == false) {
					target.add(studentTab, errors);
					target.appendJavaScript("$('#editStudent').modal('hide');");
				}
			}

			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
				target.add(errors);
			}
		});
	}
}

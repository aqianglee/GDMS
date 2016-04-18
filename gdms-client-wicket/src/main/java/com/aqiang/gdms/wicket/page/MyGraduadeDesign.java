package com.aqiang.gdms.wicket.page;

import java.util.Iterator;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.aqiang.bsms.entities.Group;
import com.aqiang.bsms.entities.Student;
import com.aqiang.bsms.entities.Teacher;
import com.aqiang.bsms.service.StudentService;
import com.aqiang.gdms.wicket.behavior.StudentBehavior;
import com.aqiang.gdms.wicket.behavior.TeacherBehavior;
import com.aqiang.gdms.wicket.componet.JobTypeLabel;
import com.aqiang.gdms.wicket.componet.StudentDataView;

public class MyGraduadeDesign extends BorderPage {

	private static final long serialVersionUID = 1L;
	@SpringBean
	private StudentService studentService;
	private WebMarkupContainer studentDetails;

	public MyGraduadeDesign(Group group) {
		WebMarkupContainer design = new WebMarkupContainer("myGraduadeDesign");
		add(design);
		add(createReturnLink());
		design.setDefaultModel(new CompoundPropertyModel<Group>(group));
		design.add(new Label("subject.topicChoosingWay"));
		design.add(new Label("teacher.compellation"));
		design.add(new Label("subject.tutorName"));
		final List<Student> students = studentService.getStudentsByGroup(group);
		IDataProvider<Student> studentDataProvider = new IDataProvider<Student>() {
			private static final long serialVersionUID = 1L;

			@Override
			public void detach() {
			}

			@Override
			public Iterator<? extends Student> iterator(long first, long count) {
				return students.iterator();
			}

			@Override
			public long size() {
				return students.size();
			}

			@Override
			public IModel<Student> model(Student object) {
				return new CompoundPropertyModel<Student>(object);
			}

		};

		StudentDataView studentsLinks = new StudentDataView("students",
				studentDataProvider) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void click(AjaxRequestTarget target, Item<Student> item) {
				Student student = item.getModelObject();
				studentDetails
						.setDefaultModel(new CompoundPropertyModel<Student>(
								student));
				target.add(studentDetails);
				target.appendJavaScript("$('#studentDetails').modal();");
			}
		};
		design.add(studentsLinks);
		design.add(new AjaxLink<Void>("addNewTask") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {

			}

		}.add(new TeacherBehavior(userType)));

		design.add(new AjaxLink<Void>("editTask") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {

			}

		}.add(new TeacherBehavior(userType)));

		design.add(new AjaxLink<Void>("showTask") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {

			}

		});

		design.add(new AjaxLink<Void>("downloadTask") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {

			}

		});

		design.add(new AjaxLink<Void>("uploadOpeningReport") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {

			}

		});

		design.add(new AjaxLink<Void>("changeOpeningReport") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {

			}

		});

		design.add(new AjaxLink<Void>("downloadOpeningReport") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {

			}

		});

		design.add(new AjaxLink<Void>("uploadDissertation") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {

			}

		});

		design.add(new AjaxLink<Void>("changeDissertation") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {

			}

		});

		design.add(new AjaxLink<Void>("downloadDissertation") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {

			}

		});

		design.add(new AjaxLink<Void>("manageSchedule") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				setResponsePage(new ManageSchedule((Student) signUser));
			}

		}.add(new StudentBehavior(userType)));

		// TODO manual test it
		StudentDataView studentsWeeklyReport = new StudentDataView(
				"studentsManageSchedule", studentDataProvider) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void click(AjaxRequestTarget target, Item<Student> item) {
				setResponsePage(new ManageSchedule(item.getModelObject()));
			}
		};
		design.add(studentsWeeklyReport.add(new TeacherBehavior(userType)));

		WebMarkupContainer teacherDetails = new WebMarkupContainer(
				"teacherDetails");
		teacherDetails.setDefaultModel(new CompoundPropertyModel<Teacher>(group
				.getTeacher()));
		add(teacherDetails);
		teacherDetails.add(new Label("compellation"));
		teacherDetails.add(new Label("gender"));
		teacherDetails.add(new JobTypeLabel("job"));
		teacherDetails.add(new Label("phone"));
		teacherDetails.add(new Label("email"));
		teacherDetails.add(new Label("researchArea"));

		studentDetails = new WebMarkupContainer("studentDetails");
		studentDetails.setOutputMarkupId(true);
		add(studentDetails);
		studentDetails.add(new Label("compellation"));
		studentDetails.add(new Label("gender"));
		studentDetails.add(new Label("number"));
		studentDetails.add(new Label("phone"));
		studentDetails.add(new Label("email"));
		studentDetails.add(new Label("likeArea"));
	}
}

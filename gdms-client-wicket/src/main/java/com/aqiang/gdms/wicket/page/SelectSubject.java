package com.aqiang.gdms.wicket.page;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.aqiang.bsms.entities.Student;
import com.aqiang.bsms.entities.Subject;
import com.aqiang.bsms.entities.Teacher;
import com.aqiang.bsms.service.StudentService;
import com.aqiang.bsms.service.SubjectService;
import com.aqiang.bsms.service.TeacherService;
import com.aqiang.gdms.wicket.componet.ConfirmLink;
import com.aqiang.gdms.wicket.componet.JobTypeLabel;

public class SelectSubject extends BorderPage {

	private static final long serialVersionUID = 1L;
	private WebMarkupContainer subjectsTab;
	private WebMarkupContainer selectedStudentTab;
	private IModel<List<Subject>> subjectsModel;
	@SpringBean
	private SubjectService subjectService;
	@SpringBean
	private TeacherService teacherService;
	@SpringBean
	private StudentService studentService;
	private List<Student> selectedStudents = new ArrayList<Student>();
	private WebMarkupContainer teacherDetails;

	@Override
	protected void onDetach() {
		super.onDetach();
		subjectsModel.detach();
	}

	public SelectSubject() {
		subjectsModel = new LoadableDetachableModel<List<Subject>>() {

			private static final long serialVersionUID = 1L;

			@Override
			protected List<Subject> load() {
				return subjectService.getSelectAbleSubject(college);
			}
		};
		add(createReturnLink());
		addSelectAbleSubjectList();
		addTeacherDetailsPanel();
		addSelectedStudentsList();
	}

	private void addSelectedStudentsList() {
		selectedStudentTab = new WebMarkupContainer("selectedStudentTab");
		selectedStudentTab.setOutputMarkupId(true);
		add(selectedStudentTab);
		selectedStudentTab.add(new DataView<Student>("selectedStudents", new IDataProvider<Student>() {
			private static final long serialVersionUID = 1L;

			@Override
			public void detach() {
			}

			@Override
			public Iterator<? extends Student> iterator(long first, long count) {
				return SelectSubject.this.selectedStudents.iterator();
			}

			@Override
			public long size() {
				return SelectSubject.this.selectedStudents.size();
			}

			@Override
			public IModel<Student> model(Student object) {
				return new CompoundPropertyModel<Student>(object);
			}
		}) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(Item<Student> item) {
				item.add(new Label("compellation"));
				item.add(new Label("gender"));
				item.add(new Label("specialty.name"));
				item.add(new Label("phone"));
				item.add(new Label("email"));
				item.add(new Label("likeArea"));
				String confirmed = "未选定";
				if (studentService.isConfirmByTeacher(item.getModelObject())) {
					confirmed = "选定";
				}
				item.add(new Label("confirmed", confirmed));
			}
		});
	}

	private void addTeacherDetailsPanel() {
		teacherDetails = new WebMarkupContainer("teacherDetails");
		teacherDetails.setOutputMarkupId(true);
		add(teacherDetails);
		teacherDetails.add(new Label("compellation"));
		teacherDetails.add(new Label("gender"));
		teacherDetails.add(new JobTypeLabel("job"));
		teacherDetails.add(new Label("phone"));
		teacherDetails.add(new Label("email"));
		teacherDetails.add(new Label("researchArea"));
	}

	private void addSelectAbleSubjectList() {
		subjectsTab = new WebMarkupContainer("subjectTab");
		subjectsTab.setOutputMarkupId(true);
		add(subjectsTab);

		subjectsTab.add(new DataView<Subject>("subjectItem", new SortableDataProvider<Subject, Integer>() {
			private static final long serialVersionUID = 1L;

			@Override
			public Iterator<? extends Subject> iterator(long first, long count) {
				return subjectsModel.getObject().iterator();
			}

			@Override
			public long size() {
				return subjectsModel.getObject().size();
			}

			@Override
			public IModel<Subject> model(Subject object) {
				return new CompoundPropertyModel<Subject>(object);
			}
		}) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(Item<Subject> item) {
				final Subject subject = item.getModelObject();
				item.add(new Label("topicChoosingWay"));
				item.add(new AjaxLink<Void>("showTeacherDetails") {
					private static final long serialVersionUID = 1L;

					@Override
					public void onClick(AjaxRequestTarget target) {
						String don = subject.getDon();
						Teacher teacher = teacherService.getTeacherByTeacherCompellation(don);
						if (teacher != null) {
							teacherDetails.setDefaultModel(new CompoundPropertyModel<Teacher>(teacher));
							target.appendJavaScript("$('#teacherDetails').modal();");
							target.add(teacherDetails);
						} else {
							target.appendJavaScript(String.format("没有找到姓名为%s的知道老师， 可能系统中没有收录该教师", don));
						}
					}
				}.add(new Label("don")));
				item.add(new AjaxLink<Void>("showSelectStudents") {
					private static final long serialVersionUID = 1L;

					@Override
					public void onClick(AjaxRequestTarget target) {
						SelectSubject.this.selectedStudents = subjectService.getSelectedStudents(subject);
						target.appendJavaScript("$('#selectStudents').modal();");
						target.add(selectedStudentTab);
					}

				});
				item.add(new AjaxLink<Void>("showDetails") {
					private static final long serialVersionUID = 1L;

					@Override
					public void onClick(AjaxRequestTarget target) {

					}
				});

				ConfirmLink<Void> select = new ConfirmLink<Void>("select") {
					private static final long serialVersionUID = 1L;

					@Override
					protected String getMessageKey() {
						return "confirm.select";
					}

					@Override
					public void onClick(AjaxRequestTarget target) {
						subjectService.addStudentToSelectList((Student) signUser, subject);
						target.add(subjectsTab);
					}
				};
				String selectString = "选择";
				if (studentService.hasSelected((Student) signUser, subject)) {
					selectString = "已选择";
					select.setEnabled(false);
				}
				select.add(new Label("selectPanel", selectString));
				item.add(select);
			}
		});
	}
}

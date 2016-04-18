package com.aqiang.gdms.wicket.page;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.request.handler.resource.ResourceStreamRequestHandler;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.resource.AbstractResourceStreamWriter;
import org.apache.wicket.util.resource.IResourceStream;

import com.aqiang.bsms.entities.Event;
import com.aqiang.bsms.entities.Student;
import com.aqiang.bsms.entities.Subject;
import com.aqiang.bsms.entities.SubjectApplyType;
import com.aqiang.bsms.entities.UserType;
import com.aqiang.bsms.service.DownloadService;
import com.aqiang.bsms.service.EventService;
import com.aqiang.bsms.service.ParameterService;
import com.aqiang.bsms.service.StudentService;
import com.aqiang.bsms.service.SubjectService;
import com.aqiang.gdms.wicket.behavior.ApplySubjectEnableBehavior;
import com.aqiang.gdms.wicket.componet.ConfirmLink;

public class ManageSubject extends BorderPage {

	private static final long serialVersionUID = 1L;
	private WebMarkupContainer subjectTab;
	private IModel<List<Subject>> subjectListModel;
	@SpringBean
	private SubjectService subjectService;
	@SpringBean
	private ParameterService parameterService;
	@SpringBean
	private EventService eventService;
	@SpringBean
	private DownloadService downloadService;
	@SpringBean
	private StudentService studentService;
	private Event currentEvent;
	private WebMarkupContainer selectedStudentsTab;
	private List<Student> selectedStudents = new ArrayList<Student>();
	private Subject selectedSubject;

	@Override
	protected void onDetach() {
		super.onDetach();
		subjectListModel.detach();
	}

	public ManageSubject() {
		subjectListModel = new LoadableDetachableModel<List<Subject>>() {
			private static final long serialVersionUID = 1L;

			@Override
			protected List<Subject> load() {
				return subjectService.getMyAllSubject(signUser);
			}
		};
		currentEvent = eventService.getCurrentEvent(college);

		add(createReturnLink());
		add(new Link<Void>("addSubject") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				Subject subject = new Subject();
				subject.setCollege(college);
				subject.setUser(signUser);
				subject.setBeginTime(currentEvent.getBeginDate());
				subject.setEndTime(currentEvent.getEndDate());
				if (userType.equals(UserType.TEACHER)) {
					subject.setSubjectApplyType(SubjectApplyType.SCHOOL_SUBJECT);
				}
				subject.setEvent(currentEvent);
				setResponsePage(new EditSubject(subject) {
					private static final long serialVersionUID = 1L;

					@Override
					protected void onPrev() {
						setResponsePage(new ManageSubject() {
							private static final long serialVersionUID = 1L;

							@Override
							protected void onPrev() {
								setResponsePage(Desktop.class);
							}
						});
					}
				});
			}
		}.add(new ApplySubjectEnableBehavior(currentEvent)));

		subjectTab = new WebMarkupContainer("subjectTab");
		subjectTab.setOutputMarkupId(true);
		add(subjectTab);

		IDataProvider<Subject> subjectDataProvider = new SortableDataProvider<Subject, Integer>() {
			private static final long serialVersionUID = 1L;

			@Override
			public Iterator<? extends Subject> iterator(long first, long count) {
				return subjectListModel.getObject().iterator();
			}

			@Override
			public long size() {
				return subjectListModel.getObject().size();
			}

			@Override
			public IModel<Subject> model(Subject object) {
				return new CompoundPropertyModel<Subject>(object);
			}
		};

		DataView<Subject> subjectItem = new DataView<Subject>("subjectItem", subjectDataProvider) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(final Item<Subject> item) {
				final Subject subject = item.getModelObject();
				item.add(new Label("topicChoosingWay"));
				item.add(new AjaxLink<Void>("showSelectedStudents") {
					private static final long serialVersionUID = 1L;

					@Override
					public void onClick(AjaxRequestTarget target) {
						ManageSubject.this.selectedStudents = subjectService.getSelectedStudents(subject);
						ManageSubject.this.selectedSubject = subject;
						target.appendJavaScript("$('#selectedStudents').modal();");
						target.add(selectedStudentsTab);
					}
				});
				item.add(new AjaxLink<Void>("showDetails") {
					private static final long serialVersionUID = 1L;

					@Override
					public void onClick(AjaxRequestTarget target) {

					}
				});
				item.add(new Link<Void>("downloadFile") {
					private static final long serialVersionUID = 1L;

					@Override
					public void onClick() {
						IResourceStream resourceStream = new AbstractResourceStreamWriter() {
							private static final long serialVersionUID = 1L;

							@Override
							public void write(OutputStream output) throws IOException {
								downloadService.downloadSubject(item.getModelObject(), output);
							}
						};

						getRequestCycle().scheduleRequestHandlerAfterCurrent(
								new ResourceStreamRequestHandler(resourceStream, String.format("mySubject-%s.doc",
										signUser.getUsername())));
					}

				});

				WebMarkupContainer operators = new WebMarkupContainer("operators");
				operators.add(new AjaxLink<Void>("edit") {
					private static final long serialVersionUID = 1L;

					@Override
					public void onClick(AjaxRequestTarget target) {
						Subject subject = item.getModelObject();
						setResponsePage(new EditSubject(subject) {
							private static final long serialVersionUID = 1L;

							@Override
							protected void onPrev() {
								setResponsePage(new ManageSubject() {
									private static final long serialVersionUID = 1L;

									@Override
									protected void onPrev() {
										setResponsePage(Desktop.class);
									}
								});
							}
						});
					}
				});
				operators.add(new ApplySubjectEnableBehavior(currentEvent));
				operators.add(new ConfirmLink<Void>("delete") {
					private static final long serialVersionUID = 1L;

					@Override
					protected String getMessageKey() {
						return "confirm.delete";
					}

					@Override
					public void onClick(AjaxRequestTarget target) {
						subjectService.deleteSubject(item.getModelObject());
						target.add(subjectTab);
					}
				});
				item.add(operators);
			}
		};
		subjectTab.add(subjectItem);

		selectedStudentsTab = new WebMarkupContainer("selectedStudentsTab");
		selectedStudentsTab.setOutputMarkupId(true);
		add(selectedStudentsTab);
		selectedStudentsTab.add(new DataView<Student>("selectedStudents", new IDataProvider<Student>() {
			private static final long serialVersionUID = 1L;

			@Override
			public void detach() {
			}

			@Override
			public Iterator<? extends Student> iterator(long first, long count) {
				return ManageSubject.this.selectedStudents.iterator();
			}

			@Override
			public long size() {
				return ManageSubject.this.selectedStudents.size();
			}

			@Override
			public IModel<Student> model(Student object) {
				return new CompoundPropertyModel<Student>(object);
			}
		}) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(final Item<Student> item) {
				item.add(new Label("compellation"));
				item.add(new Label("number"));
				item.add(new Label("gender"));
				item.add(new Label("specialty.name"));
				item.add(new Label("phone"));
				item.add(new Label("email"));
				item.add(new Label("likeArea"));
				ConfirmLink<Void> confirmLink = new ConfirmLink<Void>("confirm") {
					private static final long serialVersionUID = 1L;

					@Override
					public void onClick(AjaxRequestTarget target) {
						subjectService.confirmeStudent(selectedSubject, item.getModelObject());
						target.add(selectedStudentsTab);
					}

					@Override
					protected String getMessageKey() {
						return "confirm.confirm";
					}

					@Override
					public boolean isEnabled() {
						return !studentService.isConfirmByTeacher(item.getModelObject());
					}
				};
				String confirmed = "未选定";
				if (studentService.isConfirmByTeacher(item.getModelObject())) {
					confirmed = "选定";
				}
				confirmLink.add(new Label("confirmed", confirmed));
				item.add(confirmLink);
			}
		});
	}
}

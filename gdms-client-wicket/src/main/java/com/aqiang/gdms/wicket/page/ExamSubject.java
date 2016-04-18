package com.aqiang.gdms.wicket.page;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.OnChangeAjaxBehavior;
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
import com.aqiang.bsms.entities.Subject;
import com.aqiang.bsms.entities.WorkflowStatus;
import com.aqiang.bsms.service.DownloadService;
import com.aqiang.bsms.service.EventService;
import com.aqiang.bsms.service.SubjectService;
import com.aqiang.gdms.wicket.behavior.ApplySubjectEnableBehavior;
import com.aqiang.gdms.wicket.behavior.ApplySubjectVisibleBehavior;
import com.aqiang.gdms.wicket.behavior.SelectSubjectVisibleBehavior;
import com.aqiang.gdms.wicket.componet.CommentDropDownChoice;
import com.aqiang.gdms.wicket.componet.ConfirmLink;

public class ExamSubject extends BorderPage {
	private static final long serialVersionUID = 1L;
	private WebMarkupContainer subjectTab;
	private IModel<List<Subject>> subjectsModel;

	@SpringBean
	private SubjectService subjectService;
	@SpringBean
	private DownloadService downloadService;
	@SpringBean
	private EventService eventService;

	private Event currentEvent;

	@Override
	protected void onDetach() {
		super.onDetach();
		subjectsModel.detach();
	}

	public ExamSubject() {
		subjectsModel = new LoadableDetachableModel<List<Subject>>() {
			private static final long serialVersionUID = 1L;

			@Override
			protected List<Subject> load() {
				return subjectService.getAllSubjectByCollege(college);
			}
		};
		add(createReturnLink());
		currentEvent = eventService.getCurrentEvent(college);
		add(new ConfirmLink<Void>("passAll") {
			private static final long serialVersionUID = 1L;

			@Override
			protected String getMessageKey() {
				return "subject.passAll";
			}

			@Override
			public void onClick(AjaxRequestTarget target) {
				subjectService.passAll(subjectsModel.getObject());
				target.add(subjectTab);
			}
		}.add(new ApplySubjectVisibleBehavior(currentEvent)));

		add(new Link<Void>("downloadSubjectsSelectGuide") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				IResourceStream resourceStream = new AbstractResourceStreamWriter() {
					private static final long serialVersionUID = 1L;

					@Override
					public void write(OutputStream output) throws IOException {
						downloadService.downloadSubjectsSelectFile(college, currentEvent, output);
					}
				};
				getRequestCycle().scheduleRequestHandlerAfterCurrent(
						new ResourceStreamRequestHandler(resourceStream, String.format("subject-%s.zip",
								currentEvent.getYear())));
			}
		}.add(new ApplySubjectVisibleBehavior(currentEvent)));

		add(new ConfirmLink<Void>("endSelectSubject") {
			private static final long serialVersionUID = 1L;

			@Override
			protected String getMessageKey() {
				return "confirm.endSelectSubject";
			}

			// TODO test this method
			@Override
			public void onClick(AjaxRequestTarget target) {
				eventService.changeWorkFlowStatus(eventService.getCurrentEvent(college),
						WorkflowStatus.GUIDE_GRADUATE_DESIGN);
				target.add(ExamSubject.this);
			}
		}.add(new SelectSubjectVisibleBehavior(currentEvent)));

		add(new Link<Void>("downloadSubjectsSelectSummary") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				IResourceStream resourceStream = new AbstractResourceStreamWriter() {
					private static final long serialVersionUID = 1L;

					@Override
					public void write(OutputStream output) throws IOException {
						downloadService.downloadSubjectsSelectSummary(college, currentEvent, output);
					}
				};

				getRequestCycle().scheduleRequestHandlerAfterCurrent(
						new ResourceStreamRequestHandler(resourceStream, String.format("青海大学%s届本科生毕业论文（设计）选题汇总表.doc", currentEvent.getYear())));
			}
		}.add(new SelectSubjectVisibleBehavior(currentEvent)));

		subjectTab = new WebMarkupContainer("subjectTab");
		subjectTab.setOutputMarkupId(true);
		add(subjectTab);
		IDataProvider<Subject> subjectDataProvider = new SortableDataProvider<Subject, Integer>() {
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

		};
		DataView<Subject> subjectItem = new DataView<Subject>("subjectItem", subjectDataProvider) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(final Item<Subject> item) {
				item.add(new Label("topicChoosingWay"));
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

				item.add(new Label("user.compellation"));

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
								setResponsePage(new ExamSubject() {
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
				item.add(operators);
				CommentDropDownChoice commentDropDownChoice = new CommentDropDownChoice("commented");
				commentDropDownChoice.add(new ApplySubjectEnableBehavior(currentEvent));
				commentDropDownChoice.add(new OnChangeAjaxBehavior() {
					private static final long serialVersionUID = 1L;

					@Override
					protected void onUpdate(AjaxRequestTarget target) {
						subjectService.margeEntity(item.getModelObject());
					}
				});
				item.add(commentDropDownChoice);
			}
		};
		subjectTab.add(subjectItem);
	}
}

package com.aqiang.gdms.wicket.page;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.extensions.ajax.markup.html.form.upload.UploadProgressBar;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.aqiang.bsms.entities.File;
import com.aqiang.bsms.entities.ParameterKey;
import com.aqiang.bsms.entities.Schedule;
import com.aqiang.bsms.entities.ScheduleItem;
import com.aqiang.bsms.entities.Student;
import com.aqiang.bsms.service.FileService;
import com.aqiang.bsms.service.ParameterService;
import com.aqiang.bsms.service.ScheduleItemService;
import com.aqiang.bsms.service.ScheduleService;
import com.aqiang.gdms.wicket.componet.ConfirmLink;
import com.aqiang.gdms.wicket.componet.DateLabel;
import com.aqiang.gdms.wicket.componet.ExtensibleLabel;

public class ManageSchedule extends BorderPage {

	private static final long serialVersionUID = 1L;
	private IModel<List<ScheduleItem>> scheduleItemsModels;
	private WebMarkupContainer scheduleItemsTab;
	@SpringBean
	private ScheduleService scheduleService;
	@SpringBean
	private ScheduleItemService scheduleItemService;
	@SpringBean
	private FileService fileService;
	@SpringBean
	private ParameterService parameterService;
	private Schedule schedule;
	@SuppressWarnings("unused")
	private ScheduleItem scheduleItem;
	private FeedbackPanel editScheduleItemErroes;
	private FileUploadField fileUploadField;

	@Override
	protected void onDetach() {
		super.onDetach();
		scheduleItemsModels.detach();
	}

	public ManageSchedule(Student student) {

		schedule = scheduleService.getScheduleByStudent(student);

		scheduleItemsModels = new LoadableDetachableModel<List<ScheduleItem>>() {
			private static final long serialVersionUID = 1L;

			@Override
			protected List<ScheduleItem> load() {
				return scheduleItemService.getScheduleItemsBySchedule(schedule);
			}
		};

		add(createReturnLink());
		add(new AjaxLink<Void>("addScheduleItem") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				target.appendJavaScript("$('#addNewScheduleItem').modal();");
			}
		});
		add(new AjaxLink<Void>("downloadSubjectsSchedule") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {

			}
		});
		scheduleItemsTab = new WebMarkupContainer("scheduleItemsTab");
		scheduleItemsTab.setOutputMarkupId(true);
		add(scheduleItemsTab);

		DataView<ScheduleItem> ScheduleItems = new DataView<ScheduleItem>(
				"scheduleItems",
				new SortableDataProvider<ScheduleItem, Integer>() {
					private static final long serialVersionUID = 1L;

					@Override
					public Iterator<? extends ScheduleItem> iterator(
							long first, long count) {
						return scheduleItemsModels.getObject().iterator();
					}

					@Override
					public long size() {
						return scheduleItemsModels.getObject().size();
					}

					@Override
					public IModel<ScheduleItem> model(ScheduleItem object) {
						return new CompoundPropertyModel<ScheduleItem>(object);
					}
				}) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(final Item<ScheduleItem> item) {
				item.add(new Label("week"));
				item.add(new Label("content"));
				item.add(new DateLabel("checkedTime"));
				item.add(new ExtensibleLabel("checked") {
					private static final long serialVersionUID = 1L;

					@Override
					protected CharSequence getDisplay() {
						Boolean checked = (Boolean) getDefaultModelObject();
						if (checked == null) {
							return "";
						} else if (Boolean.TRUE.equals(checked)) {
							return "通过";
						} else {
							return "未通过";
						}
					}
				});
				Link<Void> downloadAttachmentLink = new Link<Void>(
						"downloadAttachment") {
					private static final long serialVersionUID = 1L;

					@Override
					public void onClick() {

					}
				};
				final ScheduleItem scheduleItem = item.getModelObject();
				item.add(downloadAttachmentLink);
				WebMarkupContainer editContainer = new WebMarkupContainer(
						"editContainer");
				item.add(editContainer);
				editContainer.add(new AjaxLink<Void>("edit") {
					private static final long serialVersionUID = 1L;

					@Override
					public void onClick(AjaxRequestTarget target) {

					}

				});
				editContainer.add(new ConfirmLink<Void>("delete") {
					private static final long serialVersionUID = 1L;

					@Override
					protected String getMessageKey() {
						return "confirm.delete";
					}

					@Override
					public void onClick(AjaxRequestTarget target) {
						scheduleItemService.delete(scheduleItem);
					}

				});
			}

		};
		scheduleItemsTab.add(ScheduleItems);
		editScheduleItemErroes = new FeedbackPanel("editScheduleItemErroes");
		editScheduleItemErroes.setOutputMarkupId(true);
		add(editScheduleItemErroes);
		final Form<ScheduleItem> editScheduleItem = new Form<ScheduleItem>(
				"editScheduleItem", new CompoundPropertyModel<ScheduleItem>(
						new PropertyModel<ScheduleItem>(this, "scheduleItem")));
		editScheduleItem.setOutputMarkupId(true);
		add(editScheduleItem);
		editScheduleItem.add(new TextField<String>("week"));
		editScheduleItem.add(new TextField<String>("content"));
		fileUploadField = new FileUploadField("attachment");
		editScheduleItem.add(fileUploadField);
		editScheduleItem.add(new UploadProgressBar("progress",
				editScheduleItem, fileUploadField));
		add(new AjaxSubmitLink("editScheduleItemSubmit", editScheduleItem) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				ScheduleItem item = editScheduleItem.getModelObject();
				FileUpload fileUpload = fileUploadField.getFileUpload();
				if (fileUpload != null) {
					String fileName = fileUpload.getClientFileName();
					String rootDir = parameterService.getParameterByName(
							ParameterKey.FILE_ROOT_DIR).getValue();
					String postfixName = fileService.getPostfixName(fileName);
					String filePath = "files/attachments/" + UUID.randomUUID()
							+ postfixName;
					try {
						fileUpload
								.writeTo(new java.io.File(rootDir + filePath));
						File file = new File();
						file.setFileName(fileName);
						file.setFilePath(filePath);
						fileService.saveEntitiy(file);
						scheduleItemService.addAttachment(item, file);
						scheduleItemService.margeEntity(item);
					} catch (IOException e) {
						error("上传文件错误！");
					}
				} else {
					scheduleItemService.margeEntity(item);
				}
			}
		});
	}

}

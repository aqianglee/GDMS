package com.aqiang.gdms.wicket.page;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.aqiang.bsms.entities.Task;
import com.aqiang.bsms.service.TaskService;

public class EditTask extends BorderPage {

	private static final long serialVersionUID = 1L;
	private FeedbackPanel errors;
	@SpringBean
	private TaskService taskService;

	public EditTask(Task task) {
		add(createReturnLink());
		errors = new FeedbackPanel("errors");
		errors.setOutputMarkupId(true);
		add(errors);
		final Form<Task> form = new Form<Task>("form",
				new CompoundPropertyModel<Task>(task));
		add(form);
		form.add(new TextField<String>("student.compellation"));
		form.add(new TextField<String>("student.specialty.name"));
		form.add(new TextField<String>("teacher.compellation"));
		form.add(new TextField<String>("company"));
		form.add(new TextField<String>("subject.topicChoosingWay"));
		form.add(new TextArea<String>("dissertationMainContent"));
		form.add(new TextArea<String>("mainTask"));
		form.add(new TextArea<String>("teacherComment"));
		form.add(new TextArea<String>("collegeComment"));
		add(new AjaxSubmitLink("submit", form) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> f) {
				taskService.editTask(form.getModelObject());
			}
		});
	}
}

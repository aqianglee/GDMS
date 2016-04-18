package com.aqiang.gdms.wicket.page;

import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.AjaxCallListener;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.extensions.markup.html.form.DateTextField;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.aqiang.bsms.entities.Specialty;
import com.aqiang.bsms.entities.Subject;
import com.aqiang.bsms.entities.UserType;
import com.aqiang.bsms.service.SpecialtyService;
import com.aqiang.bsms.service.SubjectService;
import com.aqiang.bsms.service.TeachAndResearchOfficeService;
import com.aqiang.gdms.wicket.componet.JobTypeDropDownChoice;
import com.aqiang.gdms.wicket.componet.SubjectApplyTypeDropDownChoice;
import com.aqiang.gdms.wicket.componet.SubjectSourceDropDownChoice;
import com.aqiang.gdms.wicket.componet.SubjectTypeDropDownChoice;
import com.aqiang.gdms.wicket.componet.TeachAndResearchOfficeDropDownChoice;

public class EditSubject extends BorderPage {
	private static final long serialVersionUID = 1L;
	@SpringBean
	private SpecialtyService specialtyService;
	@SpringBean
	private TeachAndResearchOfficeService teachAndResearchOfficeService;
	@SpringBean
	private SubjectService subjectService;
	private FeedbackPanel errors;

	public EditSubject(Subject subject) {
		add(createReturnLink());
		errors = new FeedbackPanel("errors");
		errors.setOutputMarkupId(true);
		add(errors);
		Form<Subject> form = new Form<Subject>("form", new CompoundPropertyModel<Subject>(subject));
		add(form);
		SubjectApplyTypeDropDownChoice subjectApplyTypeDropDownChoice = new SubjectApplyTypeDropDownChoice(
				"subjectApplyType");
		if (UserType.TEACHER.equals(userType)) {
			subjectApplyTypeDropDownChoice.setEnabled(false);
		}
		form.add(subjectApplyTypeDropDownChoice.setRequired(true));
		form.add(new SubjectTypeDropDownChoice("subjectType").setRequired(true));
		form.add(new SubjectSourceDropDownChoice("subjectSource").setRequired(true));
		form.add(new TextField<String>("college.collegeName").setRequired(true));
		form.add(new TeachAndResearchOfficeDropDownChoice("teachAndResearchOffice", teachAndResearchOfficeService
				.getTeachAndResearchOfficeByCollege(college)).setRequired(true));
		form.add(new TextField<String>("topicChoosingWay").setRequired(true));
		form.add(new TextField<String>("don").setRequired(true));
		form.add(new JobTypeDropDownChoice("donJob").setRequired(true));
		form.add(new TextField<String>("tutorName"));
		form.add(new JobTypeDropDownChoice("tutorJob"));
		form.add(new DateTextField("beginTime", "yyyy-MM-dd").setRequired(true));
		form.add(new DateTextField("endTime", "yyyy-MM-dd").setRequired(true));
		List<? extends Specialty> choices = specialtyService.getSpecialtiesByCollege(college);
		DropDownChoice<Specialty> dropDownChoice = new DropDownChoice<Specialty>("specialty", choices);
		IChoiceRenderer<Specialty> renderer = new IChoiceRenderer<Specialty>() {
			private static final long serialVersionUID = 1L;

			@Override
			public String getIdValue(Specialty object, int index) {
				return String.valueOf(index);
			}

			@Override
			public Object getDisplayValue(Specialty object) {
				return object.getName();
			}
		};
		dropDownChoice.setChoiceRenderer(renderer);
		dropDownChoice.setNullValid(true);
		form.add(dropDownChoice.setRequired(true));
		form.add(new TextField<String>("guideNumber").setRequired(true));
		form.add(new TextArea<String>("taskFromAndTheoryBasis").setRequired(true));
		form.add(new TextArea<String>("content").setRequired(true));
		form.add(new TextArea<String>("workload").setRequired(true));
		form.add(new TextArea<String>("budget").setRequired(true));
		form.add(new TextArea<String>("teachAndResearchOfficeComment"));
		form.add(new TextArea<String>("collegeComment"));
		add(new AjaxSubmitLink("submit", form) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void updateAjaxAttributes(AjaxRequestAttributes attributes) {
				super.updateAjaxAttributes(attributes);
				AjaxCallListener ajaxCallListener = new AjaxCallListener();
				ajaxCallListener.onPrecondition("$('#notice').modal()");
				attributes.getAjaxCallListeners().add(ajaxCallListener);
			}

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				Subject modelObject = (Subject) form.getModelObject();
				subjectService.editSubject(modelObject);
				target.appendJavaScript("$('#notice').modal('hide')");
				setResponsePage(new ManageSubject() {
					private static final long serialVersionUID = 1L;

					@Override
					protected void onPrev() {
						setResponsePage(Desktop.class);
					}
				});
			}

			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
				target.appendJavaScript("$('#notice').modal('hide')");
				target.add(errors);
			}

		});
	}
}

package com.aqiang.gdms.wicket.page;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.aqiang.bsms.entities.Student;
import com.aqiang.bsms.entities.User;
import com.aqiang.bsms.service.SpecialtyService;
import com.aqiang.gdms.wicket.componet.SpecialtyDropDownChoice;

public class StudentAboutMe extends AboutMe {
	private static final long serialVersionUID = 1L;
	@SpringBean
	private SpecialtyService specialtyService;

	@Override
	protected Form<User> getModefyUserForm() {
		Student Student = (Student) signUser;
		Form<User> form = new Form<User>("modefyUser", new CompoundPropertyModel<User>(Student));
		form.add(new TextField<String>("username").setEnabled(false));
		form.add(new TextField<String>("compellation").setEnabled(false));
		form.add(new TextField<String>("gender").add(new EnableBehaver()));
		form.add(new TextField<String>("phone").add(new EnableBehaver()));
		form.add(new TextField<String>("email").add(new EnableBehaver()));
		form.add(new SpecialtyDropDownChoice("specialty", specialtyService.getSpecialtiesByCollege(college))
				.add(new EnableBehaver()));
		form.add(new TextField<String>("number").add(new EnableBehaver()));
		form.add(new TextField<String>("likeArea").add(new EnableBehaver()));
		return form;
	}

}

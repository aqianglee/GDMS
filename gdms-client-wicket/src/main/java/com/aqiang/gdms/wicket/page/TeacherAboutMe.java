package com.aqiang.gdms.wicket.page;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;

import com.aqiang.bsms.entities.Teacher;
import com.aqiang.bsms.entities.User;
import com.aqiang.gdms.wicket.componet.JobTypeDropDownChoice;

public class TeacherAboutMe extends AboutMe {

	private static final long serialVersionUID = 1L;

	@Override
	protected Form<User> getModefyUserForm() {
		Teacher teacher = (Teacher) signUser;
		Form<User> form = new Form<User>("modefyUser", new CompoundPropertyModel<User>(teacher));
		form.add(new TextField<String>("username").setEnabled(false));
		form.add(new TextField<String>("compellation").setEnabled(false));
		form.add(new TextField<String>("gender").add(new EnableBehaver()));
		form.add(new TextField<String>("phone").add(new EnableBehaver()));
		form.add(new TextField<String>("email").add(new EnableBehaver()));
		form.add(new TextField<String>("researchArea").add(new EnableBehaver()));
		form.add(new JobTypeDropDownChoice("job").add(new EnableBehaver()));
		return form;
	}

}

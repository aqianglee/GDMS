package com.aqiang.gdms.wicket.page;



import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.aqiang.bsms.entities.CollegeManager;
import com.aqiang.bsms.entities.Student;
import com.aqiang.bsms.entities.SystemManager;
import com.aqiang.bsms.entities.Teacher;
import com.aqiang.bsms.entities.User;
import com.aqiang.bsms.entities.UserType;
import com.aqiang.bsms.service.LoginService;
import com.aqiang.gdms.wicket.MySession;

public class LoginPage extends WebPage {
	private static final long serialVersionUID = 1L;
	
	@SpringBean
	private LoginService loginService;
	private FeedbackPanel errors;
	public LoginPage() {
		errors = new FeedbackPanel("errors");
		errors.setOutputMarkupId(true);
		add(errors);
		
		Form<User> form = new Form<User>("form", new CompoundPropertyModel<User>(new User()));
		add(form);
		form.add(new TextField<String>("username"));
		form.add(new PasswordTextField("password"));
		form.add(new AjaxSubmitLink("submit"){
			private static final long serialVersionUID = 1L;
			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				User user = loginService.validateUser((User)form.getModelObject());
				if(user != null) {
					MySession session = (MySession) getSession();
					session.setUser(user);
					if(user instanceof Student) {
						session.setCollege(((Student)user).getCollege());
						session.setUserType(UserType.STUDENT);
					}
					if(user instanceof Teacher) {
						session.setCollege(((Teacher)user).getCollege());
						session.setUserType(UserType.TEACHER);
					}
					if(user instanceof CollegeManager) {
						session.setCollege(((CollegeManager)user).getCollege());
						session.setUserType(UserType.COLLEGE_MANAGER);
					}
					if(user instanceof SystemManager) {
						session.setUserType(UserType.SYSTEM_MANAGER);
					}
					setResponsePage(Desktop.class);
				} else {
					FeedbackMessage message = new FeedbackMessage(form, "用户名或密码错误！", FLAG_INITIALIZED);
					getSession().getFeedbackMessages().add(message);
					target.add(errors);
				}
			}
		});
	}
}

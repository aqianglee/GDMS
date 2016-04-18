package com.aqiang.gdms.wicket.page;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.aqiang.bsms.entities.User;
import com.aqiang.bsms.service.UserService;

public class AboutMe extends BorderPage {
	private static final long serialVersionUID = 1L;

	private String password;
	private String newPassword;
	private String confirmPassword;
	private FeedbackPanel errors;

	private boolean modefy = false;

	@SpringBean
	private UserService userService;
	private Form<User> modefyUser;

	public AboutMe() {
		add(createReturnLink());
		final WebMarkupContainer showDetails = new WebMarkupContainer(
				"showDetails");
		showDetails.setOutputMarkupId(true);
		add(showDetails);
		modefyUser = getModefyUserForm();
		new Form<User>("modefyUser", new CompoundPropertyModel<User>(signUser));
		showDetails.add(modefyUser);

		showDetails.add(new AjaxLink<Void>("modefyPassword") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				target.appendJavaScript("$('#modefyPasswordPanel').modal()");
			}

			@Override
			public boolean isVisible() {
				return !modefy;
			}
		});
		showDetails.add(new AjaxSubmitLink("submitModefy", modefyUser) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				userService.margeEntity(modefyUser.getModelObject());
				modefy = false;
				target.add(showDetails);
			}

			@Override
			public boolean isVisible() {
				return modefy;
			}
		});

		showDetails.add(new AjaxLink<Void>("cancel") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				modefy = false;
				target.add(showDetails);
			}

			@Override
			public boolean isVisible() {
				return modefy;
			}
		});

		showDetails.add(new AjaxLink<Void>("modefyUserDetails") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				modefy = true;
				target.add(showDetails);
			}

			@Override
			public boolean isVisible() {
				return !modefy;
			}
		});

		errors = new FeedbackPanel("errors");
		errors.setOutputMarkupId(true);
		add(errors);
		final Form<AboutMe> modefyPasswordForm = new Form<AboutMe>(
				"modefyPasswordForm", new CompoundPropertyModel<AboutMe>(this));
		add(modefyPasswordForm);
		modefyPasswordForm.add(new PasswordTextField("password"));
		modefyPasswordForm.add(new PasswordTextField("newPassword"));
		modefyPasswordForm.add(new PasswordTextField("confirmPassword"));

		add(new AjaxSubmitLink("submit", modefyPasswordForm) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				AboutMe aboutMe = modefyPasswordForm.getModelObject();
				if (!aboutMe.getNewPassword().equals(
						aboutMe.getConfirmPassword())) {
					target.appendJavaScript("alert('两次输入的密码不一致！')");
					return;
				}
				if (aboutMe.getNewPassword().equals(aboutMe.getPassword())) {
					target.appendJavaScript("alert('新密码和旧密码相同')");
					return;
				}
				boolean success = userService.modefyPassword(signUser,
						aboutMe.getPassword(), aboutMe.getNewPassword());
				if (!success) {
					target.appendJavaScript("alert('密码输入错误！')");
					return;
				} else {
					target.appendJavaScript("$('#modefyPasswordPanel').modal('hide')");
				}
			}
		});

	}

	protected Form<User> getModefyUserForm() {
		Form<User> form = new Form<User>("modefyUser",
				new CompoundPropertyModel<User>(signUser));
		form.add(new TextField<String>("username").setEnabled(false));
		form.add(new TextField<String>("compellation").setEnabled(false));
		form.add(new TextField<String>("gender").add(new EnableBehaver()));
		form.add(new TextField<String>("phone").add(new EnableBehaver()));
		form.add(new TextField<String>("email").add(new EnableBehaver()));
		return form;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	class EnableBehaver extends Behavior {

		private static final long serialVersionUID = 1L;

		@Override
		public void onConfigure(Component component) {
			component.setEnabled(modefy);
		}

	}

}

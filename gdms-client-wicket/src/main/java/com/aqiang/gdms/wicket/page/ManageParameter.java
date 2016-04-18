package com.aqiang.gdms.wicket.page;

import java.util.Map;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.aqiang.bsms.entities.ParameterKey;
import com.aqiang.bsms.service.ParameterService;

public class ManageParameter extends BorderPage {
	private static final long serialVersionUID = 1L;
	private Map<String, String> parameters;
	@SpringBean
	private ParameterService parameterService;
	private FeedbackPanel errors;

	private void getParameters() {
		parameters = parameterService.getParameters();
	}

	public ManageParameter() {
		add(createReturnLink());
		getParameters();
		Form<Void> form = new Form<Void>("form");
		add(form);
		errors = new FeedbackPanel("errors");
		errors.setOutputMarkupId(true);
		add(errors);
		form.add(createTextField(ParameterKey.FILE_ROOT_DIR, "fileRootDir"));
		form.add(new AjaxSubmitLink("submit") {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				parameterService.updateParameters(parameters);
				target.appendJavaScript(String.format("alert('更新系统参数成功！')"));
			}
		});
	}

	private TextField<String> createTextField(final String key, String name) {
		return new TextField<String>(name, createModel(key));
	}

	private IModel<String> createModel(final String key) {
		return new IModel<String>() {
			private static final long serialVersionUID = 1L;

			@Override
			public void detach() {
			}

			@Override
			public String getObject() {
				if (parameters.containsKey(key)) {
					return parameters.get(key);
				}
				return null;
			}

			@Override
			public void setObject(String object) {
				parameters.put(key, object);
			}
		};
	}
}

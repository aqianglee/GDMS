package com.aqiang.gdms.wicket.page;

import java.util.Iterator;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.aqiang.bsms.entities.College;
import com.aqiang.bsms.entities.CollegeManager;
import com.aqiang.bsms.exception.UsernameHasUsedException;
import com.aqiang.bsms.service.CollegeService;
import com.aqiang.gdms.wicket.componet.ConfirmLink;
import com.aqiang.gdms.wicket.componet.ExtensibleLabel;

public class ManageCollege extends BorderPage {

	private static final long serialVersionUID = 1L;
	private DataView<College> collegeItem;
	private IModel<List<College>> collegeListModel;

	@SpringBean
	private CollegeService collegeService;
	private College college;
	@SuppressWarnings("unused")
	private CollegeManager collegeManager;
	private WebMarkupContainer collegeTab;
	private Form<College> editCollegeForm;
	private Form<CollegeManager> editCollegeManagerForm;
	private FeedbackPanel editCollegeFormErroes;
	private FeedbackPanel editCollegeManagerFormErroes;

	@Override
	protected void onDetach() {
		super.onDetach();
		collegeListModel.detach();
	}

	public ManageCollege() {
		addListCollegeTable();
		addManageCollegePanel();
		addManageCollegeManagerPanel();
	}

	private void addManageCollegePanel() {

		add(createReturnLink());

		editCollegeFormErroes = new FeedbackPanel("editCollegeFormErroes");
		editCollegeFormErroes.setOutputMarkupId(true);
		add(editCollegeFormErroes);
		editCollegeForm = new Form<College>("editCollege", new CompoundPropertyModel<College>(
				new PropertyModel<College>(this, "college"))) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit() {
				College c = getModelObject();
				if (c.getId() != null) {
					collegeService.margeEntity(c);
				} else {
					collegeService.createCollege(c);
				}
				ManageCollege.this.college = null;
			}

		};
		editCollegeForm.setOutputMarkupId(true);
		add(editCollegeForm);
		editCollegeForm.add(new TextField<String>("collegeName").setRequired(true));
		editCollegeForm.add(new TextField<String>("simpleName").setRequired(true));
		editCollegeForm.add(new TextField<String>("number").setRequired(true));
		editCollegeForm.add(new TextField<String>("description"));
		add(new AjaxSubmitLink("editCollegeSubmit", editCollegeForm) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				target.appendJavaScript("$('#editCollege').modal('hide');");
				target.add(collegeTab, editCollegeFormErroes);
			}

			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
				target.add(editCollegeFormErroes);
			}
		});
	}

	private void addManageCollegeManagerPanel() {

		editCollegeManagerFormErroes = new FeedbackPanel("editCollegeManagerFormErroes");
		editCollegeManagerFormErroes.setOutputMarkupId(true);
		add(editCollegeManagerFormErroes);
		editCollegeManagerForm = new Form<CollegeManager>("editCollegeManagerForm",
				new CompoundPropertyModel<CollegeManager>(new PropertyModel<CollegeManager>(this, "collegeManager"))) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit() {
				try {
					collegeService.changeCollegeManager(ManageCollege.this.college, getModelObject());
				} catch (UsernameHasUsedException e) {
					error("用户名已经被使用！");
				}
			}
		};
		editCollegeManagerForm.setOutputMarkupId(true);
		add(editCollegeManagerForm);
		editCollegeManagerForm.add(new TextField<String>("username").setRequired(true));
		editCollegeManagerForm.add(new TextField<String>("compellation").setRequired(true));
		add(new AjaxSubmitLink("editCollegeManagerSubmit", editCollegeManagerForm) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				target.appendJavaScript("$('#editCollegeManager').modal('hide');");
				target.add(collegeTab, editCollegeManagerFormErroes);
			}

			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
				target.add(editCollegeManagerFormErroes);
			}
		});

	}

	private void addListCollegeTable() {
		add(new AjaxLink<Void>("addCollege") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				college = new College();
				target.appendJavaScript("$('#editCollege').modal()");
				target.add(editCollegeForm);
			}
		});

		collegeListModel = new LoadableDetachableModel<List<College>>() {
			private static final long serialVersionUID = 1L;

			@Override
			protected List<College> load() {
				return collegeService.getAllColleges();
			}
		};

		IDataProvider<College> dataProvider = new SortableDataProvider<College, Integer>() {
			private static final long serialVersionUID = 1L;

			@Override
			public long size() {
				return (long) collegeListModel.getObject().size();
			}

			@Override
			public IModel<College> model(College object) {
				return new CompoundPropertyModel<College>(object);
			}

			@Override
			public Iterator<? extends College> iterator(long first, long count) {
				return collegeListModel.getObject().iterator();
			}
		};
		collegeTab = new WebMarkupContainer("collegeTab");
		collegeTab.setOutputMarkupId(true);
		add(collegeTab);

		collegeItem = new DataView<College>("collegeItem", dataProvider) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(final Item<College> item) {
				item.add(new Label("collegeName"));
				item.add(new Label("simpleName"));
				item.add(new Label("number"));
				item.add(new Label("description"));
				AjaxLink<Void> managerCollegeManager = new AjaxLink<Void>("managerCollegeManager") {
					private static final long serialVersionUID = 1L;

					@Override
					public void onClick(AjaxRequestTarget target) {
						ManageCollege.this.collegeManager = item.getModelObject().getCollegeManager() == null ? new CollegeManager()
								: item.getModelObject().getCollegeManager();
						ManageCollege.this.college = item.getModelObject();
						target.appendJavaScript("$('#editCollegeManager').modal()");
						target.add(editCollegeManagerForm);
					}

				};
				managerCollegeManager.add(new ExtensibleLabel("collegeManager.compellation") {
					private static final long serialVersionUID = 1L;

					@Override
					protected CharSequence getDisplay() {
						return getDefaultModelObject() == null ? "设置" : getDefaultModelObject().toString();
					}

				});
				item.add(managerCollegeManager);

				WebMarkupContainer operators = new WebMarkupContainer("operators");
				operators.add(new AjaxLink<Void>("edit") {
					private static final long serialVersionUID = 1L;

					@Override
					public void onClick(AjaxRequestTarget target) {
						ManageCollege.this.college = item.getModelObject();
						target.appendJavaScript("$('#editCollege').modal()");
						target.add(editCollegeForm);
					}
				});
				operators.add(new ConfirmLink<Void>("delete") {
					private static final long serialVersionUID = 1L;

					@Override
					protected String getMessageKey() {
						return "confirm.delete";
					}

					@Override
					public void onClick(AjaxRequestTarget target) {
						collegeService.deleteEntity(item.getModelObject().getId());
						target.add(collegeTab);
					}
				});
				item.add(operators);
			}
		};
		collegeTab.add(collegeItem);
	}

}

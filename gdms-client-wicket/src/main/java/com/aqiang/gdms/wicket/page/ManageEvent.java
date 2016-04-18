package com.aqiang.gdms.wicket.page;

import java.util.Iterator;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.extensions.markup.html.form.DateTextField;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.aqiang.bsms.entities.Event;
import com.aqiang.bsms.service.EventService;
import com.aqiang.gdms.wicket.componet.ConfirmLink;
import com.aqiang.gdms.wicket.componet.DateLabel;
import com.aqiang.gdms.wicket.componet.ExtensibleLabel;
import com.aqiang.gdms.wicket.componet.WorkFlowStatusLabel;

public class ManageEvent extends BorderPage {
	private static final long serialVersionUID = 1L;
	private WebMarkupContainer displayCurrentEventPanel;
	private WebMarkupContainer addEventFormPanel;
	private IModel<Event> currentEvent;
	private IModel<List<Event>> eventListModel;

	@SpringBean
	private EventService eventService;
	private Form<Event> addEventForm;
	private WebMarkupContainer eventTab;

	@Override
	protected void detachModel() {
		super.detachModel();
		eventListModel.detach();
		currentEvent.detach();
	}

	public ManageEvent() {

		currentEvent = new LoadableDetachableModel<Event>() {
			private static final long serialVersionUID = 1L;

			@Override
			protected Event load() {
				return eventService.getCurrentEvent(college);
			}
		};
		
		eventListModel = new LoadableDetachableModel<List<Event>>() {
			private static final long serialVersionUID = 1L;

			@Override
			protected List<Event> load() {
				return eventService.getEvents(college);
			}
		};

		addEventFormPanel();
		displayCurrentEventPanel();
		addEventListPanel();

	}

	private void addEventListPanel() {
		IDataProvider<Event> eventDataProvider = new SortableDataProvider<Event, Integer>() {
			private static final long serialVersionUID = 1L;

			@Override
			public Iterator<? extends Event> iterator(long first, long count) {
				return eventListModel.getObject().iterator();
			}

			@Override
			public long size() {
				return eventListModel.getObject().size();
			}

			@Override
			public IModel<Event> model(Event object) {
				return new CompoundPropertyModel<Event>(object);
			}

		};
		eventTab = new WebMarkupContainer("eventTab");
		eventTab.setOutputMarkupId(true);
		add(eventTab);
		DataView<Event> eventItem = new DataView<Event>("eventItem", eventDataProvider) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(final Item<Event> item) {
				item.add(new Label("college.collegeName"));
				item.add(new Label("year"));
				item.add(new DateLabel("beginDate"));
				item.add(new DateLabel("endDate"));
				ExtensibleLabel active = new ExtensibleLabel("active") {
					private static final long serialVersionUID = 1L;

					@Override
					protected CharSequence getDisplay() {
						Boolean active = (Boolean) getDefaultModelObject();
						if (active == null) {
							return "";
						}
						if (active) {
							return "已激活";
						}
						return "未激活";
					}
				};

				ConfirmLink<Void> changeToActive = new ConfirmLink<Void>("changeToActive") {
					private static final long serialVersionUID = 1L;

					@Override
					protected String getMessageKey() {
						return "confirm.changeToActive";
					}

					@Override
					public void onClick(AjaxRequestTarget target) {
						eventService.changeToActive(item.getModelObject());
						target.add(eventTab, addEventFormPanel, displayCurrentEventPanel);
					}

					@Override
					public boolean isEnabled() {
						return !item.getModelObject().getActive();
					}

				};
				changeToActive.add(active);
				item.add(changeToActive);
				item.add(new WorkFlowStatusLabel("workFlowStatus"));
				item.add(new AjaxLink<Void>("delete") {
					private static final long serialVersionUID = 1L;

					@Override
					public void onClick(AjaxRequestTarget target) {

					}
				});
			}
		};
		eventTab.add(eventItem);
	}

	private void displayCurrentEventPanel() {
		displayCurrentEventPanel = new WebMarkupContainer("displayCurrentEventPanel") {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isVisible() {
				return currentEvent.getObject() != null;
			}
		};
		displayCurrentEventPanel.setOutputMarkupId(true);
		add(displayCurrentEventPanel);
		displayCurrentEventPanel.setDefaultModel(new CompoundPropertyModel<Event>(currentEvent));
		displayCurrentEventPanel.add(new Label("college.collegeName"));
		displayCurrentEventPanel.add(new Label("year"));
		displayCurrentEventPanel.add(new DateLabel("beginDate"));
		displayCurrentEventPanel.add(new DateLabel("endDate"));
		ExtensibleLabel active = new ExtensibleLabel("active") {
			private static final long serialVersionUID = 1L;

			@Override
			protected CharSequence getDisplay() {
				Boolean active = (Boolean) getDefaultModelObject();
				if (active == null) {
					return "";
				}
				if (active) {
					return "已激活";
				}
				return "未激活";
			}
		};
		displayCurrentEventPanel.add(active);
		displayCurrentEventPanel.add(new WorkFlowStatusLabel("workFlowStatus"));
	}

	private void addEventFormPanel() {
		add(createReturnLink());
		addEventFormPanel = new WebMarkupContainer("addEventFormPanel") {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isVisible() {
				return currentEvent.getObject() == null;
			}
		};
		addEventFormPanel.setOutputMarkupId(true);
		add(addEventFormPanel);
		addEventForm = new Form<Event>("form", new CompoundPropertyModel<Event>(new Event()));
		addEventFormPanel.add(addEventForm);
		addEventForm.add(new TextField<String>("year"));
		addEventForm.add(new DateTextField("beginDate", "yyyy-MM-dd").setRequired(true));
		addEventForm.add(new DateTextField("endDate", "yyyy-MM-dd").setRequired(true));
		addEventForm.add(new AjaxSubmitLink("addNewEvent") {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				Event newEvent = addEventForm.getModelObject();
				newEvent.setCollege(college);
				if (eventService.createNewEvent(newEvent)) {
					target.appendJavaScript("alert('添加成功！')");
					target.add(eventTab, addEventFormPanel);
				} else {
					target.appendJavaScript("alert('年份重复，添加失败！')");
				}
			}
		});
	}
}

package com.aqiang.gdms.wicket.page;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.aqiang.bsms.entities.Event;
import com.aqiang.bsms.entities.Group;
import com.aqiang.bsms.entities.Student;
import com.aqiang.bsms.entities.UserType;
import com.aqiang.bsms.entities.WorkflowStatus;
import com.aqiang.bsms.service.EventService;
import com.aqiang.bsms.service.GroupService;
import com.aqiang.gdms.wicket.behavior.ApplySubjectVisibleBehavior;
import com.aqiang.gdms.wicket.behavior.BeginVisibleBehavior;
import com.aqiang.gdms.wicket.behavior.CollegeManagerBehavior;
import com.aqiang.gdms.wicket.behavior.GuideGraduateDesignVisibleBehavior;
import com.aqiang.gdms.wicket.behavior.SelectSubjectVisibleBehavior;
import com.aqiang.gdms.wicket.behavior.StudentBehavior;
import com.aqiang.gdms.wicket.behavior.SystemManagerBehavior;
import com.aqiang.gdms.wicket.behavior.TeacherBehavior;
import com.aqiang.gdms.wicket.componet.ConfirmLink;

public class Desktop extends BorderPage {
	private static final long serialVersionUID = 1L;

	@SpringBean
	private EventService eventService;
	@SpringBean
	private GroupService groupService;

	private IModel<Event> currentEvent;

	private Group group;

	@Override
	protected void onDetach() {
		super.onDetach();
		currentEvent.detach();
	}

	public Desktop() {

		currentEvent = new LoadableDetachableModel<Event>() {
			private static final long serialVersionUID = 1L;

			@Override
			protected Event load() {
				return eventService.getCurrentEvent(college);
			}
		};

		WebMarkupContainer menuItem1 = new WebMarkupContainer("menuItem1");
		WebMarkupContainer menuItem2 = new WebMarkupContainer("menuItem2");
		WebMarkupContainer menuItem3 = new WebMarkupContainer("menuItem3");
		WebMarkupContainer menuItem4 = new WebMarkupContainer("menuItem4");
		WebMarkupContainer menuItem5 = new WebMarkupContainer("menuItem5");
		WebMarkupContainer menuItem6 = new WebMarkupContainer("menuItem6");
		WebMarkupContainer menuItem7 = new WebMarkupContainer("menuItem7");
		WebMarkupContainer menuItem8 = new WebMarkupContainer("menuItem8");
		WebMarkupContainer menuItem9 = new WebMarkupContainer("menuItem9");
		WebMarkupContainer menuItem10 = new WebMarkupContainer("menuItem10");
		WebMarkupContainer menuItem11 = new WebMarkupContainer("menuItem11");
		WebMarkupContainer menuItem12 = new WebMarkupContainer("menuItem12");
		WebMarkupContainer menuItem13 = new WebMarkupContainer("menuItem13");
		WebMarkupContainer menuItem14 = new WebMarkupContainer("menuItem14");
		WebMarkupContainer menuItem15 = new WebMarkupContainer("menuItem15");
		WebMarkupContainer menuItem16 = new WebMarkupContainer("menuItem16");
		WebMarkupContainer menuItem20 = new WebMarkupContainer("menuItem20");
		WebMarkupContainer menuItem21 = new WebMarkupContainer("menuItem21");

		menuItem2.add(new SystemManagerBehavior(userType));
		menuItem3.add(new SystemManagerBehavior(userType));
		menuItem4.add(new CollegeManagerBehavior(userType));
		menuItem5.add(new CollegeManagerBehavior(userType));
		menuItem6.add(new CollegeManagerBehavior(userType),
				new BeginVisibleBehavior(currentEvent.getObject()));
		menuItem7.add(new TeacherBehavior(userType));
		menuItem8.add(new CollegeManagerBehavior(userType));
		menuItem9.add(new CollegeManagerBehavior(userType),
				new ApplySubjectVisibleBehavior(currentEvent.getObject()));
		menuItem10.add(new StudentBehavior(userType),
				new SelectSubjectVisibleBehavior(currentEvent.getObject()));
		menuItem11.add(new StudentBehavior(userType),
				new ApplySubjectVisibleBehavior(currentEvent.getObject()));
		menuItem12
				.add(new StudentBehavior(userType),
						new GuideGraduateDesignVisibleBehavior(currentEvent
								.getObject()));
		menuItem13
				.add(new TeacherBehavior(userType),
						new GuideGraduateDesignVisibleBehavior(currentEvent
								.getObject()));
		menuItem14
				.add(new StudentBehavior(userType),
						new GuideGraduateDesignVisibleBehavior(currentEvent
								.getObject()));
		menuItem15
				.add(new CollegeManagerBehavior(userType),
						new GuideGraduateDesignVisibleBehavior(currentEvent
								.getObject()));
		menuItem16
				.add(new CollegeManagerBehavior(userType),
						new GuideGraduateDesignVisibleBehavior(currentEvent
								.getObject()));
		menuItem20.add(new CollegeManagerBehavior(userType));

		add(menuItem1);
		add(menuItem2);
		add(menuItem3);
		add(menuItem4);
		add(menuItem5);
		add(menuItem6);
		add(menuItem7);
		add(menuItem8);
		add(menuItem9);
		add(menuItem10);
		add(menuItem11);
		add(menuItem12);
		add(menuItem13);
		add(menuItem14);
		add(menuItem15);
		add(menuItem16);
		add(menuItem20);
		add(menuItem21);

		menuItem1.add(new Link<Void>("me") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				if (userType.equals(UserType.STUDENT)) {
					setResponsePage(new StudentAboutMe() {
						private static final long serialVersionUID = 1L;

						@Override
						protected void onPrev() {
							setResponsePage(Desktop.class);
						}
					});
				} else if (userType.equals(UserType.TEACHER)) {
					setResponsePage(new TeacherAboutMe() {
						private static final long serialVersionUID = 1L;

						@Override
						protected void onPrev() {
							setResponsePage(Desktop.class);
						}
					});
				} else {
					setResponsePage(new ManagerAboutMe() {
						private static final long serialVersionUID = 1L;

						@Override
						protected void onPrev() {
							setResponsePage(Desktop.class);
						}
					});
				}
			}
		});

		menuItem2.add(new Link<Void>("manageCollege") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				setResponsePage(new ManageCollege() {
					private static final long serialVersionUID = 1L;

					@Override
					protected void onPrev() {
						setResponsePage(Desktop.class);
					}
				});
			}
		});

		menuItem3.add(new Link<Void>("manageParameter") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				setResponsePage(new ManageParameter() {
					private static final long serialVersionUID = 1L;

					@Override
					protected void onPrev() {
						setResponsePage(Desktop.class);
					}
				});
			}
		});

		menuItem4.add(new AjaxLink<Void>("menuGroupLink") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				target.appendJavaScript("$('#moreMenu1').modal();");
			}

		});
		add(new Link<Void>("manageTeacher") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				setResponsePage(new ManageTeacher() {
					private static final long serialVersionUID = 1L;

					@Override
					protected void onPrev() {
						setResponsePage(Desktop.class);
					}
				});
			}

		});

		add(new Link<Void>("manageStudent") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				setResponsePage(new ManageStudent() {
					private static final long serialVersionUID = 1L;

					@Override
					protected void onPrev() {
						setResponsePage(Desktop.class);
					}
				});
			}

		});

		add(new Link<Void>("manageTeachAndResearchOffice") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				setResponsePage(new ManageTeachAndResearchOffice() {
					private static final long serialVersionUID = 1L;

					@Override
					protected void onPrev() {
						setResponsePage(Desktop.class);
					}
				});
			}

		});

		add(new Link<Void>("manageClazz") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				setResponsePage(new ManageSpecialty() {
					private static final long serialVersionUID = 1L;

					@Override
					protected void onPrev() {
						setResponsePage(Desktop.class);
					}
				});
			}
		});

		menuItem5.add(new Link<Void>("manageEvent") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				setResponsePage(new ManageEvent() {
					private static final long serialVersionUID = 1L;

					@Override
					protected void onPrev() {
						setResponsePage(Desktop.class);
					}
				});
			}
		});

		menuItem6.add(new ConfirmLink<Void>("beginApplySubject") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				eventService.changeWorkFlowStatus(currentEvent.getObject(),
						WorkflowStatus.APPLY_SUBJECT);
			}

			@Override
			protected String getMessageKey() {
				return "confirm.changeToApplySubject";
			}
		});

		menuItem7.add(new Link<Void>("applySubject") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				setResponsePage(new ManageSubject() {
					private static final long serialVersionUID = 1L;

					@Override
					protected void onPrev() {
						setResponsePage(Desktop.class);
					}
				});
			}

		});

		menuItem8.add(new Link<Void>("examSubject") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				setResponsePage(new ExamSubject() {
					private static final long serialVersionUID = 1L;

					@Override
					protected void onPrev() {
						setResponsePage(Desktop.class);
					}
				});
			}
		});

		menuItem9.add(new ConfirmLink<Void>("beginSelectSubject") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				eventService.changeWorkFlowStatus(currentEvent.getObject(),
						WorkflowStatus.SELECT_SUBJECT);
			}

			@Override
			protected String getMessageKey() {
				return "confirm.changeToSelectSubject";
			}
		});

		menuItem10.add(new Link<Void>("selectSubject") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				setResponsePage(new SelectSubject() {
					private static final long serialVersionUID = 1L;

					@Override
					protected void onPrev() {
						setResponsePage(Desktop.class);
					}
				});
			}

		});

		menuItem11.add(new Link<Void>("studentApplySubject") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				setResponsePage(new ManageSubject() {
					private static final long serialVersionUID = 1L;

					@Override
					protected void onPrev() {
						setResponsePage(Desktop.class);
					}
				});
			}

		});

		if (signUser instanceof Student) {
			group = groupService.getGroupByStudent((Student) signUser);
		}
		if (group == null) {
			menuItem12.setVisible(false);
		}
		menuItem12.add(new Link<Void>("myGraduadeDesign") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				setResponsePage(new MyGraduadeDesign(group) {
					private static final long serialVersionUID = 1L;

					@Override
					protected void onPrev() {
						setResponsePage(Desktop.class);
					}
				});
			}
		});

		menuItem13.add(new Link<Void>("guideGraduadeDesign") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				setResponsePage(new GuideGraduadeDesign() {
					private static final long serialVersionUID = 1L;

					@Override
					protected void onPrev() {
						setResponsePage(Desktop.class);
					}
				});
			}

		});

		menuItem14.add(new Link<Void>("changeSubject") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				setResponsePage(new ChangeSubject() {
					private static final long serialVersionUID = 1L;

					@Override
					protected void onPrev() {
						setResponsePage(Desktop.class);
					}
				});
			}

		});

		menuItem15.add(new Link<Void>("manageChangeSubject") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				setResponsePage(new ManageChangeSubject() {
					private static final long serialVersionUID = 1L;

					@Override
					protected void onPrev() {
						setResponsePage(Desktop.class);
					}
				});
			}

		});

		menuItem16.add(new Link<Void>("arrangeReply") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				setResponsePage(new ManageSubject() {
					private static final long serialVersionUID = 1L;

					@Override
					protected void onPrev() {
						setResponsePage(Desktop.class);
					}
				});
			}

		});

		menuItem20.add(new AjaxLink<Void>("publicNotice") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {

			}
		});

		menuItem21.add(new AjaxLink<Void>("about") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				setResponsePage(new About() {
					private static final long serialVersionUID = 1L;

					@Override
					protected void onPrev() {
						setResponsePage(Desktop.class);
					}
				});
			}
		});
	}
}

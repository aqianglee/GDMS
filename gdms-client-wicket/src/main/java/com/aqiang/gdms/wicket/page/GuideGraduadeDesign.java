package com.aqiang.gdms.wicket.page;

import java.util.Iterator;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.aqiang.bsms.entities.Group;
import com.aqiang.bsms.entities.Student;
import com.aqiang.bsms.entities.Teacher;
import com.aqiang.bsms.service.GroupService;
import com.aqiang.bsms.service.StudentService;

public class GuideGraduadeDesign extends BorderPage {
	private static final long serialVersionUID = 1L;

	private WebMarkupContainer groupsTab;
	private IModel<List<Group>> groupsModel;
	@SpringBean
	private GroupService groupService;
	@SpringBean
	private StudentService studentService;

	@Override
	protected void onDetach() {
		super.onDetach();
		groupsModel.detach();
	}

	public GuideGraduadeDesign() {
		groupsModel = new LoadableDetachableModel<List<Group>>() {

			private static final long serialVersionUID = 1L;

			@Override
			protected List<Group> load() {
				return groupService.getGroupsByTeacher((Teacher) signUser);
			}
		};

		add(createReturnLink());
		groupsTab = new WebMarkupContainer("groupsTab");
		groupsTab.setOutputMarkupId(true);
		add(groupsTab);
		groupsTab.add(new DataView<Group>("groupItem",
				new IDataProvider<Group>() {
					private static final long serialVersionUID = 1L;

					@Override
					public void detach() {

					}

					@Override
					public Iterator<? extends Group> iterator(long first,
							long count) {
						return groupsModel.getObject().iterator();
					}

					@Override
					public long size() {
						return groupsModel.getObject().size();
					}

					@Override
					public IModel<Group> model(Group object) {
						return new CompoundPropertyModel<Group>(object);
					}
				}) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(final Item<Group> item) {
				item.add(new Label("subject.topicChoosingWay"));
				item.add(new AjaxLink<Void>("details") {
					private static final long serialVersionUID = 1L;

					@Override
					public void onClick(AjaxRequestTarget target) {
						setResponsePage(new MyGraduadeDesign(item
								.getModelObject()) {
							private static final long serialVersionUID = 1L;

							protected void onPrev() {
								setResponsePage(GuideGraduadeDesign.class);
							};
						});
					}
				});
				item.add(new DataView<Student>("student",
						new IDataProvider<Student>() {
							private static final long serialVersionUID = 1L;

							@Override
							public void detach() {

							}

							@Override
							public Iterator<? extends Student> iterator(
									long first, long count) {
								return studentService.getStudentsByGroup(
										item.getModelObject()).iterator();
							}

							@Override
							public long size() {
								return studentService.getStudentsByGroup(
										item.getModelObject()).size();
							}

							@Override
							public IModel<Student> model(Student object) {
								return new CompoundPropertyModel<Student>(
										object);
							}
						}) {

					private static final long serialVersionUID = 1L;

					@Override
					protected void populateItem(Item<Student> item) {
						item.add(new Label("compellation"));
					}
				});
			}
		});
	}
}

package com.aqiang.gdms.wicket.page;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.aqiang.bsms.entities.Group;
import com.aqiang.bsms.entities.JobType;
import com.aqiang.bsms.entities.Student;
import com.aqiang.bsms.entities.Subject;
import com.aqiang.bsms.entities.Teacher;
import com.aqiang.bsms.service.GroupService;
import com.aqiang.bsms.service.StudentService;
import com.aqiang.gdms.wicket.PageTest;
import com.ttdev.wicketpagetest.MockableSpringBeanInjector;
import com.ttdev.wicketpagetest.WebPageTestContext;
import com.ttdev.wicketpagetest.WicketSeleniumDriver;

public class GuideGraduadeDesignTest extends PageTest {

	abstract class MockStudentService implements StudentService {
		@Override
		public List<Student> getStudentsByGroup(Group group) {
			List<Student> students = new ArrayList<Student>();
			if (group.getId() == 1) {
				students.add(createStudent("齐庆"));
			}
			if (group.getId() == 2) {
				students.add(createStudent("陈大文"));
				students.add(createStudent("鲁大文"));
			}
			if (group.getId() == 3) {
				students.add(createStudent("杨静波"));
				students.add(createStudent("李志强"));
				students.add(createStudent("陈杨旭"));
			}
			return students;
		}

		private Student createStudent(String compellation) {
			Student student = new Student();
			student.setCompellation(compellation);
			return student;
		}
	}

	abstract class MockGroupService implements GroupService {
		private List<Group> groups = new ArrayList<Group>();

		@Override
		public List<Group> getGroupsByTeacher(Teacher teacher) {
			if (groups.isEmpty()) {
				groups.add(createGroup("信息技术方向——基于敏捷开发的校园超市系统", "曹腾飞", "王晓英",
						"女", JobType.PROFESSOR, "123456789",
						"333666999@qq.com", "云计算"));
				groups.add(createGroup("信息技术方向——基于敏捷开发的校园超市系统2", "曹腾飞", "王晓英",
						"女", JobType.PROFESSOR, "123456789",
						"333666999@qq.com", "云计算"));
				groups.add(createGroup("信息技术方向——基于敏捷开发的校园超市系统3", "曹腾飞", "王晓英",
						"女", JobType.PROFESSOR, "123456789",
						"333666999@qq.com", "云计算"));
			}
			return groups;
		}

		private Group createGroup(String topChoosingWay, String tutorName,
				String teacherCompellation, String teacherGender,
				String teacherJobType, String teacherPhone,
				String teacherEmail, String teacherResearchArea) {
			Group group = new Group();
			group.setId(groups.size() + 1);
			Subject subject = new Subject();
			subject.setTopicChoosingWay(topChoosingWay);
			subject.setTutorName(tutorName);
			group.setSubject(subject);
			Teacher teacher = new Teacher();
			teacher.setCompellation(teacherCompellation);
			teacher.setGender(teacherGender);
			teacher.setJob(teacherJobType);
			teacher.setPhone(teacherPhone);
			teacher.setEmail(teacherEmail);
			teacher.setResearchArea(teacherResearchArea);
			group.setTeacher(teacher);
			return group;
		}
	}

	@Test
	public void testDisplayCurrentInfo() {
		MockableSpringBeanInjector.mockBean("groupService",
				factory.implementAbstractMethods(MockGroupService.class));
		MockableSpringBeanInjector.mockBean("studentService",
				factory.implementAbstractMethods(MockStudentService.class));
		WicketSeleniumDriver ws = WebPageTestContext.getWicketSelenium();
		ws.openNonBookmarkablePage(GuideGraduadeDesign.class);
		isTrue(ws.isElementPresent("//return"));
		isTrue(ws.isElementPresent("//groupsTab"));
		isEq(ws.getText("//groupItem[0]//subject.topicChoosingWay"),
				"信息技术方向——基于敏捷开发的校园超市系统");
		isEq(ws.getText("//groupItem[1]//subject.topicChoosingWay"),
				"信息技术方向——基于敏捷开发的校园超市系统2");
		isEq(ws.getText("//groupItem[2]//subject.topicChoosingWay"),
				"信息技术方向——基于敏捷开发的校园超市系统3");
	}

}

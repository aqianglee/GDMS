package com.aqiang.gdms.wicket.page;

import org.apache.wicket.Session;
import org.junit.Test;

import com.aqiang.bsms.entities.College;
import com.aqiang.bsms.entities.Event;
import com.aqiang.bsms.entities.Group;
import com.aqiang.bsms.entities.JobType;
import com.aqiang.bsms.entities.Student;
import com.aqiang.bsms.entities.Subject;
import com.aqiang.bsms.entities.Teacher;
import com.aqiang.bsms.entities.User;
import com.aqiang.bsms.entities.UserType;
import com.aqiang.bsms.entities.WorkflowStatus;
import com.aqiang.bsms.service.EventService;
import com.aqiang.bsms.service.GroupService;
import com.aqiang.gdms.wicket.PageTest;
import com.aqiang.gdms.wicket.service.SignService;
import com.ttdev.wicketpagetest.MockableSpringBeanInjector;
import com.ttdev.wicketpagetest.WebPageTestContext;
import com.ttdev.wicketpagetest.WicketSeleniumDriver;

public class DesktopTest6 extends PageTest {
	abstract class MockEventService implements EventService {
		@Override
		public Event getCurrentEvent(College college) {
			Event event = new Event();
			event.setYear("2016");
			event.setWorkFlowStatus(WorkflowStatus.BEGIN);
			return event;
		}
	}

	abstract class MockSignService implements SignService {
		@Override
		public User getSignUser(Session session) {
			Student student = new Student();
			student.setId(1);
			return student;
		}

		@Override
		public College getCollege(Session session) {
			College college = new College();
			college.setCollegeName("计算机科学与技术");
			return college;
		}

		@Override
		public String getUserType(Session session) {
			return UserType.STUDENT;
		}
	}
	
	abstract class MockGroupService implements GroupService {
		@Override
		public Group getGroupByStudent(Student student) {
			Group group = new Group();
			Subject subject = new Subject();
			subject.setTopicChoosingWay("信息技术方向——基于敏捷开发的校园超市系统");
			subject.setTutorName("曹腾飞");
			group.setSubject(subject);
			Teacher teacher = new Teacher();
			teacher.setCompellation("王晓英");
			teacher.setGender("女");
			teacher.setJob(JobType.PROFESSOR);
			teacher.setPhone("123456789");
			teacher.setEmail("333666999@qq.com");
			teacher.setResearchArea("云计算");
			group.setTeacher(teacher);
			return group;
		}
	}

	@Test
	public void testDeskTopStudentBegin() {
		MockableSpringBeanInjector.mockBean("signService",
				factory.implementAbstractMethods(MockSignService.class));
		MockableSpringBeanInjector.mockBean("eventService",
				factory.implementAbstractMethods(MockEventService.class));
		MockableSpringBeanInjector.mockBean("groupService",
				factory.implementAbstractMethods(MockGroupService.class));
		WicketSeleniumDriver ws = WebPageTestContext.getWicketSelenium();
		ws.openNonBookmarkablePage(Desktop.class);
		pause(1);
		isTrue(ws.isElementPresent("//menuItem1"));
		isTrue(ws.isElementPresent("//menuItem21"));
	}
}

package com.aqiang.gdms.wicket.page;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.Session;
import org.junit.Test;

import com.aqiang.bsms.entities.College;
import com.aqiang.bsms.entities.CollegeManager;
import com.aqiang.bsms.entities.Event;
import com.aqiang.bsms.entities.Subject;
import com.aqiang.bsms.entities.Teacher;
import com.aqiang.bsms.entities.User;
import com.aqiang.bsms.entities.UserType;
import com.aqiang.bsms.entities.WorkflowStatus;
import com.aqiang.bsms.service.DownloadService;
import com.aqiang.bsms.service.EventService;
import com.aqiang.bsms.service.SubjectService;
import com.aqiang.gdms.wicket.PageTest;
import com.aqiang.gdms.wicket.service.SignService;
import com.ttdev.wicketpagetest.MockableSpringBeanInjector;
import com.ttdev.wicketpagetest.WebPageTestContext;
import com.ttdev.wicketpagetest.WicketSeleniumDriver;

public class ExamSubjectTest extends PageTest {
	abstract class MockSignService implements SignService {
		@Override
		public College getCollege(Session session) {
			College college = new College();
			college.setCollegeName("计算机技术与应用系");
			return college;
		}

		@Override
		public String getUserType(Session session) {
			return UserType.COLLEGE_MANAGER;
		}

		@Override
		public User getSignUser(Session session) {
			CollegeManager collegeManager = new CollegeManager();
			collegeManager.setCompellation("刘志强");
			return collegeManager;
		}
	}

	abstract class MockDownloadService implements DownloadService {

		@Override
		public void downloadSubject(Subject subject, OutputStream output) {

		}

		@Override
		public void downloadSubjectsSelectFile(College college,
				Event currentEvent, OutputStream output) {

		}

		@Override
		public void downloadSubjectsSelectSummary(College college,
				Event currentEvent, OutputStream output) {

		}

	}

	abstract class MockEventService implements EventService {
		@Override
		public Event getCurrentEvent(College college) {
			Event event = new Event();
			event.setYear("2016");
			event.setWorkFlowStatus(WorkflowStatus.BEGIN);
			return event;
		}
	}
	
	abstract class MockSubjectClass implements SubjectService {
		private List<Subject> subjects = new ArrayList<Subject>();

		@Override
		public List<Subject> getAllSubjectByCollege(College college) {
			subjects.add(createSubject(college, "软件工程", createTeacher("陈二文")));
			subjects.add(createSubject(college, "云计算", createTeacher("陈大文")));
			subjects.add(createSubject(college, "信息处理", createTeacher("卢大文")));
			return subjects;
		}

		private Teacher createTeacher(String string) {
			Teacher teacher = new Teacher();
			teacher.setCompellation(string);
			return teacher;
		}

		private Subject createSubject(College college, String topicChoosingWay,
				Teacher teacher) {
			Subject subject = new Subject();
			subject.setCollege(college);
			subject.setTopicChoosingWay(topicChoosingWay);
			subject.setUser(teacher);
			return subject;
		}
	}

	@Test
	public void testDisplayInfo() {
		MockableSpringBeanInjector.mockBean("loginService",
				factory.implementAbstractMethods(MockSignService.class));
		MockableSpringBeanInjector.mockBean("subjectService",
				factory.implementAbstractMethods(MockSubjectClass.class));
		MockableSpringBeanInjector.mockBean("downloadService",
				factory.implementAbstractMethods(MockDownloadService.class));
		MockableSpringBeanInjector.mockBean("eventService",
				factory.implementAbstractMethods(MockEventService.class));
		WicketSeleniumDriver ws = WebPageTestContext.getWicketSelenium();
		ws.openNonBookmarkablePage(ExamSubject.class);
		isTrue(ws.isElementPresent("//return"));
		isTrue(ws.isElementPresent("//subjectTab"));
		isTrue(ws.isElementPresent("//subjectItem"));
		isEq("软件工程", ws.getText("//subjectItem[0]//topicChoosingWay"));
		isEq("陈二文", ws.getText("//subjectItem[0]//teacher.compellation"));
		isEq("云计算", ws.getText("//subjectItem[1]//topicChoosingWay"));
		isEq("陈大文", ws.getText("//subjectItem[1]//teacher.compellation"));
		isEq("信息处理", ws.getText("//subjectItem[2]//topicChoosingWay"));
		isEq("卢大文", ws.getText("//subjectItem[2]//teacher.compellation"));
	}

}

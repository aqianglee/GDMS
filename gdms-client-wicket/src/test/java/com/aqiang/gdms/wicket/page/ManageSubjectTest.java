package com.aqiang.gdms.wicket.page;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.Session;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;

import com.aqiang.bsms.entities.College;
import com.aqiang.bsms.entities.Event;
import com.aqiang.bsms.entities.Parameter;
import com.aqiang.bsms.entities.Specialty;
import com.aqiang.bsms.entities.Student;
import com.aqiang.bsms.entities.Subject;
import com.aqiang.bsms.entities.Teacher;
import com.aqiang.bsms.entities.User;
import com.aqiang.bsms.entities.UserType;
import com.aqiang.bsms.entities.WorkflowStatus;
import com.aqiang.bsms.service.DownloadService;
import com.aqiang.bsms.service.EventService;
import com.aqiang.bsms.service.ParameterService;
import com.aqiang.bsms.service.SubjectService;
import com.aqiang.gdms.wicket.PageTest;
import com.aqiang.gdms.wicket.service.SignService;
import com.ttdev.wicketpagetest.MockableSpringBeanInjector;
import com.ttdev.wicketpagetest.WebPageTestContext;
import com.ttdev.wicketpagetest.WicketSeleniumDriver;

public class ManageSubjectTest extends PageTest {

	abstract class MockParameterService implements ParameterService {
		@Override
		public Parameter getParameterByName(String name) {
			return null;
		}
	}

	abstract class MockSignService implements SignService {
		@Override
		public User getSignUser(Session session) {
			Teacher teacher = new Teacher();
			teacher.setId(1);
			College college = new College();
			college.setId(1);
			college.setCollegeName("计算机技术与应用系");
			teacher.setCollege(college);
			return teacher;
		}

		@Override
		public College getCollege(Session session) {
			College college = new College();
			college.setId(1);
			college.setCollegeName("计算机技术与应用系");
			return college;
		}

		@Override
		public String getUserType(Session session) {
			return UserType.TEACHER;
		}
	}

	abstract class MockEventService implements EventService {
		@Override
		public Event getCurrentEvent(College college) {
			Event event = new Event();
			event.setYear("2016");
			event.setWorkFlowStatus(WorkflowStatus.SELECT_SUBJECT);
			return event;
		}
	}

	abstract class MockSubjectService implements SubjectService {
		private List<Subject> subjects = new ArrayList<Subject>();

		@Override
		public List<Subject> getMyAllSubject(User user) {
			if (subjects.isEmpty()) {
				subjects.add(createSubject("云计算"));
				subjects.add(createSubject("网络安全"));
				subjects.add(createSubject("敏捷软件开发的研究与实现"));
			}
			return subjects;
		}

		private Subject createSubject(String name) {
			Subject subject = new Subject();
			subject.setTopicChoosingWay(name);
			subject.setId(subjects.size() + 1);
			return subject;
		}

		@Override
		public List<Student> getSelectedStudents(Subject subject) {
			College college = createCollege("计算机技术与应用系", "计算机系", "1");
			List<Student> students = new ArrayList<Student>();
			students.add(createStudent(college, "陈大文", "1000000001", "chendawen", "男", "66665678", "aqiang@gmail.com",
					"云计算"));
			Student student = createStudent(college, "陈二文", "1000000002", "chenerwen", "女", "99998877", "123@qq.com",
					"网络工程");
			students.add(student);
			return students;
		}

		private College createCollege(String collegeName, String simpleName, String number) {
			College college = new College();
			college.setCollegeName(collegeName);
			college.setSimpleName(simpleName);
			college.setNumber(number);
			return college;
		}

		private Student createStudent(College college, String compellation, String number, String username,
				String gender, String phone, String email, String likeArea) {
			Student student = new Student();
			student.setCollege(college);
			student.setCompellation(compellation);
			Specialty specialty = new Specialty();
			specialty.setName("计算机系");
			student.setNumber(number);
			student.setSpecialty(specialty);
			student.setUsername(username);
			student.setGender(gender);
			student.setEmail(email);
			student.setPhone(phone);
			student.setLikeArea(likeArea);
			return student;
		}
	}

	abstract class MockDownloadService implements DownloadService {
		@Override
		public void downloadSubject(Subject subject, OutputStream output) {

		}
	}

	@Test
	public void testDesplayInfo() {
		MockableSpringBeanInjector.mockBean("signService", factory.implementAbstractMethods(MockSignService.class));
		MockableSpringBeanInjector.mockBean("subjectService",
				factory.implementAbstractMethods(MockSubjectService.class));
		MockableSpringBeanInjector.mockBean("parameterService",
				factory.implementAbstractMethods(MockParameterService.class));
		MockableSpringBeanInjector.mockBean("eventService", factory.implementAbstractMethods(MockEventService.class));
		MockableSpringBeanInjector.mockBean("downloadService",
				factory.implementAbstractMethods(MockDownloadService.class));
		final WicketSeleniumDriver ws = WebPageTestContext.getWicketSelenium();
		ws.openNonBookmarkablePage(ManageSubject.class);
		isTrue(ws.isElementPresent("//subjectTab"));
		isTrue(ws.isElementPresent("//subjectItem"));
		isTrue("云计算".equals(ws.getText("//subjectItem[0]//topicChoosingWay")));
		isTrue("网络安全".equals(ws.getText("//subjectItem[1]//topicChoosingWay")));
		isTrue("敏捷软件开发的研究与实现".equals(ws.getText("//subjectItem[2]//topicChoosingWay")));
		isTrue(ws.isElementPresent("//showSelectedStudents"));
		ws.click("//showSelectedStudents");
		ws.wait(new ExpectedCondition<Boolean>() {

			@Override
			public Boolean apply(WebDriver arg0) {
				return ws.isElementPresent("//selectedStudentsTab");
			}
		});
		isTrue(ws.isElementPresent("//selectedStudents"));
		isTrue("陈大文".equals(ws.getText("//selectedStudents[0]//compellation")));
		isTrue("1000000001".equals(ws.getText("//selectedStudents[0]//number")));
		isTrue("男".equals(ws.getText("//selectedStudents[0]//gender")));
		isTrue("计算机系".equals(ws.getText("//selectedStudents[0]//specialty.name")));
		isTrue("66665678".equals(ws.getText("//selectedStudents[0]//phone")));
		isTrue("aqiang@gmail.com".equals(ws.getText("//selectedStudents[0]//email")));
		isTrue("云计算".equals(ws.getText("//selectedStudents[0]//likeArea")));
		isTrue("陈二文".equals(ws.getText("//selectedStudents[1]//compellation")));
		isTrue("1000000002".equals(ws.getText("//selectedStudents[1]//number")));
		isTrue("女".equals(ws.getText("//selectedStudents[1]//gender")));
		isTrue("计算机系".equals(ws.getText("//selectedStudents[1]//specialty.name")));
		isTrue("99998877".equals(ws.getText("//selectedStudents[1]//phone")));
		isTrue("123@qq.com".equals(ws.getText("//selectedStudents[1]//email")));
		isTrue("网络工程".equals(ws.getText("//selectedStudents[1]//likeArea")));
	}
}

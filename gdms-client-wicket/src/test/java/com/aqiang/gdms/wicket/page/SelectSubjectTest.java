package com.aqiang.gdms.wicket.page;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.Session;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;

import com.aqiang.bsms.entities.College;
import com.aqiang.bsms.entities.Event;
import com.aqiang.bsms.entities.JobType;
import com.aqiang.bsms.entities.Specialty;
import com.aqiang.bsms.entities.Student;
import com.aqiang.bsms.entities.Subject;
import com.aqiang.bsms.entities.TeachAndResearchOffice;
import com.aqiang.bsms.entities.Teacher;
import com.aqiang.bsms.entities.User;
import com.aqiang.bsms.entities.UserType;
import com.aqiang.bsms.service.SubjectService;
import com.aqiang.gdms.wicket.PageTest;
import com.aqiang.gdms.wicket.service.SignService;
import com.ttdev.wicketpagetest.MockableSpringBeanInjector;
import com.ttdev.wicketpagetest.WebPageTestContext;
import com.ttdev.wicketpagetest.WicketSeleniumDriver;

public class SelectSubjectTest extends PageTest {

	abstract class MockSignService implements SignService {
		@Override
		public User getSignUser(Session session) {
			Student student = new Student();
			student.setCompellation("陈大文");
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
			return UserType.SYSTEM_MANAGER;
		}
	}

	abstract class MockSubjcetService implements SubjectService {

		private List<Subject> subjects = new ArrayList<Subject>();

		@Override
		public List<Subject> getSelectAbleSubject(College college) {

			if (subjects.size() == 0) {
				Event event = new Event();
				event.setYear("2016");
				event.setActive(true);
				TeachAndResearchOffice teachAndResearchOffice = createTeachAndResearchOffice("计算机教研室");
				Specialty specialty = createSpecialty("计算机技术与应用系");
				Subject subject = createSubject(event, teachAndResearchOffice,
						"信息技术方向-基于敏捷软件开发的校园超市系统", "2016-03-01", "2016-06-01",
						college, specialty, "陈大文", JobType.PROFESSOR,
						JobType.LECTURER, "0", true);
				List<Student> students = new ArrayList<Student>();
				students.add(createStudent(college, "陈大文", "1000000001",
						"chendawen", "男", "66665678", "aqiang@gmail.com", "云计算"));
				Student student = createStudent(college, "陈二文", "1000000002",
						"chenerwen", "女", "99998877", "123@qq.com", "网络工程");
				students.add(student);
				subject.setStudents(students);
				subject.setUser(createTeacher(college, "王大文", "男",
						"123456789@gmail.com", JobType.PROFESSOR, "12345678",
						"多媒体技术"));
				subjects.add(subject);
				subjects.add(createSubject(event, teachAndResearchOffice,
						"信息技术方向-基于软件工程的XXX技术", "2016-03-01", "2016-06-01",
						college, specialty, "陈二文", JobType.PROFESSOR,
						JobType.LECTURER, "0", true));
				subjects.add(createSubject(event, teachAndResearchOffice,
						"信息技术方向-基于软件工程的YYY技术", "2016-03-01", "2016-06-01",
						college, specialty, "鲁大文", JobType.PROFESSOR,
						JobType.LECTURER, "0", null));
			}
			return subjects;
		}

		@Override
		public List<Student> getSelectedStudents(Subject subject) {
			for (Subject s : subjects) {
				if (s.getId() == subject.getId()) {
					return s.getStudents();
				}
			}
			return null;
		}

		private Subject createSubject(Event event,
				TeachAndResearchOffice teachAndResearchOffice, String name,
				String beginDate, String endDate, College college,
				Specialty specialty, String don, String donJob,
				String tutorJob, String budget, Boolean commented) {
			Subject subject = new Subject();
			subject.setTopicChoosingWay(name);
			subject.setEvent(event);
			subject.setTeachAndResearchOffice(teachAndResearchOffice);
			subject.setBeginTime(date(beginDate, "yyyy-MM-dd"));
			subject.setEndTime(date(endDate, "yyyy-MM-dd"));
			subject.setBudget(budget);
			subject.setCollege(college);
			subject.setDon(don);
			subject.setTutorJob(tutorJob);
			subject.setDonJob(donJob);
			subject.setSpecialty(specialty);
			subject.setCommented(commented);
			subject.setId(subjects.size() + 1);
			return subject;
		}

		private Student createStudent(College college, String compellation,
				String number, String username, String gender, String phone,
				String email, String likeArea) {
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

		private Teacher createTeacher(College college, String compellation,
				String gender, String email, String job, String phone,
				String researchArea) {
			Teacher teacher = new Teacher();
			teacher.setCollege(college);
			teacher.setCompellation(compellation);
			teacher.setGender(gender);
			teacher.setEmail(email);
			teacher.setJob(job);
			teacher.setPhone(phone);
			teacher.setResearchArea(researchArea);
			return teacher;
		}

		private TeachAndResearchOffice createTeachAndResearchOffice(String name) {
			TeachAndResearchOffice teachAndResearchOffice = new TeachAndResearchOffice();
			teachAndResearchOffice.setName(name);
			return teachAndResearchOffice;
		}

		private Specialty createSpecialty(String name) {
			Specialty specialty = new Specialty();
			specialty.setName(name);
			return specialty;
		}
	}

	@Test
	public void testDisplayCurrentInfo() {
		MockableSpringBeanInjector.mockBean("signService",
				factory.implementAbstractMethods(MockSignService.class));
		MockableSpringBeanInjector.mockBean("subjectService",
				factory.implementAbstractMethods(MockSubjcetService.class));
		final WicketSeleniumDriver ws = WebPageTestContext.getWicketSelenium();
		ws.openNonBookmarkablePage(SelectSubject.class);
		isTrue(ws.isElementPresent("//return"));
		isTrue(ws.isElementPresent("//subjectTab"));
		isTrue(ws.isElementPresent("//subjectItem"));
		isTrue(ws.getText("//subjectItem[0]//topicChoosingWay").equals(
				"信息技术方向-基于敏捷软件开发的校园超市系统"));
		isTrue(ws.getText("//subjectItem[1]//topicChoosingWay").equals(
				"信息技术方向-基于软件工程的XXX技术"));
		isTrue(ws.getText("//subjectItem[2]//topicChoosingWay").equals(
				"信息技术方向-基于软件工程的YYY技术"));
		isTrue(ws.getText("//subjectItem[0]//don").equals("陈大文"));
		isTrue(ws.getText("//subjectItem[1]//don").equals("陈二文"));
		isTrue(ws.getText("//subjectItem[2]//don").equals("鲁大文"));
		isTrue(ws.isElementPresent("//showSelectStudents"));
		ws.click("//subjectItem[0]//showSelectStudents");
		ws.wait(new ExpectedCondition<Boolean>() {

			@Override
			public Boolean apply(WebDriver arg0) {
				return ws.isElementPresent("//selectedStudentTab");
			}
		});
		isTrue(ws.isElementPresent("//selectedStudents"));
		isTrue("陈大文".equals(ws.getText("//selectedStudents[0]//compellation")));
		isTrue("陈二文".equals(ws.getText("//selectedStudents[1]//compellation")));
		isTrue("男".equals(ws.getText("//selectedStudents[0]//gender")));
		isTrue("女".equals(ws.getText("//selectedStudents[1]//gender")));
		isTrue("66665678".equals(ws.getText("//selectedStudents[0]//phone")));
		isTrue("99998877".equals(ws.getText("//selectedStudents[1]//phone")));
		isTrue("aqiang@gmail.com".equals(ws
				.getText("//selectedStudents[0]//email")));
		isTrue("123@qq.com".equals(ws.getText("//selectedStudents[1]//email")));
		isTrue("云计算".equals(ws.getText("//selectedStudents[0]//likeArea")));
		isTrue("网络工程".equals(ws.getText("//selectedStudents[1]//likeArea")));
		isTrue("未选定".equals(ws.getText("//selectedStudents[0]//confirmed")));
		isTrue("选定".equals(ws.getText("//selectedStudents[1]//confirmed")));
		ws.findElement(By.id("closeSelectedStudents")).click();
		ws.wait(new ExpectedCondition<Boolean>() {

			@Override
			public Boolean apply(WebDriver arg0) {
				return ws.isElementPresent("//subjectTab");
			}
		});
		isTrue(ws.isElementPresent("//showTeacherDetails"));
		ws.click("//subjectItem[0]//showTeacherDetails");
		ws.wait(new ExpectedCondition<Boolean>() {

			@Override
			public Boolean apply(WebDriver arg0) {
				return ws.isElementPresent("//teacherDetails");
			}
		});
		isTrue("王大文".equals(ws.getText("//teacherDetails//compellation")));
		isTrue("男".equals(ws.getText("//teacherDetails//gender")));
		isTrue("教授".equals(ws.getText("//teacherDetails//job")));
		isTrue("12345678".equals(ws.getText("//teacherDetails//phone")));
		isTrue("123456789@gmail.com".equals(ws
				.getText("//teacherDetails//email")));
		isTrue("多媒体技术".equals(ws.getText("//teacherDetails//researchArea")));
		ws.findElement(By.id("closeTeacherDetails")).click();
		ws.wait(new ExpectedCondition<Boolean>() {

			@Override
			public Boolean apply(WebDriver arg0) {
				return ws.isElementPresent("//subjectTab");
			}
		});
		isTrue(ws.isElementPresent("//select"));
		ws.click("//subjectItem[0]//select");
		pause(1);
		ws.getSelenium().switchTo().alert().accept();
	}

}

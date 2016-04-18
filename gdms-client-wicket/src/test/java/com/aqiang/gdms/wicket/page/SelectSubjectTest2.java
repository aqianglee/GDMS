package com.aqiang.gdms.wicket.page;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.Session;
import org.junit.Test;

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

public class SelectSubjectTest2 extends PageTest {

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
						"chendawen"));
				Student student = createStudent(college, "陈二文", "1000000002",
						"chenerwen");
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
			return subject;
		}

		private Student createStudent(College college, String compellation,
				String number, String username) {
			Student student = new Student();
			student.setCollege(college);
			student.setCompellation(compellation);
			Specialty specialty = new Specialty();
			specialty.setName("计算机系");
			student.setNumber(number);
			student.setSpecialty(specialty);
			student.setUsername(username);
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
	public void testSelectSubject() {
		MockableSpringBeanInjector.mockBean("signService",
				factory.implementAbstractMethods(MockSignService.class));
		MockableSpringBeanInjector.mockBean("subjectService",
				factory.implementAbstractMethods(MockSubjcetService.class));
		WicketSeleniumDriver ws = WebPageTestContext.getWicketSelenium();
		ws.openNonBookmarkablePage(SelectSubject.class);
		isTrue(ws.isElementPresent("//select"));
		ws.click("//subjectItem[0]//select");
		pause(1);
		ws.getSelenium().switchTo().alert().accept();
	}

}

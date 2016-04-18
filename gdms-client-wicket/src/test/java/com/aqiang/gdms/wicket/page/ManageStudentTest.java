package com.aqiang.gdms.wicket.page;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.Session;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aqiang.bsms.entities.College;
import com.aqiang.bsms.entities.CollegeManager;
import com.aqiang.bsms.entities.Event;
import com.aqiang.bsms.entities.Specialty;
import com.aqiang.bsms.entities.Student;
import com.aqiang.bsms.entities.User;
import com.aqiang.bsms.entities.UserType;
import com.aqiang.bsms.service.SpecialtyService;
import com.aqiang.bsms.service.StudentService;
import com.aqiang.gdms.wicket.PageTest;
import com.aqiang.gdms.wicket.service.SignService;
import com.ttdev.wicketpagetest.MockableSpringBeanInjector;
import com.ttdev.wicketpagetest.WebPageTestContext;
import com.ttdev.wicketpagetest.WicketSeleniumDriver;

public class ManageStudentTest extends PageTest {

	private Logger LOGGER = LoggerFactory.getLogger(ManageStudentTest.class);

	abstract class MockSignService implements SignService {
		@Override
		public College getCollege(Session session) {
			College college = new College();
			LOGGER.info("the method SignService getCollege is Called");
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

	abstract class MockSpecialtyService implements SpecialtyService {

		private List<Specialty> specialtys = new ArrayList<Specialty>();

		@Override
		public List<Specialty> getSpecialtiesByCollege(College college) {
			if (specialtys.size() == 0) {
				specialtys.add(createSpecialty(college, "软件工程", "1"));
				specialtys.add(createSpecialty(college, "计算机科学与技术", "2"));
			}
			return specialtys;
		}

		private Specialty createSpecialty(College college, String name,
				String num) {
			Specialty specialty = new Specialty();
			specialty.setCollege(college);
			specialty.setName(name);
			specialty.setNum(num);
			specialty.setId(specialtys.size() + 1);
			return specialty;
		}

	}

	abstract class MockStudentService implements StudentService {
		private List<Student> students = new ArrayList<Student>();

		@Override
		public List<Student> getStudentsByCollege(College college) {
			if (students.size() == 0) {
				students.add(createStudent(college, "陈大文", "1234567890",
						"chendawen"));
				students.add(createStudent(college, "鲁大文", "1234567891",
						"ludawen"));
			}
			return students;
		}

		@Override
		public List<Student> getStudentsByCollegeAndCurrnetEvent(
				College college, Event currentEvent) {
			if (students.size() == 0) {
				students.add(createStudent(college, "陈大文", "1234567890",
						"chendawen"));
				students.add(createStudent(college, "鲁大文", "1234567891",
						"ludawen"));
			}
			return students;
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
	}

	@Test
	public void testDisplayInfo() {
		MockableSpringBeanInjector.mockBean("loginService",
				factory.implementAbstractMethods(MockSignService.class));
		MockableSpringBeanInjector.mockBean("specialtyService",
				factory.implementAbstractMethods(MockSpecialtyService.class));
		MockableSpringBeanInjector.mockBean("studentService",
				factory.implementAbstractMethods(MockStudentService.class));
		WicketSeleniumDriver ws = WebPageTestContext.getWicketSelenium();
		ws.openNonBookmarkablePage(ManageStudent.class);
		isTrue(ws.isElementPresent("//return"));
		isTrue(ws.isElementPresent("//addStudent"));
		isTrue(ws.isElementPresent("//studentTab"));
		isTrue(ws.isElementPresent("//studentItem"));
		// isTrue(ws.getText("//studentItem[0]//college.collegeName").equals("计算机技术与应用系"));
		// isTrue(ws.getText("//studentItem[1]//college.collegeName").equals("计算机技术与应用系"));
		isTrue(ws.getText("//studentItem[0]//compellation").equals("陈大文"));
		isTrue(ws.getText("//studentItem[1]//compellation").equals("鲁大文"));
		isTrue(ws.getText("//studentItem[0]//specialty.name").equals("计算机系"));
		isTrue(ws.getText("//studentItem[1]//specialty.name").equals("计算机系"));
		isTrue(ws.getText("//studentItem[0]//number").equals("1234567890"));
		isTrue(ws.getText("//studentItem[1]//number").equals("1234567891"));
		isTrue(ws.getText("//studentItem[0]//username").equals("chendawen"));
		isTrue(ws.getText("//studentItem[1]//username").equals("ludawen"));
	}
}

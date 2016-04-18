package com.aqiang.gdms.wicket.page;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.Session;
import org.junit.Test;

import com.aqiang.bsms.entities.College;
import com.aqiang.bsms.entities.Group;
import com.aqiang.bsms.entities.JobType;
import com.aqiang.bsms.entities.Student;
import com.aqiang.bsms.entities.Subject;
import com.aqiang.bsms.entities.Teacher;
import com.aqiang.bsms.entities.User;
import com.aqiang.bsms.entities.UserType;
import com.aqiang.bsms.service.StudentService;
import com.aqiang.gdms.wicket.PageTest;
import com.aqiang.gdms.wicket.service.SignService;
import com.ttdev.wicketpagetest.MockableSpringBeanInjector;
import com.ttdev.wicketpagetest.WebPageTestContext;
import com.ttdev.wicketpagetest.WicketSeleniumDriver;

public class MyGraduadeDesignTest3 extends PageTest {

	abstract class MockStudentService implements StudentService {
		private List<Student> students = new ArrayList<Student>();

		@Override
		public List<Student> getStudentsByGroup(Group group) {
			if (students.size() == 0) {
				students.add(createStudent("李志强", "1200802022", "男",
						"18826202524", "695182311@qq.com", "软件工程"));
				students.add(createStudent("杨静波", "1200802021", "男",
						"123456789", "987654321", "人工智能"));
			}
			return students;
		}

		private Student createStudent(String compellation, String number,
				String gender, String phone, String email, String likeArea) {
			Student student = new Student();
			student.setCompellation(compellation);
			student.setNumber(number);
			student.setEmail(email);
			student.setPhone(phone);
			student.setGender(gender);
			student.setLikeArea(likeArea);
			return student;
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

	@Test
	public void testCurrentDisplay() {
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

		MockableSpringBeanInjector.mockBean("studentService",
				factory.implementAbstractMethods(MockStudentService.class));
		MockableSpringBeanInjector.mockBean("signService",
				factory.implementAbstractMethods(MockSignService.class));
		final WicketSeleniumDriver ws = WebPageTestContext.getWicketSelenium();
		ws.openNonBookmarkablePage(MyGraduadeDesign.class, group);
		assertTrue(!ws.isElementPresent("//addNewTask"));
		assertTrue(!ws.isElementPresent("//editTask"));
		assertTrue(ws.isElementPresent("//showTask"));
		assertTrue(ws.isElementPresent("//downloadTask"));
		assertTrue(!ws.isElementPresent("//studentsManageSchedule"));
		assertTrue(ws.isElementPresent("//manageSchedule"));
	}

}

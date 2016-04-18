package com.aqiang.gdms.wicket.page;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.Session;
import org.junit.Test;

import com.aqiang.bsms.entities.College;
import com.aqiang.bsms.entities.CollegeManager;
import com.aqiang.bsms.entities.JobType;
import com.aqiang.bsms.entities.Teacher;
import com.aqiang.bsms.entities.User;
import com.aqiang.bsms.entities.UserType;
import com.aqiang.bsms.service.TeacherService;
import com.aqiang.gdms.wicket.PageTest;
import com.aqiang.gdms.wicket.service.SignService;
import com.ttdev.wicketpagetest.MockableSpringBeanInjector;
import com.ttdev.wicketpagetest.WebPageTestContext;
import com.ttdev.wicketpagetest.WicketSeleniumDriver;

public class ManageTeacherTest extends PageTest {

	abstract class MockSignService implements SignService {
		@Override
		public User getSignUser(Session session) {
			CollegeManager collegeManager = new CollegeManager();
			collegeManager.setId(1);
			College college = new College();
			college.setId(1);
			college.setCollegeName("计算机技术与应用系");
			collegeManager.setCollege(college);
			return collegeManager;
		}

		@Override
		public College getCollege(Session session) {
			College college = new College();
			college.setCollegeName("计算机科学与技术");
			return college;
		}

		@Override
		public String getUserType(Session session) {
			return UserType.COLLEGE_MANAGER;
		}
	}

	abstract class MockTeacherService implements TeacherService {
		private List<Teacher> teachers = new ArrayList<Teacher>();

		@Override
		public List<Teacher> getTeachersByCollege(College college) {
			if (teachers.size() == 0) {
				teachers.add(createTeacher(college, "李志强", "男", "695182311@qq.com", JobType.ASSISTANT, "18397101270",
						"云计算"));
				teachers.add(createTeacher(college, "陈大文", "女", "695182311@qq.com", JobType.PROFESSOR, "18397101280",
						"网络工程"));
				teachers.add(createTeacher(college, "曹腾飞", "男", "695182311@qq.com", JobType.LECTURER, "18397101290",
						"软件工程"));
			}
			return teachers;
		}

		private Teacher createTeacher(College myCollege, String compellation, String gender, String email, String job,
				String phone, String researchArea) {
			Teacher teacher = new Teacher();
			teacher.setCollege(myCollege);
			teacher.setCompellation(compellation);
			teacher.setGender(gender);
			teacher.setEmail(email);
			teacher.setJob(job);
			teacher.setPhone(phone);
			teacher.setResearchArea(researchArea);
			teacher.setId(teachers.size() + 1);
			return teacher;
		}
	}

	@Test
	public void testDesplayInfo() {
		MockableSpringBeanInjector.mockBean("signService", factory.implementAbstractMethods(MockSignService.class));
		MockableSpringBeanInjector.mockBean("teacherService",
				factory.implementAbstractMethods(MockTeacherService.class));
		WicketSeleniumDriver ws = WebPageTestContext.getWicketSelenium();
		ws.openNonBookmarkablePage(ManageTeacher.class);
		ws.isElementPresent("//teacherTab");
		ws.isElementPresent("//teacherItem");
		isTrue("计算机科学与技术".equals(ws.getText("//teacherItem[0]//college.collegeName")));
		isTrue("计算机科学与技术".equals(ws.getText("//teacherItem[1]//college.collegeName")));
		isTrue("计算机科学与技术".equals(ws.getText("//teacherItem[2]//college.collegeName")));
		isTrue("李志强".equals(ws.getText("//teacherItem[0]//compellation")));
		isTrue("陈大文".equals(ws.getText("//teacherItem[1]//compellation")));
		isTrue("曹腾飞".equals(ws.getText("//teacherItem[2]//compellation")));
		isTrue("男".equals(ws.getText("//teacherItem[0]//gender")));
		isTrue("女".equals(ws.getText("//teacherItem[1]//gender")));
		isTrue("男".equals(ws.getText("//teacherItem[2]//gender")));
		isTrue("助教".equals(ws.getText("//teacherItem[0]//job")));
		isTrue("教授".equals(ws.getText("//teacherItem[1]//job")));
		isTrue("讲师".equals(ws.getText("//teacherItem[2]//job")));
		isTrue("18397101270".equals(ws.getText("//teacherItem[0]//phone")));
		isTrue("18397101280".equals(ws.getText("//teacherItem[1]//phone")));
		isTrue("18397101290".equals(ws.getText("//teacherItem[2]//phone")));
		isTrue("云计算".equals(ws.getText("//teacherItem[0]//researchArea")));
		isTrue("网络工程".equals(ws.getText("//teacherItem[1]//researchArea")));
		isTrue("软件工程".equals(ws.getText("//teacherItem[2]//researchArea")));
	}
}

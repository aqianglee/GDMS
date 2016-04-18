package com.aqiang.gdms.wicket.page;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;

import com.aqiang.bsms.entities.Group;
import com.aqiang.bsms.entities.JobType;
import com.aqiang.bsms.entities.Student;
import com.aqiang.bsms.entities.Subject;
import com.aqiang.bsms.entities.Teacher;
import com.aqiang.bsms.service.StudentService;
import com.aqiang.gdms.wicket.PageTest;
import com.ttdev.wicketpagetest.MockableSpringBeanInjector;
import com.ttdev.wicketpagetest.WebPageTestContext;
import com.ttdev.wicketpagetest.WicketSeleniumDriver;

public class MyGraduadeDesignTest extends PageTest {

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
		final WicketSeleniumDriver ws = WebPageTestContext.getWicketSelenium();
		ws.openNonBookmarkablePage(MyGraduadeDesign.class, group);
		isTrue(ws.isElementPresent("//return"));
		isTrue(ws.isElementPresent("//myGraduadeDesign"));
		isEq(ws.getText("//subject.topicChoosingWay"), "信息技术方向——基于敏捷开发的校园超市系统");
		isEq(ws.getText("//teacher.compellation"), "王晓英");
		ws.click(By.id("teacher"));
		ws.wait(new ExpectedCondition<Boolean>() {

			@Override
			public Boolean apply(WebDriver arg0) {
				return ws.isElementPresent("//teacherDetails");
			}
		});
		isEq(ws.getText("//teacherDetails//compellation"), "王晓英");
		isEq(ws.getText("//teacherDetails//gender"), "女");
		isEq(ws.getText("//teacherDetails//job"), "教授");
		isEq(ws.getText("//teacherDetails//phone"), "123456789");
		isEq(ws.getText("//teacherDetails//email"), "333666999@qq.com");
		isEq(ws.getText("//teacherDetails//researchArea"), "云计算");
		ws.click(By.id("closeTeacherDetails"));
		ws.wait(new ExpectedCondition<Boolean>() {

			@Override
			public Boolean apply(WebDriver arg0) {
				return ws.isElementPresent("//myGraduadeDesign");
			}
		});
		isEq(ws.getText("//subject.tutorName"), "曹腾飞");
		isTrue(ws.isElementPresent("//studentDetails"));
		isEq(ws.getText("//students[0]//student//compellation"), "李志强");
		isEq(ws.getText("//students[1]//student//compellation"), "杨静波");
		ws.click("//students[0]//student");
		ws.wait(new ExpectedCondition<Boolean>() {

			@Override
			public Boolean apply(WebDriver arg0) {
				return ws.isElementPresent("//studentDetails");
			}
		});
		isEq(ws.getText("//studentDetails//compellation"), "李志强");
		isEq(ws.getText("//studentDetails//gender"), "男");
		isEq(ws.getText("//studentDetails//number"), "1200802022");
		isEq(ws.getText("//studentDetails//phone"), "18826202524");
		isEq(ws.getText("//studentDetails//email"), "695182311@qq.com");
		isEq(ws.getText("//studentDetails//likeArea"), "软件工程");
		ws.click(By.id("closeStudentDetails"));
		ws.wait(new ExpectedCondition<Boolean>() {

			@Override
			public Boolean apply(WebDriver arg0) {
				return ws.isElementPresent("//myGraduadeDesign");
			}
		});
		ws.click("//students[1]//student");
		ws.wait(new ExpectedCondition<Boolean>() {

			@Override
			public Boolean apply(WebDriver arg0) {
				return ws.isElementPresent("//studentDetails");
			}
		});
		isEq(ws.getText("//studentDetails//compellation"), "杨静波");
		isEq(ws.getText("//studentDetails//gender"), "男");
		isEq(ws.getText("//studentDetails//number"), "1200802021");
		isEq(ws.getText("//studentDetails//phone"), "123456789");
		isEq(ws.getText("//studentDetails//email"), "987654321");
		isEq(ws.getText("//studentDetails//likeArea"), "人工智能");
		ws.click(By.id("closeStudentDetails"));
		ws.wait(new ExpectedCondition<Boolean>() {

			@Override
			public Boolean apply(WebDriver arg0) {
				return ws.isElementPresent("//myGraduadeDesign");
			}
		});
	}

}

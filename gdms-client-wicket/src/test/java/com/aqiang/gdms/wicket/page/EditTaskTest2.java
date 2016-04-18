package com.aqiang.gdms.wicket.page;

import org.junit.Test;

import com.aqiang.bsms.entities.Specialty;
import com.aqiang.bsms.entities.Student;
import com.aqiang.bsms.entities.Subject;
import com.aqiang.bsms.entities.Task;
import com.aqiang.bsms.entities.Teacher;
import com.aqiang.bsms.service.TaskService;
import com.aqiang.gdms.wicket.PageTest;
import com.ttdev.wicketpagetest.MockableSpringBeanInjector;
import com.ttdev.wicketpagetest.WebPageTestContext;
import com.ttdev.wicketpagetest.WicketSeleniumDriver;

public class EditTaskTest2 extends PageTest {
	abstract class MockTaskService implements TaskService {
		@Override
		public void editTask(Task task) {

		}
	}

	@Test
	public void testEditTask() {
		Task task = new Task();
		Subject subject = new Subject();
		subject.setTopicChoosingWay("信息技术方向");
		Student student = new Student();
		student.setCompellation("李志强");
		Specialty specialty = new Specialty();
		specialty.setName("计算机科学与技术2012级");
		student.setSpecialty(specialty);
		task.setStudent(student);
		task.setSubject(subject);
		Teacher teacher = new Teacher();
		teacher.setCompellation("曹腾飞");
		task.setTeacher(teacher);
		task.setCompany("青海大学");
		MockableSpringBeanInjector.mockBean("taskService",
				factory.implementAbstractMethods(MockTaskService.class));
		WicketSeleniumDriver ws = WebPageTestContext.getWicketSelenium();
		ws.openNonBookmarkablePage(EditTask.class, task);
		isTrue(ws.isElementPresent("//dissertationMainContent"));
		isTrue(ws.isElementPresent("//mainTask"));
		isTrue(ws.isElementPresent("//teacherComment"));
		isTrue(ws.isElementPresent("//collegeComment"));
		ws.sendKeys("//dissertationMainContent", "aaaaaaaaa");
		ws.sendKeys("//mainTask", "bbbbbbbb");
		ws.sendKeys("//teacherComment", "cccccccc");
		ws.sendKeys("//collegeComment", "dddddddd");
		isTrue(ws.isElementPresent("//submit"));
		ws.click("//submit");
	}
}

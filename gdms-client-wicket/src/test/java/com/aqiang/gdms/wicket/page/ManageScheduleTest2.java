package com.aqiang.gdms.wicket.page;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;

import com.aqiang.bsms.entities.File;
import com.aqiang.bsms.entities.Parameter;
import com.aqiang.bsms.entities.Schedule;
import com.aqiang.bsms.entities.ScheduleItem;
import com.aqiang.bsms.entities.Student;
import com.aqiang.bsms.service.FileService;
import com.aqiang.bsms.service.ParameterService;
import com.aqiang.bsms.service.ScheduleItemService;
import com.aqiang.bsms.service.ScheduleService;
import com.aqiang.gdms.wicket.PageTest;
import com.ttdev.wicketpagetest.MockableSpringBeanInjector;
import com.ttdev.wicketpagetest.WebPageTestContext;
import com.ttdev.wicketpagetest.WicketSeleniumDriver;

public class ManageScheduleTest2 extends PageTest {

	abstract class MockScheduleService implements ScheduleService {
		@Override
		public Schedule getScheduleByStudent(Student student) {
			Schedule schedule = new Schedule();
			return schedule;
		}
	}

	abstract class MockFileService implements FileService {
		@Override
		public String getPostfixName(String fileName) {
			return null;
		}

		@Override
		public void saveEntitiy(File file) {

		}
	}

	abstract class MockParameterService implements ParameterService {
		@Override
		public Parameter getParameterByName(String name) {
			return null;
		}
	}

	abstract class MockScheduleItemService implements ScheduleItemService {
		private List<ScheduleItem> scheduleItems = new ArrayList<ScheduleItem>();

		@Override
		public List<ScheduleItem> getScheduleItemsBySchedule(Schedule schedule) {
			if (scheduleItems.size() == 0) {
				createScheduleItem(schedule, "week 1 content", "2016-01-21",
						"第一周", true, new File());
				createScheduleItem(schedule, "week 2 content", "2016-01-27",
						"第二周", false, null);
				createScheduleItem(schedule, "week 3 content", null, "第三周",
						null, new File());
			}
			return scheduleItems;
		}

		private void createScheduleItem(Schedule schedule, String content,
				String dataStr, String week, Boolean checked, File attachment) {
			ScheduleItem scheduleItem = new ScheduleItem();
			scheduleItem.setId(scheduleItems.size() + 1);
			scheduleItem.setSchedule(schedule);
			scheduleItem.setContent(content);
			if (dataStr != null && dataStr.length() > 0) {
				scheduleItem.setCheckedTime(date(dataStr, "yyyy-MM-dd"));
			}
			scheduleItem.setWeek(week);
			scheduleItem.setChecked(checked);
			scheduleItems.add(scheduleItem);
		}
	}

	@Test
	public void testAddNewSchedule() {
		Student student = new Student();
		final WicketSeleniumDriver ws = WebPageTestContext.getWicketSelenium();
		MockableSpringBeanInjector.mockBean("scheduleService",
				factory.implementAbstractMethods(MockScheduleService.class));
		MockableSpringBeanInjector
				.mockBean(
						"scheduleItemService",
						factory.implementAbstractMethods(MockScheduleItemService.class));
		MockableSpringBeanInjector.mockBean("fileService",
				MockFileService.class);
		MockableSpringBeanInjector.mockBean("parameterService",
				MockParameterService.class);
		ws.openNonBookmarkablePage(ManageSchedule.class, student);
		isTrue(ws.isElementPresent("//addScheduleItem"));
		ws.click("//addScheduleItem");
		ws.wait(new ExpectedCondition<Boolean>() {

			@Override
			public Boolean apply(WebDriver arg0) {
				return ws.isElementPresent("//editScheduleItem");
			}
		});
		isTrue(ws.isElementPresent("//week"));
		isTrue(ws.isElementPresent("//content"));
		isTrue(ws.isElementPresent("//attachment"));
		isTrue(ws.isElementPresent("//progress"));
	}

}

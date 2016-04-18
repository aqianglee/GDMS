package com.aqiang.gdms.wicket.page;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

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
import com.aqiang.gdms.wicket.page.ManageScheduleTest2.MockFileService;
import com.aqiang.gdms.wicket.page.ManageScheduleTest2.MockParameterService;
import com.ttdev.wicketpagetest.MockableSpringBeanInjector;
import com.ttdev.wicketpagetest.WebPageTestContext;
import com.ttdev.wicketpagetest.WicketSeleniumDriver;

public class ManageScheduleTest extends PageTest {

	abstract class MockScheduleService implements ScheduleService {
		@Override
		public Schedule getScheduleByStudent(Student student) {
			Schedule schedule = new Schedule();
			return schedule;
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

	@Test
	public void testCurrentDisplay() {
		Student student = new Student();
		WicketSeleniumDriver ws = WebPageTestContext.getWicketSelenium();
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
		isTrue(ws.isElementPresent("//return"));
		isTrue(ws.isElementPresent("//addScheduleItem"));
		isTrue(ws.isElementPresent("//downloadSubjectsSchedule"));
		isTrue(ws.isElementPresent("//scheduleItemsTab"));
		isTrue(ws.isElementPresent("//scheduleItems"));
		isEq(ws.getText("//scheduleItems[0]//week"), "第一周");
		isEq(ws.getText("//scheduleItems[1]//week"), "第二周");
		isEq(ws.getText("//scheduleItems[2]//week"), "第三周");

		isEq(ws.getText("//scheduleItems[0]//content"), "week 1 content");
		isEq(ws.getText("//scheduleItems[1]//content"), "week 2 content");
		isEq(ws.getText("//scheduleItems[2]//content"), "week 3 content");

		isEq(ws.getText("//scheduleItems[0]//checkedTime"), "2016-01-21");
		isEq(ws.getText("//scheduleItems[1]//checkedTime"), "2016-01-27");
		isEq(ws.getText("//scheduleItems[2]//checkedTime"), "");

		isEq(ws.getText("//scheduleItems[0]//checked"), "通过");
		isEq(ws.getText("//scheduleItems[1]//checked"), "未通过");
		isEq(ws.getText("//scheduleItems[2]//checked"), "");

		isEq(ws.getText("//scheduleItems[0]//downloadLabel"), "下载");
		isEq(ws.getText("//scheduleItems[1]//downloadLabel"), "无");
		isEq(ws.getText("//scheduleItems[2]//downloadLabel"), "下载");
	}

}

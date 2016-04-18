package com.aqiang.bsms.service.impl;

import java.util.List;

import org.junit.Test;

import com.aqiang.bsms.entities.Schedule;
import com.aqiang.bsms.entities.ScheduleItem;
import com.aqiang.gdms.BaseTest;

public class ScheduleItemServiceImplTest extends BaseTest {

	@Test
	public void testGetScheduleItemsBySchedule() {
		Schedule schedule = new Schedule();
		scheduleService.saveEntitiy(schedule);
		createScheduleItem(schedule, "week 1 content", "2016-01-27", "1");
		createScheduleItem(null, "week 1 content", "2016-01-27", "1");
		createScheduleItem(schedule, "week 2 content", "2016-01-27", "2");

		List<ScheduleItem> scheduleItems = scheduleItemService
				.getScheduleItemsBySchedule(schedule);
		isEq(scheduleItems.size(), 2);
	}

	private void createScheduleItem(Schedule schedule, String content,
			String dataStr, String week) {
		ScheduleItem scheduleItem = new ScheduleItem();
		scheduleItem.setSchedule(schedule);
		scheduleItem.setContent(content);
		scheduleItem.setCheckedTime(date(dataStr, "yyyy-MM-dd"));
		scheduleItem.setWeek(week);
		scheduleItemService.saveEntitiy(scheduleItem);
	}
}

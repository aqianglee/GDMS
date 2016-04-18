package com.aqiang.bsms.service.impl;

import org.junit.Test;

import com.aqiang.bsms.entities.Schedule;
import com.aqiang.bsms.entities.Student;
import com.aqiang.gdms.BaseTest;

public class ScheduleServiceImplTest extends BaseTest {

	@Test
	public void testGetScheduleByStudent() {

		Student student = new Student();
		student.setCompellation("aqianglee");
		studentService.saveEntitiy(student);

		createSchedule(student);

		Schedule schedule = scheduleService.getScheduleByStudent(student);
		isNotNull(schedule);
	}

	private void createSchedule(Student student) {
		Schedule schedule = new Schedule();
		schedule.setStudent(student);
		scheduleService.saveEntitiy(schedule);
	}
}

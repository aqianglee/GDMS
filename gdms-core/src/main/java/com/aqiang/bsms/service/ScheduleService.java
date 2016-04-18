package com.aqiang.bsms.service;

import com.aqiang.bsms.entities.Schedule;
import com.aqiang.bsms.entities.Student;

public interface ScheduleService extends BaseService<Schedule> {
	public Schedule getScheduleByStudent(Student student);
}

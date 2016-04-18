package com.aqiang.bsms.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aqiang.bsms.dao.BaseDao;
import com.aqiang.bsms.entities.Schedule;
import com.aqiang.bsms.entities.Student;
import com.aqiang.bsms.service.ScheduleService;

@Service
@Transactional
public class ScheduleServiceImpl extends BaseServiceImpl<Schedule> implements
		ScheduleService {

	@Resource(name = "scheduleDao")
	@Override
	public void setDao(BaseDao<Schedule> dao) {
		this.dao = dao;
	}

	@Override
	public Schedule getScheduleByStudent(Student student) {
		List<Schedule> schedules = findEntityByJpql(
				"select s from Schedule s where s.student = ?", student);
		return schedules.isEmpty() ? null : schedules.get(0);
	}

}

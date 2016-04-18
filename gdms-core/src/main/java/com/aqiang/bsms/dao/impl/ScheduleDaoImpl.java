package com.aqiang.bsms.dao.impl;

import org.springframework.stereotype.Repository;

import com.aqiang.bsms.dao.ScheduleDao;
import com.aqiang.bsms.entities.Schedule;

@Repository("scheduleDao")
public class ScheduleDaoImpl extends BaseDaoImpl<Schedule> implements
		ScheduleDao {

}

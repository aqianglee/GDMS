package com.aqiang.bsms.dao.impl;

import org.springframework.stereotype.Repository;

import com.aqiang.bsms.dao.ScheduleItemDao;
import com.aqiang.bsms.entities.ScheduleItem;

@Repository("scheduleItemDao")
public class ScheduleItemDaoImpl extends BaseDaoImpl<ScheduleItem> implements
		ScheduleItemDao {

}

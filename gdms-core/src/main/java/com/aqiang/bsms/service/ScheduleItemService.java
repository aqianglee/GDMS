package com.aqiang.bsms.service;

import java.util.List;

import com.aqiang.bsms.entities.File;
import com.aqiang.bsms.entities.Schedule;
import com.aqiang.bsms.entities.ScheduleItem;

public interface ScheduleItemService extends BaseService<ScheduleItem> {
	public List<ScheduleItem> getScheduleItemsBySchedule(Schedule schedule);

	public void delete(ScheduleItem scheduleItem);

	public List<File> getAttachments(ScheduleItem scheduleItem);

	public void addAttachment(ScheduleItem scheduleItem, File file);
}

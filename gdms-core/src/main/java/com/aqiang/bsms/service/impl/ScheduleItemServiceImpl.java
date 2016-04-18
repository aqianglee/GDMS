package com.aqiang.bsms.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aqiang.bsms.dao.BaseDao;
import com.aqiang.bsms.entities.File;
import com.aqiang.bsms.entities.Schedule;
import com.aqiang.bsms.entities.ScheduleItem;
import com.aqiang.bsms.exception.AttachmentNotFindException;
import com.aqiang.bsms.service.FileService;
import com.aqiang.bsms.service.ScheduleItemService;

@Service
@Transactional
public class ScheduleItemServiceImpl extends BaseServiceImpl<ScheduleItem>
		implements ScheduleItemService {

	private static final Logger logger = LoggerFactory
			.getLogger(ScheduleItemServiceImpl.class);

	@Autowired
	private FileService fileService;

	@Resource(name = "scheduleItemDao")
	@Override
	public void setDao(BaseDao<ScheduleItem> dao) {
		this.dao = dao;
	}

	@Override
	public List<ScheduleItem> getScheduleItemsBySchedule(Schedule schedule) {
		return findEntityByJpql(
				"Select s From ScheduleItem s where s.schedule = ?", schedule);
	}

	@Override
	public void delete(ScheduleItem scheduleItem) {
		List<File> attachments = getAttachments(scheduleItem);
		for (File file : attachments) {
			fileService.deleteFile(file);
		}
		deleteEntity(scheduleItem.getId());
	}

	@Override
	public List<File> getAttachments(ScheduleItem scheduleItem) {
		List<File> attachments = new ArrayList<File>();
		String ids = scheduleItem.getAttachments();
		logger.info("The attachments ids is : {}", ids);
		if (StringUtils.isNotBlank(ids)) {
			String[] attachmentsId = ids.split("\\,");
			for (String id : attachmentsId) {
				int parseInt = Integer.parseInt(id);
				File attachment = fileService.findEntity(parseInt);
				if (attachment != null) {
					attachments.add(attachment);
				} else {
					throw new AttachmentNotFindException(
							"not find attachment with id " + id);
				}
			}
		}
		return attachments;
	}

	@Override
	public void addAttachment(ScheduleItem scheduleItem, File file) {
		Integer id = file.getId();
		if (fileService.findEntity(id) == null) {
			throw new AttachmentNotFindException("not find attachment with id "
					+ id);
		}
		String attachments = scheduleItem.getAttachments();
		if (StringUtils.isNotBlank(attachments)) {
			attachments = attachments + "," + id;
		} else {
			attachments = String.valueOf(id);
		}
		scheduleItem.setAttachments(attachments);
	}

}

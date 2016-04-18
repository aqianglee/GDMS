package com.aqiang.bsms.service.impl;

import java.util.List;

import org.junit.Test;

import com.aqiang.bsms.entities.File;
import com.aqiang.bsms.entities.ScheduleItem;
import com.aqiang.bsms.exception.AttachmentNotFindException;
import com.aqiang.gdms.BaseTest;

public class ScheduleItemServiceImplTest3 extends BaseTest {

	@Test
	public void testGetAttachments() {
		ScheduleItem scheduleItem = new ScheduleItem();
		List<File> attachments = scheduleItemService
				.getAttachments(scheduleItem);
		isEq(attachments.size(), 0);
	}

	@Test
	public void testGetAttachments2() {
		File file = createFile("aaa", "aaa");
		ScheduleItem scheduleItem = new ScheduleItem();
		scheduleItemService.addAttachment(scheduleItem, file);
		List<File> attachments = scheduleItemService
				.getAttachments(scheduleItem);
		isEq(attachments.size(), 1);
	}

	private File createFile(String fileName, String filePath) {
		File file = new File();
		file.setFileName(fileName);
		file.setFilePath(filePath);
		fileService.saveEntitiy(file);
		return file;
	}

	@Test
	public void testGetAttachments3() {
		ScheduleItem scheduleItem = new ScheduleItem();
		File file = createFile("aaa", "aaa");
		File file2 = createFile("bbb", "bbb");
		File file3 = createFile("ccc", "ccc");
		scheduleItemService.addAttachment(scheduleItem, file);
		scheduleItemService.addAttachment(scheduleItem, file2);
		scheduleItemService.addAttachment(scheduleItem, file3);
		List<File> attachments = scheduleItemService
				.getAttachments(scheduleItem);
		isEq(attachments.size(), 3);
	}

	@Test(expected = AttachmentNotFindException.class)
	public void testGetAttachments4() {
		File file = new File();
		fileService.saveEntitiy(file);
		ScheduleItem scheduleItem = new ScheduleItem();
		scheduleItem.setAttachments("300");
		List<File> attachments = scheduleItemService
				.getAttachments(scheduleItem);
		isEq(attachments.size(), 0);
	}
}

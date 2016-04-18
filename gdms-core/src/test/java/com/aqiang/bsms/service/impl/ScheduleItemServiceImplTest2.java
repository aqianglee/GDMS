package com.aqiang.bsms.service.impl;

import java.util.List;

import org.junit.Test;

import com.aqiang.bsms.entities.File;
import com.aqiang.bsms.entities.ScheduleItem;
import com.aqiang.bsms.exception.AttachmentNotFindException;
import com.aqiang.gdms.BaseTest;

public class ScheduleItemServiceImplTest2 extends BaseTest {

	@Test
	public void testAddAttachment() {
		ScheduleItem scheduleItem = new ScheduleItem();
		File file = createFile("aaa", "aaa");
		fileService.saveEntitiy(file);
		scheduleItemService.addAttachment(scheduleItem, file);
		List<File> attachments = scheduleItemService
				.getAttachments(scheduleItem);
		isEq(attachments.size(), 1);
		isEq(attachments.get(0).getFileName(), "aaa");
		isEq(attachments.get(0).getFilePath(), "aaa");
	}

	@Test
	public void testAddAttachment2() {
		ScheduleItem scheduleItem = new ScheduleItem();
		File file = createFile("aaa", "aaa");
		File file2 = createFile("bbb", "bbb");
		File file3 = createFile("ccc", "ccc");
		fileService.saveEntitiy(file);
		fileService.saveEntitiy(file2);
		fileService.saveEntitiy(file3);
		scheduleItemService.addAttachment(scheduleItem, file);
		scheduleItemService.addAttachment(scheduleItem, file2);
		scheduleItemService.addAttachment(scheduleItem, file3);
		List<File> attachments = scheduleItemService
				.getAttachments(scheduleItem);
		isEq(attachments.size(), 3);
		isEq(attachments.get(0).getFileName(), "aaa");
		isEq(attachments.get(0).getFilePath(), "aaa");
		isEq(attachments.get(1).getFileName(), "bbb");
		isEq(attachments.get(1).getFilePath(), "bbb");
		isEq(attachments.get(2).getFileName(), "ccc");
		isEq(attachments.get(2).getFilePath(), "ccc");
	}

	@Test(expected = AttachmentNotFindException.class)
	public void testAddAttachment3() {
		ScheduleItem scheduleItem = new ScheduleItem();
		File file = createFile("aaa", "bbbb");
		file.setId(10);
		scheduleItemService.addAttachment(scheduleItem, file);
	}

	private File createFile(String fileName, String filePath) {
		File file = new File();
		file.setFileName(fileName);
		file.setFilePath(filePath);
		return file;
	}

}

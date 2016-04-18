package com.aqiang.bsms.service.impl;

import org.junit.Test;

import com.aqiang.bsms.entities.File;
import com.aqiang.bsms.entities.Parameter;
import com.aqiang.bsms.entities.ParameterKey;
import com.aqiang.bsms.entities.ScheduleItem;
import com.aqiang.gdms.BaseTest;

public class ScheduleItemServiceImplTest4 extends BaseTest {

	@Test
	public void testDelete() {
		Parameter parameter = new Parameter();
		parameter.setName(ParameterKey.FILE_ROOT_DIR);
		parameter.setValue("D://aaa/");
		parameterService.saveEntitiy(parameter);

		ScheduleItem scheduleItem = new ScheduleItem();
		File file = createFile("aaa", "aaa");
		scheduleItemService.addAttachment(scheduleItem, file);
		scheduleItemService.saveEntitiy(scheduleItem);
		isEq(fileService.getAll().size(), 1);
		isEq(scheduleItemService.getAll().size(), 1);
		scheduleItemService.delete(scheduleItem);
		isEq(fileService.getAll().size(), 0);
		isEq(scheduleItemService.getAll().size(), 0);
	}

	@Test
	public void testDelete2() {
		Parameter parameter = new Parameter();
		parameter.setName(ParameterKey.FILE_ROOT_DIR);
		parameter.setValue("D://aaa/");
		parameterService.saveEntitiy(parameter);

		ScheduleItem scheduleItem = new ScheduleItem();
		File file = createFile("aaa", "aaa");
		File file2 = createFile("bbb", "bbb");
		File file3 = createFile("ccc", "ccc");
		scheduleItemService.addAttachment(scheduleItem, file);
		scheduleItemService.addAttachment(scheduleItem, file2);
		scheduleItemService.addAttachment(scheduleItem, file3);
		scheduleItemService.saveEntitiy(scheduleItem);
		isEq(fileService.getAll().size(), 3);
		isEq(scheduleItemService.getAll().size(), 1);
		scheduleItemService.delete(scheduleItem);
		isEq(fileService.getAll().size(), 0);
		isEq(scheduleItemService.getAll().size(), 0);
	}

	@Test
	public void testDelete3() {
		ScheduleItem scheduleItem = new ScheduleItem();
		scheduleItemService.saveEntitiy(scheduleItem);
		scheduleItemService.delete(scheduleItem);
	}

	private File createFile(String fileName, String filePath) {
		File file = new File();
		file.setFileName(fileName);
		file.setFilePath(filePath);
		fileService.saveEntitiy(file);
		return file;
	}
}

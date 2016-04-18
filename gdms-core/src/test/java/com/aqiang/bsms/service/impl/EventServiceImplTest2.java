package com.aqiang.bsms.service.impl;

import java.util.List;

import org.junit.Test;

import com.aqiang.bsms.entities.College;
import com.aqiang.bsms.entities.Event;
import com.aqiang.bsms.entities.File;
import com.aqiang.bsms.entities.Parameter;
import com.aqiang.bsms.entities.ParameterKey;
import com.aqiang.bsms.entities.WorkflowStatus;
import com.aqiang.gdms.BaseTest;

public class EventServiceImplTest2 extends BaseTest {

	@Test
	public void testDeleteSelectSubjectGuideFile() {
		Parameter parameter = new Parameter();
		parameter.setName(ParameterKey.FILE_ROOT_DIR);
		parameter.setValue("D://aaa/");
		parameterService.saveEntitiy(parameter);
		College college = new College();
		college.setCollegeName("计算机技术与应用系");
		collegeService.saveEntitiy(college);
		File file = new File();
		file.setFileName("test.txt");
		file.setFilePath("testFile/test.txt");
		fileService.saveEntitiy(file);
		Event event = new Event();
		event.setYear("2015");
		event.setWorkFlowStatus(WorkflowStatus.BEGIN);
		event.setActive(true);
		event.setSelectSubjectGuide(file);
		eventService.saveEntitiy(event);
		List<File> files = fileService.getAll();
		isTrue(files.size() == 1);
		eventService.deleteSubjectsSelectGuideFile(event);
		files = fileService.getAll();
		isTrue(files.size() == 0);
	}

}

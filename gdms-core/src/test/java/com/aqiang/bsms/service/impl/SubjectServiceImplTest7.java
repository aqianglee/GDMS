package com.aqiang.bsms.service.impl;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.junit.Test;

import com.aqiang.bsms.entities.File;
import com.aqiang.bsms.entities.Parameter;
import com.aqiang.bsms.entities.ParameterKey;
import com.aqiang.bsms.entities.Subject;
import com.aqiang.gdms.BaseTest;

public class SubjectServiceImplTest7 extends BaseTest {

	@Test
	public void testDeleteSubject() {
		Subject subject = new Subject();
		subject.setTopicChoosingWay("information");
		subjectService.saveEntitiy(subject);
		isTrue(subjectService.getAll().size() > 0);
		subjectService.deleteSubject(subject);
		isTrue(subjectService.getAll().size() == 0);
	}

	@Test
	public void testDeleteSubject2() {
		Parameter parameter = new Parameter();
		parameter.setName(ParameterKey.FILE_ROOT_DIR);
		parameter.setValue("D://aaa");
		parameterService.saveEntitiy(parameter);
		File file = new File();
		file.setFileName("aaa.doc");
		fileService.saveEntitiy(file);
		Subject subject = new Subject();
		subject.setFile(file);
		subject.setTopicChoosingWay("information");
		subjectService.saveEntitiy(subject);
		List<Subject> all = subjectService.getAll();
		isTrue(all.size() > 0);
		File file2 = all.get(0).getFile();
		isTrue(file2 != null);
		isTrue("aaa.doc".equals(file2.getFileName()));
		logger.info("The file name is {}", file2.getFileName());
		subjectService.deleteSubject(subject);
		all = subjectService.getAll();
		isTrue(all.size() == 0);
	}

	@Test
	public void testDeleteSubject3() throws IOException {
		Parameter parameter = new Parameter();
		parameter.setName(ParameterKey.FILE_ROOT_DIR);
		parameter.setValue("D://aaa/");
		parameterService.saveEntitiy(parameter);
		String dir = parameter.getValue();
		String filePath = "testFile/";
		java.io.File path = new java.io.File(dir + filePath);
		if (!path.isDirectory()) {
			path.mkdirs();
		}
		File file = new File();
		String fileName = "test.txt";
		file.setFileName(fileName);
		file.setFilePath(filePath + fileName);
		fileService.saveEntitiy(file);
		java.io.File f = new java.io.File(dir + filePath + fileName);
		FileOutputStream out = new FileOutputStream(f);
		out.write("I Love You".getBytes());
		out.close();
		String tempFile = parameterService.getParameterByName(ParameterKey.FILE_ROOT_DIR).getValue()
				+ file.getFilePath();
		java.io.File temp = new java.io.File(tempFile);
		isTrue(temp.isFile());
		isTrue(fileService.getAll().get(0) != null);
		Subject subject = new Subject();
		subject.setFile(file);
		subject.setTopicChoosingWay("information");
		subjectService.saveEntitiy(subject);
		List<Subject> all = subjectService.getAll();
		isTrue(all.size() == 1);
		File file2 = all.get(0).getFile();
		isTrue(file2 != null);
		isTrue("test.txt".equals(file2.getFileName()));
		subjectService.deleteSubject(subject);
		all = subjectService.getAll();
		isTrue(all.size() == 0);
		isTrue(fileService.getAll().size() == 0);
		temp = new java.io.File(tempFile);
		isTrue(!temp.isFile());
	}

}

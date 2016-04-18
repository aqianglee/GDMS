package com.aqiang.bsms.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.Test;

import com.aqiang.bsms.entities.File;
import com.aqiang.bsms.entities.Parameter;
import com.aqiang.bsms.entities.ParameterKey;
import com.aqiang.bsms.entities.Subject;
import com.aqiang.gdms.BaseTest;

public class DownloadServiceImplTest extends BaseTest {

	@Test
	public void testDownloadSubject() throws IOException {
		Parameter parameter = new Parameter();
		parameter.setName(ParameterKey.FILE_ROOT_DIR);
		parameter.setValue("D://aaa/");
		parameterService.saveEntitiy(parameter);
		Subject subject = new Subject();
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
		java.io.File f = new java.io.File(dir + filePath + fileName);
		FileOutputStream out = new FileOutputStream(f);
		out.write("I Love You".getBytes());
		out.close();
		fileService.saveEntitiy(file);
		subject.setFile(file);
		subjectService.saveEntitiy(subject);
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		downloadService.downloadSubject(subject, output);
		isTrue(output.size() > 0);
		out = new FileOutputStream(new java.io.File("target/test.txt"));
		downloadService.downloadSubject(subject, out);
		java.io.File file2 = new java.io.File("target/test.txt");
		isTrue(file2.isFile());
		isTrue(file2.length() > 0);
	}
}

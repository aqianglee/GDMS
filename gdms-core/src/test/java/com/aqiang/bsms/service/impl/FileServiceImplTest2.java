package com.aqiang.bsms.service.impl;

import java.io.IOException;

import org.junit.Test;

import com.aqiang.bsms.entities.File;
import com.aqiang.bsms.entities.Parameter;
import com.aqiang.bsms.entities.ParameterKey;
import com.aqiang.gdms.BaseTest;

public class FileServiceImplTest2 extends BaseTest {

	@Test
	public void testDeleteFileNoFile() throws IOException {

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
		String tempFile = parameterService.getParameterByName(ParameterKey.FILE_ROOT_DIR).getValue()
				+ file.getFilePath();
		java.io.File temp = new java.io.File(tempFile);
		isTrue(!temp.isFile());
		isTrue(fileService.getAll().get(0) != null);
		fileService.deleteFile(file);
		java.io.File temp2 = new java.io.File(tempFile);
		isTrue(!temp2.isFile());
		isTrue(fileService.getAll().size() == 0);
	}

}

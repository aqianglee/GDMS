package com.aqiang.bsms.service.impl;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aqiang.bsms.dao.BaseDao;
import com.aqiang.bsms.entities.File;
import com.aqiang.bsms.entities.ParameterKey;
import com.aqiang.bsms.service.FileService;
import com.aqiang.bsms.service.ParameterService;

@Service
@Transactional
public class FileServiceImpl extends BaseServiceImpl<File> implements
		FileService {

	@Autowired
	private ParameterService parameterService;

	@Resource(name = "fileDao")
	@Override
	public void setDao(BaseDao<File> dao) {
		this.dao = dao;
	}

	@Override
	public void deleteFile(File file) {
		String dir = parameterService.getParameterByName(
				ParameterKey.FILE_ROOT_DIR).getValue();
		String path = file.getFilePath();
		java.io.File f = new java.io.File(dir + path);
		if (f.isFile()) {
			f.delete();
		}
		deleteEntity(file.getId());
	}

	@Override
	public String getPostfixName(String fileName) {
		String[] strings = fileName.split("\\.");
		if (StringUtils.isBlank(fileName)) {
			return "";
		}
		if (strings.length == 1) {
			return "";
		}
		return strings[strings.length - 1];
	}

}

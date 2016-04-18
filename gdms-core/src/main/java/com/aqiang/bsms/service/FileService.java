package com.aqiang.bsms.service;

import com.aqiang.bsms.entities.File;

public interface FileService extends BaseService<File> {

	public void deleteFile(File file);

	public String getPostfixName(String fileName);

}

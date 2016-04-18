package com.aqiang.bsms.service.impl;

import java.util.UUID;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aqiang.bsms.dao.BaseDao;
import com.aqiang.bsms.entities.File;
import com.aqiang.bsms.entities.ParameterKey;
import com.aqiang.bsms.entities.Task;
import com.aqiang.bsms.service.FileService;
import com.aqiang.bsms.service.GenerateFile;
import com.aqiang.bsms.service.ParameterService;
import com.aqiang.bsms.service.TaskService;

@Service
@Transactional
public class TaskServiceImpl extends BaseServiceImpl<Task> implements
		TaskService {

	@Autowired
	private FileService fileService;
	@Autowired
	private GenerateFile generateFile;
	@Autowired
	private ParameterService parameterService;

	@Resource(name = "taskDao")
	@Override
	public void setDao(BaseDao<Task> dao) {
		this.dao = dao;
	}

	@Override
	public void editTask(Task task) {
		File file = task.getFile();
		task.setFile(null);
		if (file != null) {
			fileService.deleteFile(file);
		}
		File f = saveModelAsFile(task);
		task.setFile(f);
		margeEntity(task);
	}

	private File saveModelAsFile(Task task) {
		String filePath = "files/subject/";
		String fileName = UUID.randomUUID() + ".doc";
		String root = parameterService.getParameterByName(
				ParameterKey.FILE_ROOT_DIR).getValue();
		java.io.File dir = new java.io.File(root + filePath);
		if (!dir.isDirectory()) {
			dir.mkdirs();
		}
		// TODO : start new thread
		generateFile.generateTask(task, root + filePath + fileName);
		File file = new File();
		file.setFileName(String.format("%s-%s-%s.doc", task.getTeacher()
				.getCompellation(), task.getStudent().getCompellation(), task
				.getSubject().getTopicChoosingWay()));
		file.setFilePath(filePath + fileName);
		fileService.saveEntitiy(file);
		return file;
	}
}

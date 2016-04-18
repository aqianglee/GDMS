package com.aqiang.bsms.service;

import com.aqiang.bsms.entities.Task;

public interface TaskService extends BaseService<Task> {

	public void editTask(Task task);
}

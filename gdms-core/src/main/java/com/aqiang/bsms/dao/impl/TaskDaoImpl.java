package com.aqiang.bsms.dao.impl;

import org.springframework.stereotype.Repository;

import com.aqiang.bsms.dao.TaskDao;
import com.aqiang.bsms.entities.Task;

@Repository("taskDao")
public class TaskDaoImpl extends BaseDaoImpl<Task> implements TaskDao {

}

package com.aqiang.bsms.service;

import java.util.List;

import com.aqiang.bsms.entities.College;
import com.aqiang.bsms.entities.Teacher;
import com.aqiang.bsms.exception.UsernameHasUsedException;

public interface TeacherService extends BaseService<Teacher> {

	public void addNewTeacher(Teacher teacher) throws UsernameHasUsedException;

	public Teacher getTeacherByTeacherCompellation(String compellation);

	public List<Teacher> getTeachersByCollege(College myCollege);

	public String getJobType(String jobType);
}

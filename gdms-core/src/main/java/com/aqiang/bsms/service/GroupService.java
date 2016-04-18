package com.aqiang.bsms.service;

import java.util.List;

import com.aqiang.bsms.entities.College;
import com.aqiang.bsms.entities.Event;
import com.aqiang.bsms.entities.Group;
import com.aqiang.bsms.entities.Student;
import com.aqiang.bsms.entities.Teacher;

public interface GroupService extends BaseService<Group> {

	public List<Group> getGroupByCollege(College college);

	public List<Group> getGroupByCollegeAndEvent(College college, Event event);

	public Group getGroupByStudent(Student student);

	public List<Group> getGroupsByTeacher(Teacher teacher);
}

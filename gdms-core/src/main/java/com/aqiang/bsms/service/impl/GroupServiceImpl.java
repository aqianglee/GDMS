package com.aqiang.bsms.service.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aqiang.bsms.dao.BaseDao;
import com.aqiang.bsms.entities.College;
import com.aqiang.bsms.entities.Event;
import com.aqiang.bsms.entities.Group;
import com.aqiang.bsms.entities.Student;
import com.aqiang.bsms.entities.Teacher;
import com.aqiang.bsms.service.GroupService;
import com.aqiang.bsms.service.StudentService;

@Service("groupService")
@Transactional
public class GroupServiceImpl extends BaseServiceImpl<Group> implements GroupService {

	@Autowired
	private StudentService studentService;

	@Resource(name = "groupDao")
	@Override
	public void setDao(BaseDao<Group> dao) {
		this.dao = dao;
	}

	@Override
	public List<Group> getGroupByCollege(College college) {
		return findEntityByJpql("From Group g where g.college = ?", college);
	}

	@Override
	public List<Group> getGroupByCollegeAndEvent(College college, Event event) {
		if (event != null) {
			return findEntityByJpql("From Group g where g.college = ? and event = ?", college, event);
		}
		return getGroupByCollege(college);
	}

	@Override
	public Group getGroupByStudent(Student student) {
		return studentService.findEntity(student.getId()).getGroup();
	}

	@Override
	public List<Group> getGroupsByTeacher(Teacher teacher) {
		return null;
	}
}

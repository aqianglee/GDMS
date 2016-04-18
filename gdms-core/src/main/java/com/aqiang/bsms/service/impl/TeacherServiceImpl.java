package com.aqiang.bsms.service.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.aqiang.bsms.dao.BaseDao;
import com.aqiang.bsms.entities.College;
import com.aqiang.bsms.entities.JobType;
import com.aqiang.bsms.entities.Teacher;
import com.aqiang.bsms.entities.User;
import com.aqiang.bsms.exception.UsernameHasUsedException;
import com.aqiang.bsms.service.TeacherService;
import com.aqiang.bsms.service.UserService;
import com.aqiang.bsms.utils.Md5Util;

@Service("teacherService")
@Transactional
public class TeacherServiceImpl extends BaseServiceImpl<Teacher> implements TeacherService {
	@Autowired
	private UserService userService;

	@Override
	@Resource(name = "teacherDao")
	public void setDao(BaseDao<Teacher> dao) {
		this.dao = dao;
	}

	@Override
	public void addNewTeacher(Teacher teacher) throws UsernameHasUsedException {
		User user = userService.getUserByUsername(teacher.getUsername());
		if (user != null) {
			throw new UsernameHasUsedException("The username has used");
		}
		teacher.setPassword(Md5Util.md5("123456"));
		saveEntitiy(teacher);
	}

	@Override
	public Teacher getTeacherByTeacherCompellation(String compellation) {
		String jpql = "From Teacher t where t.compellation = ?";
		List<Teacher> teachers = findEntityByJpql(jpql, compellation);
		return teachers.isEmpty() ? null : teachers.get(0);
	}

	@Override
	public List<Teacher> getTeachersByCollege(College college) {
		String jpql = "From Teacher t where t.college = ?";
		return findEntityByJpql(jpql, college);
	}

	@Override
	public String getJobType(String jobType) {
		if (jobType == null || StringUtils.isEmpty(jobType)) {
			return "";
		}
		if (jobType.equals(JobType.ASSISTANT)) {
			return "助教";
		}
		if (jobType.equals(JobType.LECTURER)) {
			return "讲师";
		}
		if (jobType.equals(JobType.ASSOCIATE_PROFESSOR)) {
			return "副教授";
		}
		return "教授";
	}
}

package com.aqiang.bsms.service.impl;

import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aqiang.bsms.dao.BaseDao;
import com.aqiang.bsms.entities.College;
import com.aqiang.bsms.entities.Event;
import com.aqiang.bsms.entities.Group;
import com.aqiang.bsms.entities.Student;
import com.aqiang.bsms.entities.Subject;
import com.aqiang.bsms.entities.User;
import com.aqiang.bsms.exception.UsernameHasUsedException;
import com.aqiang.bsms.service.MessageService;
import com.aqiang.bsms.service.QuestionAndAnswerService;
import com.aqiang.bsms.service.StudentService;
import com.aqiang.bsms.service.UserService;
import com.aqiang.bsms.utils.Md5Util;

@Service("studentService")
@Transactional
public class StudentServiceImpl extends BaseServiceImpl<Student> implements StudentService {

	@Autowired
	private QuestionAndAnswerService questionAndAnswerService;
	@Autowired
	private MessageService messageService;
	@Autowired
	private UserService userService;
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

	@Resource(name = "studentDao")
	@Override
	public void setDao(BaseDao<Student> dao) {
		this.dao = dao;
	}

	public void addNewStudent(Student student) throws UsernameHasUsedException {
		User user = userService.getUserByUsername(student.getUsername());
		if (user != null) {
			throw new UsernameHasUsedException("The username has used");
		}
		student.setPassword(Md5Util.md5("123456"));
		this.saveEntitiy(student);

	}

	@Override
	public List<Student> getStudentsByCollege(College college) {
		return findEntityByJpql("From Student s where s.college = ?", college);
	}

	@Override
	public void deleteUnconfirmedSubjects(Student student, Subject subject) {
		List<Subject> subjects = student.getSubjects();
		Iterator<Subject> iterator = subjects.iterator();
		while (iterator.hasNext()) {
			Subject s = iterator.next();
			if (!s.getId().equals(subject.getId())) {
				s.getStudents().remove(student);
				iterator.remove();
			}
		}
	}

	@Override
	public void deleteUnConfirmedStudents(Student student, Subject subject) {
		List<Student> students = subject.getStudents();
		Iterator<Student> iterator = students.iterator();
		while (iterator.hasNext()) {
			Student s = iterator.next();
			if (!s.getId().equals(student.getId())) {
				s.getSubjects().remove(subject);
				iterator.remove();
			}
		}
	}

	@Override
	public boolean isConfirmByTeacher(Student student) {
		if (student == null) {
			return false;
		}
		return student.getGroup() != null;
	}

	@Override
	public List<Student> getStudentsByGroup(Group group) {
		return findEntityByJpql("From Student s where s.group = ?", group);
	}

	@Override
	public List<Student> getStudentsByCollegeAndCurrnetEvent(College college, Event currentEvent) {
		if (currentEvent == null) {
			return getStudentsByCollege(college);
		}
		return findEntityByJpql("From Student s where s.college = ? and event = ?", college, currentEvent);
	}

	@Override
	public boolean hasSelected(Student student, Subject subject) {
		Student stu = findEntity(student.getId());
		List<Subject> subjects = stu.getSubjects();
		for (Subject s : subjects) {
			if (s.getId().equals(subject.getId())) {
				return true;
			}
		}
		return false;
	}
}

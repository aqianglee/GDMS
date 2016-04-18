package com.aqiang.bsms.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aqiang.bsms.dao.BaseDao;
import com.aqiang.bsms.dao.CollegeManagerDao;
import com.aqiang.bsms.entities.College;
import com.aqiang.bsms.entities.Event;
import com.aqiang.bsms.entities.File;
import com.aqiang.bsms.entities.Group;
import com.aqiang.bsms.entities.ParameterKey;
import com.aqiang.bsms.entities.Student;
import com.aqiang.bsms.entities.Subject;
import com.aqiang.bsms.entities.User;
import com.aqiang.bsms.entities.Work;
import com.aqiang.bsms.service.EventService;
import com.aqiang.bsms.service.FileService;
import com.aqiang.bsms.service.GenerateFile;
import com.aqiang.bsms.service.GroupService;
import com.aqiang.bsms.service.MessageService;
import com.aqiang.bsms.service.ParameterService;
import com.aqiang.bsms.service.StudentService;
import com.aqiang.bsms.service.SubjectService;
import com.aqiang.bsms.service.TeacherService;

@Service("subjectService")
@Transactional
public class SubjectServiceImpl extends BaseServiceImpl<Subject> implements SubjectService {
	@Autowired
	private CollegeManagerDao collegeManagerDao;
	@Autowired
	private TeacherService teacherService;
	@Autowired
	private StudentService studentService;
	@Autowired
	private MessageService messageService;
	@Autowired
	private GroupService groupService;
	@Autowired
	private ParameterService parameterService;
	@Autowired
	private FileService fileService;
	@Autowired
	private EventService eventService;
	@Autowired
	private GenerateFile generateFile;
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(SubjectServiceImpl.class);

	@Resource(name = "subjectDao")
	@Override
	public void setDao(BaseDao<Subject> dao) {
		this.dao = dao;
	}

	@Override
	public List<Subject> getMyAllSubject(User user) {
		String jpql = "From Subject s where s.user = ?";
		List<Subject> subjects = this.findEntityByJpql(jpql, user);
		return subjects;
	}

	private File saveModelAsFile(Subject subject) {
		String filePath = "files/subject/";
		String fileName = UUID.randomUUID() + ".doc";
		User user = subject.getUser();
		String root = parameterService.getParameterByName(ParameterKey.FILE_ROOT_DIR).getValue();
		java.io.File dir = new java.io.File(root + filePath);
		if (!dir.isDirectory()) {
			dir.mkdirs();
		}
		// TODO : start new thread
		generateFile.generateSubject(subject, root + filePath + fileName);
		File file = new File();
		file.setFileName(user.getCompellation() + "-" + subject.getTopicChoosingWay() + ".doc");
		file.setFilePath(filePath + fileName);
		file.setUser(user);
		fileService.saveEntitiy(file);
		return file;
	}

	// TODO : add Test for this method
	@Override
	public void editSubject(Subject subject) {
		File file = subject.getFile();
		subject.setFile(null);
		if (file != null) {
			fileService.deleteFile(file);
		}
		File f = saveModelAsFile(subject);
		subject.setFile(f);
		margeEntity(subject);
	}

	@Override
	public void deleteSubject(Subject subject) {
		File file = subject.getFile();
		if (file != null) {
			fileService.deleteFile(file);
		}
		this.deleteEntity(subject.getId());
	}

	@Override
	public List<Subject> getAllSubjectByCollege(College college) {
		return findEntityByJpql("From Subject s where s.college = ?", college);
	}

	@Override
	public void addStudentToSelectList(Student student, Subject subject) {
		student = studentService.findEntity(student.getId());
		subject = findEntity(subject.getId());
		subject.getStudents().add(student);
		student.getSubjects().add(subject);
		margeEntity(subject);
		studentService.margeEntity(student);
	}

	@Override
	public Subject getSubjectById(int id) {
		Subject subject = findEntity(id);
		subject.getStudents().size();
		return subject;
	}

	@Override
	public void confirmeStudent(Subject subject, Student student) {
		student = studentService.findEntity(student.getId());
		subject = findEntity(subject.getId());
		studentService.deleteUnconfirmedSubjects(student, subject);
		studentService.deleteUnConfirmedStudents(student, subject);
		Group group = new Group();
		group.setSubject(subject);
		group.setCollege(student.getCollege());
		group.setEvent(student.getEvent());
		group.setTeacher(teacherService.getTeacherByTeacherCompellation(subject.getDon()));
		student.setGroup(group);
		groupService.saveEntitiy(group);
		student.setWork(Work.GROUP_LEADER);
		subject.setGroup(group);
	}

	@Override
	public void passAll(List<Subject> subjects) {
		for (Subject subject : subjects) {
			subject.setCommented(true);
			this.margeEntity(subject);
		}
	}

	@Override
	public List<Subject> getAllSubjects(College college, Event currentEvent) {
		if (currentEvent == null) {
			return getAllSubjectByCollege(college);
		}
		return findEntityByJpql("From Subject s where s.college = ? and event = ?", college, currentEvent);
	}

	@Override
	public List<Subject> getSelectAbleSubject(College college) {
		Event event = eventService.getCurrentEvent(college);
		List<Subject> allSubjects = getAllSubjects(college, event);
		List<Subject> subjects = new ArrayList<Subject>();
		for (Subject subject : allSubjects) {
			if (subject.isCommented() != null && subject.isCommented()) {
				subjects.add(subject);
			}
		}
		return subjects;
	}

	@Override
	public List<Student> getSelectedStudents(Subject subject) {
		List<Student> students = findEntity(subject.getId()).getStudents();
		students.size();
		return students;
	}

}

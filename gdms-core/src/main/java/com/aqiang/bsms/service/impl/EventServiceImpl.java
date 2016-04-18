package com.aqiang.bsms.service.impl;

import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aqiang.bsms.dao.BaseDao;
import com.aqiang.bsms.entities.College;
import com.aqiang.bsms.entities.Event;
import com.aqiang.bsms.entities.File;
import com.aqiang.bsms.entities.Group;
import com.aqiang.bsms.entities.ParameterKey;
import com.aqiang.bsms.entities.Subject;
import com.aqiang.bsms.entities.WorkflowStatus;
import com.aqiang.bsms.service.EventService;
import com.aqiang.bsms.service.FileService;
import com.aqiang.bsms.service.GenerateFile;
import com.aqiang.bsms.service.GroupService;
import com.aqiang.bsms.service.ParameterService;
import com.aqiang.bsms.service.SubjectService;

@Service
@Transactional
public class EventServiceImpl extends BaseServiceImpl<Event> implements EventService {
	private static final Logger logger = LoggerFactory.getLogger(EventServiceImpl.class);
	@Autowired
	private FileService fileService;
	@Autowired
	private ParameterService parameterService;
	@Autowired
	private SubjectService subjectService;
	@Autowired
	private GenerateFile generateFile;
	@Autowired
	private GroupService groupService;

	@Resource(name = "eventDao")
	@Override
	public void setDao(BaseDao<Event> dao) {
		this.dao = dao;
	}

	@Override
	public Event getCurrentEvent(College college) {
		String jpql = "From Event e where e.college = ? and e.active = ?";
		List<Event> events = findEntityByJpql(jpql, college, true);
		return events.isEmpty() ? null : events.get(0);
	}

	@Override
	public Event getEventByYear(String year, College college) {
		String jpql = "From Event e where e.year = ? and e.college = ?";
		List<Event> events = findEntityByJpql(jpql, year, college);
		return events.isEmpty() ? null : events.get(0);
	}

	@Override
	public boolean createNewEvent(Event event) {
		Event eventByYear = getEventByYear(event.getYear(), event.getCollege());
		if (eventByYear != null) {
			return false;
		}
		event.setWorkFlowStatus(WorkflowStatus.BEGIN);
		event.setActive(false);
		saveEntitiy(event);
		return true;
	}

	@Override
	public List<Event> getEvents(College college) {
		String jpql = "From Event e where e.college = ?";
		return findEntityByJpql(jpql, college);
	}

	@Override
	public void changeToActive(Event event) {
		event.setActive(true);
		margeEntity(event);
	}

	@Override
	public void changeWorkFlowStatus(Event event, String status) {
		event.setWorkFlowStatus(status);
		margeEntity(event);
	}

	@Override
	public void deleteSubjectsSelectGuideFile(Event event) {
		File file = event.getSelectSubjectGuide();
		if (file != null) {
			logger.info("has subject select guide file");
			event.setSelectSubjectGuide(null);
			margeEntity(event);
			fileService.deleteFile(file);
		}
	}

	@Override
	public void genereteSelectsSubjectGuideFile(College college) {
		String filePath = "files/guids/";
		String root = getFileRootDir(filePath);
		Event event = getCurrentEvent(college);
		List<Subject> subjects = subjectService.getAllSubjects(college, event);
		logger.info("get {} subjects", subjects.size());
		String fileName = UUID.randomUUID() + ".doc";
		// TODO : start new thread
		generateFile.generateSubjectSelectGuide(college, event, subjects, root + filePath + fileName);
		File file = new File();
		file.setFileName(String.format("%s-%s届本科生毕业论文（设计）选题方向指南.doc", college.getCollegeName(), event.getYear()));
		file.setFilePath(filePath + fileName);
		fileService.saveEntitiy(file);
		event.setSelectSubjectGuide(file);
		margeEntity(event);
	}

	private String getFileRootDir(String filePath) {
		String root = parameterService.getParameterByName(ParameterKey.FILE_ROOT_DIR).getValue();
		java.io.File dir = new java.io.File(root + filePath);
		if (!dir.isDirectory()) {
			dir.mkdirs();
		}
		return root;
	}

	@Override
	public void updateSubjectsSelectGuideFile(College college) {
		Event currentEvent = getCurrentEvent(college);
		logger.info("get current and current year is {}", currentEvent.getYear());
		deleteSubjectsSelectGuideFile(currentEvent);
		genereteSelectsSubjectGuideFile(college);
	}

	@Override
	public void deleteSubjectsSelectSummaryFile(Event event) {
		File file = event.getSelectSubjectSummary();
		if (file != null) {
			event.setSelectSubjectSummary(null);
			margeEntity(event);
			fileService.deleteFile(file);
		}
	}

	@Override
	public void genereteSubjectsSelectSummaryFile(College college) {
		String filePath = "files/Summary/";
		String root = getFileRootDir(filePath);
		Event event = getCurrentEvent(college);
		String fileName = UUID.randomUUID() + ".doc";
		List<Group> groups = groupService.getGroupByCollegeAndEvent(college, event);
		// TODO : start new thread
		generateFile.generateSubjectSelectSummary(college, event, groups, root + filePath + fileName);
		File file = new File();
		file.setFileName(String.format("%s-%s届本科生毕业论文（设计）选题方向指南.doc", college.getCollegeName(), event.getYear()));
		file.setFilePath(filePath + fileName);
		fileService.saveEntitiy(file);
		event.setSelectSubjectSummary(file);
		margeEntity(event);
	}

	@Override
	public void updateSubjectsSelectSummaryFile(College college) {
		Event currentEvent = getCurrentEvent(college);
		deleteSubjectsSelectSummaryFile(currentEvent);
		genereteSubjectsSelectSummaryFile(college);
	}

}

package com.aqiang.bsms.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.aqiang.bsms.entities.College;
import com.aqiang.bsms.entities.Event;
import com.aqiang.bsms.entities.Group;
import com.aqiang.bsms.entities.JobType;
import com.aqiang.bsms.entities.Specialty;
import com.aqiang.bsms.entities.Student;
import com.aqiang.bsms.entities.Subject;
import com.aqiang.bsms.entities.SubjectSource;
import com.aqiang.bsms.entities.SubjectType;
import com.aqiang.gdms.BaseTest;

public class GenerateFileImplTest3 extends BaseTest {

	@Test
	public void testGenerateSubjectSelectSummary() {
		College college = new College();
		college.setCollegeName("计算机技术与应用系");
		collegeService.saveEntitiy(college);
		Event currentEvent = new Event();
		currentEvent.setYear("2016");
		currentEvent.setBeginDate(date("2016-3", "yyyy-MM"));
		currentEvent.setEndDate(date("2016-6", "yyyy-MM"));
		eventService.saveEntitiy(currentEvent);
		List<Group> groups = new ArrayList<Group>();
		Specialty specialty = new Specialty();
		specialty.setName("计算机科学与技术2012级");
		specialtyService.saveEntitiy(specialty);
		Group group = createGrpup(createSubject(currentEvent, "信息技术方向——基于Office插件的信息填报辅助工具设计与实现", "王晓英",
				JobType.PROFESSOR, specialty, SubjectType.DESIGN, SubjectSource.TEACHING));
		Group group2 = createGrpup(createSubject(currentEvent, "信息技术方向——基于Linux环境的多类典型应用能耗模型研究", "王晓英",
				JobType.PROFESSOR, specialty, SubjectType.PAPER, SubjectSource.SCIENTIFIC_RESEARCH));
		Group group3 = createGrpup(createSubject(currentEvent, "信息技术方向——基于敏捷开发的校园通购物系统", "曹腾飞", JobType.ASSISTANT,
				specialty, SubjectType.DESIGN, SubjectSource.PRODUCTION));
		Group group4 = createGrpup(createSubject(currentEvent, "信息技术方向——3D打印中支撑结构的自动生成", "杜正君", JobType.ASSISTANT,
				specialty, SubjectType.DESIGN, SubjectSource.PRODUCTION));
		Group group5 = createGrpup(createSubject(currentEvent, "信息技术方向——内容感知的图像缩放处理", "杜正君", JobType.ASSISTANT,
				specialty, SubjectType.DESIGN, SubjectSource.SCIENTIFIC_RESEARCH));
		Group group6 = createGrpup(createSubject(currentEvent, "信息技术方向——基于WEBGL互助土族故土园的研究与实现", "李鑫丽", JobType.LECTURER,
				specialty, SubjectType.DESIGN, SubjectSource.PRODUCTION));

		groups.add(group);
		groups.add(group2);
		groups.add(group3);
		groups.add(group4);
		groups.add(group5);
		groups.add(group6);

		createStudent(college, "1200800001", "齐庆", specialty, group);
		createStudent(college, "1200800002", "左存杰", specialty, group);
		createStudent(college, "1200800003", "张俊霞", specialty, group2);
		createStudent(college, "1200800004", "刘彦彦", specialty, group2);
		createStudent(college, "1200800005", "陈田", specialty, group3);
		createStudent(college, "1200800006", "黎欢", specialty, group3);
		createStudent(college, "1200800007", "申进", specialty, group4);
		createStudent(college, "1200800008", "张庆科", specialty, group5);
		createStudent(college, "1200800009", "张一新", specialty, group5);
		createStudent(college, "1200800010", "张洋", specialty, group6);
		String fileName = "D://abc.doc";
		generateFile.generateSubjectSelectSummary(college, currentEvent, groups, fileName);
	}

	private void createStudent(College college, String number, String compllcation, Specialty specialty, Group group) {
		Student student = new Student();
		student.setCollege(college);
		student.setCompellation(compllcation);
		student.setNumber(number);
		student.setGroup(group);
		student.setSpecialty(specialty);
		studentService.saveEntitiy(student);
	}

	private Group createGrpup(Subject subject) {
		Group group = new Group();
		group.setSubject(subject);
		groupService.saveEntitiy(group);
		return group;
	}

	private Subject createSubject(Event event, String topicChoosingWay, String don, String donJob, Specialty specialty,
			String subjectType, String subjectSource) {
		Subject subject = new Subject();
		subject.setEvent(event);
		subject.setBeginTime(event.getBeginDate());
		subject.setEndTime(event.getEndDate());
		subject.setTopicChoosingWay(topicChoosingWay);
		subject.setDon(don);
		subject.setDonJob(donJob);
		subject.setSpecialty(specialty);
		subject.setSubjectType(subjectType);
		subject.setSubjectSource(subjectSource);
		subjectService.saveEntitiy(subject);
		return subject;
	}

}

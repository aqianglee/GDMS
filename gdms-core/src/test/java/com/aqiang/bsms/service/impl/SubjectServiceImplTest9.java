package com.aqiang.bsms.service.impl;

import java.util.List;

import org.junit.Test;

import com.aqiang.bsms.entities.College;
import com.aqiang.bsms.entities.Event;
import com.aqiang.bsms.entities.JobType;
import com.aqiang.bsms.entities.Specialty;
import com.aqiang.bsms.entities.Student;
import com.aqiang.bsms.entities.Subject;
import com.aqiang.bsms.entities.Teacher;
import com.aqiang.gdms.BaseTest;

public class SubjectServiceImplTest9 extends BaseTest {

	@Test
	public void testGetAllSubjects() {

		Teacher teacher = new Teacher();
		teacherService.saveEntitiy(teacher);

		College college = new College();
		college.setCollegeName("计算机技术与应用系");
		collegeService.saveEntitiy(college);

		Event currentEvent = new Event();
		currentEvent.setYear("2016");
		currentEvent.setActive(true);
		currentEvent.setCollege(college);
		eventService.saveEntitiy(currentEvent);

		Specialty specialty = new Specialty();
		specialty.setName("计算机科学与技术2012级");
		specialtyService.saveEntitiy(specialty);

		createSubjectUseTeacher(college, currentEvent,
				"信息技术方向——基于Office插件的信息填报辅助工具设计与实现", "王晓英", JobType.PROFESSOR,
				specialty, teacher);
		createSubjectUseTeacher(college, currentEvent,
				"信息技术方向——基于Linux环境的多类典型应用能耗模型研究", "王晓英", JobType.PROFESSOR,
				specialty, teacher);
		createSubjectUseTeacher(college, currentEvent,
				"信息技术方向——基于敏捷开发的校园通购物系统", "曹腾飞", JobType.ASSISTANT, specialty,
				null);
		createSubjectUseTeacher(college, currentEvent,
				"信息技术方向——3D打印中支撑结构的自动生成", "杜正君", JobType.ASSISTANT, specialty,
				teacher);
		createSubjectUseTeacher(college, currentEvent, "信息技术方向——内容感知的图像缩放处理",
				"杜正君", JobType.ASSISTANT, specialty, null);
		createSubjectUseTeacher(college, currentEvent,
				"信息技术方向——基于WEBGL互助土族故土园的研究与实现", "李鑫丽", JobType.LECTURER,
				specialty, teacher);
		List<Subject> subjects = subjectService.getMyAllSubject(teacher);
		isTrue(subjects.size() == 4);
		isTrue("信息技术方向——基于Office插件的信息填报辅助工具设计与实现".equals(subjects.get(0)
				.getTopicChoosingWay()));
		isTrue("信息技术方向——基于Linux环境的多类典型应用能耗模型研究".equals(subjects.get(1)
				.getTopicChoosingWay()));
		isTrue("信息技术方向——3D打印中支撑结构的自动生成".equals(subjects.get(2)
				.getTopicChoosingWay()));
		isTrue("信息技术方向——基于WEBGL互助土族故土园的研究与实现".equals(subjects.get(3)
				.getTopicChoosingWay()));
	}

	private Subject createSubjectUseTeacher(College college, Event event,
			String topicChoosingWay, String don, String donJob,
			Specialty specialty, Teacher teacher) {
		Subject subject = new Subject();
		subject.setCollege(college);
		subject.setEvent(event);
		subject.setTopicChoosingWay(topicChoosingWay);
		subject.setDon(don);
		subject.setDonJob(donJob);
		subject.setSpecialty(specialty);
		subject.setUser(teacher);
		subjectService.saveEntitiy(subject);
		return subject;
	}

	private Subject createSubjectUseStudent(College college, Event event,
			String topicChoosingWay, String don, String donJob,
			Specialty specialty, Student student) {
		Subject subject = new Subject();
		subject.setCollege(college);
		subject.setEvent(event);
		subject.setTopicChoosingWay(topicChoosingWay);
		subject.setDon(don);
		subject.setDonJob(donJob);
		subject.setSpecialty(specialty);
		subject.setUser(student);
		subjectService.saveEntitiy(subject);
		return subject;
	}

	@Test
	public void testGetAllSubjects2() {
		Student student = new Student();
		studentService.saveEntitiy(student);
		College college = new College();
		college.setCollegeName("计算机技术与应用系");
		collegeService.saveEntitiy(college);

		Event currentEvent = new Event();
		currentEvent.setYear("2016");
		currentEvent.setActive(true);
		currentEvent.setCollege(college);
		eventService.saveEntitiy(currentEvent);

		Specialty specialty = new Specialty();
		specialty.setName("计算机科学与技术2012级");
		specialtyService.saveEntitiy(specialty);

		createSubjectUseStudent(college, currentEvent,
				"信息技术方向——基于Office插件的信息填报辅助工具设计与实现", "王晓英", JobType.PROFESSOR,
				specialty, student);
		createSubjectUseStudent(college, currentEvent,
				"信息技术方向——基于Linux环境的多类典型应用能耗模型研究", "王晓英", JobType.PROFESSOR,
				specialty, null);
		createSubjectUseStudent(college, currentEvent,
				"信息技术方向——基于敏捷开发的校园通购物系统", "曹腾飞", JobType.ASSISTANT, specialty,
				student);
		createSubjectUseStudent(college, currentEvent,
				"信息技术方向——3D打印中支撑结构的自动生成", "杜正君", JobType.ASSISTANT, specialty,
				null);
		createSubjectUseStudent(college, currentEvent, "信息技术方向——内容感知的图像缩放处理",
				"杜正君", JobType.ASSISTANT, specialty, null);
		createSubjectUseStudent(college, currentEvent,
				"信息技术方向——基于WEBGL互助土族故土园的研究与实现", "李鑫丽", JobType.LECTURER,
				specialty, student);
		List<Subject> subjects = subjectService.getMyAllSubject(student);
		isTrue(3 == subjects.size());
		isTrue("信息技术方向——基于Office插件的信息填报辅助工具设计与实现".equals(subjects.get(0)
				.getTopicChoosingWay()));
		isTrue("信息技术方向——基于敏捷开发的校园通购物系统".equals(subjects.get(1)
				.getTopicChoosingWay()));
		isTrue("信息技术方向——基于WEBGL互助土族故土园的研究与实现".equals(subjects.get(2)
				.getTopicChoosingWay()));
	}
}

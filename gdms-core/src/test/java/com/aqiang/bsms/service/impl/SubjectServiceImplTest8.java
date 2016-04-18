package com.aqiang.bsms.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.aqiang.bsms.entities.College;
import com.aqiang.bsms.entities.Event;
import com.aqiang.bsms.entities.JobType;
import com.aqiang.bsms.entities.Specialty;
import com.aqiang.bsms.entities.Subject;
import com.aqiang.gdms.BaseTest;

public class SubjectServiceImplTest8 extends BaseTest {

	@Test
	public void testGetAllSubjects() {

		College college = new College();
		college.setCollegeName("计算机技术与应用系");
		collegeService.saveEntitiy(college);

		Event currentEvent = new Event();
		currentEvent.setYear("2016");
		currentEvent.setActive(true);
		currentEvent.setCollege(college);
		eventService.saveEntitiy(currentEvent);

		List<Subject> subjects = new ArrayList<Subject>();
		Specialty specialty = new Specialty();
		specialty.setName("计算机科学与技术2012级");
		specialtyService.saveEntitiy(specialty);
		subjects.add(createSubject(college, currentEvent, "信息技术方向——基于Office插件的信息填报辅助工具设计与实现", "王晓英", JobType.PROFESSOR,
				specialty));
		subjects.add(createSubject(college, currentEvent, "信息技术方向——基于Linux环境的多类典型应用能耗模型研究", "王晓英", JobType.PROFESSOR,
				specialty));
		subjects.add(createSubject(college, currentEvent, "信息技术方向——基于敏捷开发的校园通购物系统", "曹腾飞", JobType.ASSISTANT, specialty));
		subjects.add(createSubject(college, currentEvent, "信息技术方向——3D打印中支撑结构的自动生成", "杜正君", JobType.ASSISTANT, specialty));
		subjects.add(createSubject(college, currentEvent, "信息技术方向——内容感知的图像缩放处理", "杜正君", JobType.ASSISTANT, specialty));
		subjects.add(createSubject(college, currentEvent, "信息技术方向——基于WEBGL互助土族故土园的研究与实现", "李鑫丽", JobType.LECTURER,
				specialty));
		List<Subject> allSubjects = subjectService.getAllSubjects(college, currentEvent);
		isTrue(allSubjects.size() == 6);
	}

	private Subject createSubject(College college, Event event, String topicChoosingWay, String don, String donJob,
			Specialty specialty) {
		Subject subject = new Subject();
		subject.setCollege(college);
		subject.setEvent(event);
		subject.setTopicChoosingWay(topicChoosingWay);
		subject.setDon(don);
		subject.setDonJob(donJob);
		subject.setSpecialty(specialty);
		subjectService.saveEntitiy(subject);
		return subject;
	}
}

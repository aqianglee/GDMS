package com.aqiang.bsms.service.impl;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.aqiang.bsms.entities.College;
import com.aqiang.bsms.entities.Event;
import com.aqiang.bsms.entities.JobType;
import com.aqiang.bsms.entities.Specialty;
import com.aqiang.bsms.entities.Subject;
import com.aqiang.bsms.entities.TeachAndResearchOffice;
import com.aqiang.bsms.service.CollegeService;
import com.aqiang.bsms.service.EventService;
import com.aqiang.bsms.service.SpecialtyService;
import com.aqiang.bsms.service.SubjectService;
import com.aqiang.bsms.service.TeachAndResearchOfficeService;
import com.aqiang.gdms.BaseTest;

public class SubjectServiceImplTest3 extends BaseTest {

	@Autowired
	private SubjectService subjectService;
	@Autowired
	private EventService eventService;
	@Autowired
	private TeachAndResearchOfficeService teachAndResearchOfficeService;
	@Autowired
	private CollegeService collegeService;
	@Autowired
	private SpecialtyService specialtyService;

	@Test
	public void testGetSelectAbleSubject() {
		Event event = new Event();
		event.setYear("2016");
		event.setActive(true);
		eventService.saveEntitiy(event);
		Specialty specialty = createSpecialty("计算机科学与技术");
		College college = createCollege("计算机技术与应用系");
		TeachAndResearchOffice teachAndResearchOffice = createTeachAndResearchOffice("计算机教研室");
		createSubject(event, teachAndResearchOffice, "信息技术方向-基于敏捷软件开发的校园超市系统", "2016-03-01", "2016-06-01", college,
				specialty, JobType.PROFESSOR, JobType.LECTURER, "0", true);
		createSubject(event, teachAndResearchOffice, "信息技术方向-基于软件工程的XXX技术", "2016-03-01", "2016-06-01", college,
				specialty, JobType.PROFESSOR, JobType.LECTURER, "0", false);
		createSubject(event, teachAndResearchOffice, "信息技术方向-基于软件工程的YYY技术", "2016-03-01", "2016-06-01", college,
				specialty, JobType.PROFESSOR, JobType.LECTURER, "0", null);
		List<Subject> subjects = subjectService.getSelectAbleSubject(college);
		System.out.println("subjcet size is : " + subjects.size());
	}

	private Subject createSubject(Event event, TeachAndResearchOffice teachAndResearchOffice, String name,
			String beginDate, String endDate, College college, Specialty specialty, String donJob, String tutorJob,
			String budget, Boolean commented) {
		Subject subject = new Subject();
		subject.setTopicChoosingWay(name);
		subject.setEvent(event);
		subject.setTeachAndResearchOffice(teachAndResearchOffice);
		subject.setBeginTime(date(beginDate, "yyyy-MM-dd"));
		subject.setEndTime(date(endDate, "yyyy-MM-dd"));
		subject.setBudget(budget);
		subject.setCollege(college);
		subject.setTutorJob(tutorJob);
		subject.setDonJob(donJob);
		subject.setSpecialty(specialty);
		subject.setCommented(commented);
		subjectService.saveEntitiy(subject);
		return subject;
	}

	private TeachAndResearchOffice createTeachAndResearchOffice(String name) {
		TeachAndResearchOffice teachAndResearchOffice = new TeachAndResearchOffice();
		teachAndResearchOffice.setName(name);
		teachAndResearchOfficeService.saveEntitiy(teachAndResearchOffice);
		return teachAndResearchOffice;
	}

	private Specialty createSpecialty(String name) {
		Specialty specialty = new Specialty();
		specialty.setName(name);
		specialtyService.saveEntitiy(specialty);
		return specialty;
	}

	private College createCollege(String collegeName) {
		College college = new College();
		college.setCollegeName(collegeName);
		collegeService.saveEntitiy(college);
		return college;
	}

}

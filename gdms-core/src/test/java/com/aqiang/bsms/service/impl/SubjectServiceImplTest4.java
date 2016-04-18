package com.aqiang.bsms.service.impl;

import org.junit.Test;

import com.aqiang.bsms.entities.College;
import com.aqiang.bsms.entities.Event;
import com.aqiang.bsms.entities.JobType;
import com.aqiang.bsms.entities.Specialty;
import com.aqiang.bsms.entities.Student;
import com.aqiang.bsms.entities.Subject;
import com.aqiang.bsms.entities.TeachAndResearchOffice;
import com.aqiang.bsms.entities.WorkflowStatus;
import com.aqiang.gdms.BaseTest;

public class SubjectServiceImplTest4 extends BaseTest {

	@Test
	public void testAddStudentToSelectList() {
		College college = createCollege("计算机技术与应用系", "计算机系", "1");
		Event event = createEvent(college, "2016", WorkflowStatus.SELECT_SUBJECT, "2016-03-01", "2016-06-01", false);
		TeachAndResearchOffice teachAndResearchOffice = createTeachAndResearchOffice(college, "软件工程教研室", "3");
		Specialty specialty = createSpecialty(college, "计算机科学与技术", "2");
		Subject subject = createSubject(event, teachAndResearchOffice, "信息技术方向-基于敏捷软件开发的校园超市系统", "2016-03-01",
				"2016-06-01", college, specialty, "陈大文", JobType.PROFESSOR, JobType.LECTURER, "0", true);
		Student student = createStudent(college, specialty, "李志强", "1200802022", "aqiang");
		isTrue(student.getSubjects().size() == 0);
		isTrue(subject.getStudents().size() == 0);
		subjectService.addStudentToSelectList(student, subject);
		isTrue(student.getSubjects().size() == 1);
		isTrue(subject.getStudents().size() == 1);
		Subject subject2 = student.getSubjects().get(0);
		Student student2 = subject.getStudents().get(0);
		isTrue("信息技术方向-基于敏捷软件开发的校园超市系统".equals(subject2.getTopicChoosingWay()));
		isTrue("李志强".equals(student2.getCompellation()));
	}

	private Specialty createSpecialty(College college, String name, String num) {
		Specialty specialty = new Specialty();
		specialty.setCollege(college);
		specialty.setName(name);
		specialty.setNum(num);
		specialtyService.saveEntitiy(specialty);
		return specialty;
	}

	private TeachAndResearchOffice createTeachAndResearchOffice(College college, String name, String number) {
		TeachAndResearchOffice teachAndResearchOffice = new TeachAndResearchOffice();
		teachAndResearchOffice.setName(name);
		teachAndResearchOffice.setNumber(number);
		teachAndResearchOffice.setCollege(college);
		teachAndResearchOfficeService.saveEntitiy(teachAndResearchOffice);
		return teachAndResearchOffice;
	}

	private Event createEvent(College college, String year, String status, String beginDate, String endDate,
			Boolean active) {
		Event event = new Event();
		event.setCollege(college);
		event.setActive(active);
		event.setYear(year);
		event.setWorkFlowStatus(status);
		event.setBeginDate(date(beginDate, "yyyy-MM-dd"));
		event.setEndDate(date(endDate, "yyyy-MM-dd"));
		eventService.saveEntitiy(event);
		return event;
	}

	private College createCollege(String collegeName, String simpleName, String number) {
		College college = new College();
		college.setCollegeName(collegeName);
		college.setSimpleName(simpleName);
		college.setNumber(number);
		collegeService.saveEntitiy(college);
		return college;
	}

	private Student createStudent(College college, Specialty specialty, String compellation, String number,
			String username) {
		Student student = new Student();
		student.setCollege(college);
		student.setCompellation(compellation);
		student.setNumber(number);
		student.setSpecialty(specialty);
		student.setUsername(username);
		studentService.saveEntitiy(student);
		return student;
	}

	private Subject createSubject(Event event, TeachAndResearchOffice teachAndResearchOffice, String name,
			String beginDate, String endDate, College college, Specialty specialty, String don, String donJob,
			String tutorJob, String budget, Boolean commented) {
		Subject subject = new Subject();
		subject.setTopicChoosingWay(name);
		subject.setEvent(event);
		subject.setTeachAndResearchOffice(teachAndResearchOffice);
		subject.setBeginTime(date(beginDate, "yyyy-MM-dd"));
		subject.setEndTime(date(endDate, "yyyy-MM-dd"));
		subject.setBudget(budget);
		subject.setCollege(college);
		subject.setDon(don);
		subject.setTutorJob(tutorJob);
		subject.setDonJob(donJob);
		subject.setSpecialty(specialty);
		subject.setCommented(commented);
		subjectService.saveEntitiy(subject);
		return subject;
	}

}

package com.aqiang.bsms.service.impl;

import java.util.List;

import org.junit.Test;

import com.aqiang.bsms.entities.College;
import com.aqiang.bsms.entities.Event;
import com.aqiang.bsms.entities.Student;
import com.aqiang.gdms.BaseTest;

public class StudentServiceImplTest5 extends BaseTest {

	@Test
	public void testGetStudentsByCollegeAndCurrnetEvent() {
		College college = createCollege("计算机技术与应用系");
		College college2 = createCollege("土木工程学院");
		Event event2015 = createEvent("2015");
		Event event2016 = createEvent("2016");
		createStudent(college, event2015, "陈大文");
		createStudent(college, event2016, "陈少文");
		createStudent(college2, event2015, "陈二文");
		createStudent(college2, event2016, "陈三文");
		List<Student> students = studentService
				.getStudentsByCollegeAndCurrnetEvent(college, event2015);
		isTrue(students.size() == 1);
		isTrue(students.get(0).getCompellation().equals("陈大文"));
		students = studentService.getStudentsByCollegeAndCurrnetEvent(college,
				null);
		isTrue(students.size() == 2);
		isTrue(students.get(0).getCompellation().equals("陈大文"));
		isTrue(students.get(1).getCompellation().equals("陈少文"));
		students = studentService.getStudentsByCollegeAndCurrnetEvent(college2,
				null);
		isTrue(students.size() == 2);
		isTrue(students.get(0).getCompellation().equals("陈二文"));
		isTrue(students.get(1).getCompellation().equals("陈三文"));
	}

	private void createStudent(College college, Event event2015,
			String compellation) {
		Student student = new Student();
		student.setCollege(college);
		student.setEvent(event2015);
		student.setCompellation(compellation);
		studentService.saveEntitiy(student);
	}

	private Event createEvent(String year) {
		Event event2015 = new Event();
		event2015.setYear(year);
		eventService.saveEntitiy(event2015);
		return event2015;
	}

	private College createCollege(String collegeName) {
		College college = new College();
		college.setCollegeName(collegeName);
		collegeService.saveEntitiy(college);
		return college;
	}

}

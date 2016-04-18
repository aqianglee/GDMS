package com.aqiang.bsms.service.impl;

import java.util.List;

import org.junit.Test;

import com.aqiang.bsms.entities.Student;
import com.aqiang.bsms.entities.Subject;
import com.aqiang.gdms.BaseTest;

public class SubjectServiceImplTest6 extends BaseTest {

	@Test
	public void testAddStudentToSelectList() {
		Student student = new Student();
		student.setCompellation("studentA");
		Student student2 = new Student();
		student.setCompellation("studentB");
		Student student3 = new Student();
		student.setCompellation("studentC");

		Subject subject = new Subject();
		Subject subject2 = new Subject();
		Subject subject3 = new Subject();

		subject.setTopicChoosingWay("Information Technology Way");
		subject.getStudents().add(student);
		subject.getStudents().add(student2);
		subject.getStudents().add(student3);

		subject2.getStudents().add(student);
		subject2.getStudents().add(student3);

		subject3.getStudents().add(student);
		subject3.getStudents().add(student2);
		subject3.getStudents().add(student3);

		student.getSubjects().add(subject);
		student.getSubjects().add(subject2);
		student.getSubjects().add(subject3);

		student2.getSubjects().add(subject);
		student2.getSubjects().add(subject3);

		student3.getSubjects().add(subject);
		student3.getSubjects().add(subject2);
		student3.getSubjects().add(subject3);

		studentService.saveEntitiy(student);
		studentService.saveEntitiy(student2);
		studentService.saveEntitiy(student3);

		subjectService.saveEntitiy(subject);
		subjectService.saveEntitiy(subject2);
		subjectService.saveEntitiy(subject3);

		List<Student> students = studentService.getAll();
		List<Subject> subjects = subjectService.getAll();

		isTrue(students.size() == 3);
		isTrue(subjects.size() == 3);

		isTrue(students.get(0).getSubjects().size() == 3);
		isTrue(students.get(1).getSubjects().size() == 2);
		isTrue(students.get(2).getSubjects().size() == 3);

		isTrue(subjects.get(0).getStudents().size() == 3);
		isTrue(subjects.get(1).getStudents().size() == 2);
		isTrue(subjects.get(2).getStudents().size() == 3);

		subjectService.addStudentToSelectList(student2, subject2);

		students = studentService.getAll();
		subjects = subjectService.getAll();

		isTrue(students.size() == 3);
		isTrue(subjects.size() == 3);

		isTrue(students.get(0).getSubjects().size() == 3);
		isTrue(students.get(1).getSubjects().size() == 3);
		isTrue(students.get(2).getSubjects().size() == 3);

		isTrue(subjects.get(0).getStudents().size() == 3);
		isTrue(subjects.get(1).getStudents().size() == 3);
		isTrue(subjects.get(2).getStudents().size() == 3);
	}

}

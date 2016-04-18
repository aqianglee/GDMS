package com.aqiang.bsms.service.impl;

import java.util.List;

import org.junit.Test;

import com.aqiang.bsms.entities.Group;
import com.aqiang.bsms.entities.Student;
import com.aqiang.gdms.BaseTest;

public class StudentServiceImplTest3 extends BaseTest {

	@Test
	public void testGetStudentsByGroup() {
		Group group = new Group();
		groupService.saveEntitiy(group);
		Student student = new Student();
		student.setCompellation("陈大文");
		student.setGroup(group);
		studentService.saveEntitiy(student);
		Student student2 = new Student();
		student2.setCompellation("朱允文");
		student2.setGroup(group);
		studentService.saveEntitiy(student2);
		List<Student> students = studentService.getStudentsByGroup(group);
		isTrue(students.size() == 2);
		isTrue(students.get(0).getCompellation().equals("陈大文"));
		isTrue(students.get(1).getCompellation().equals("朱允文"));
	}
}

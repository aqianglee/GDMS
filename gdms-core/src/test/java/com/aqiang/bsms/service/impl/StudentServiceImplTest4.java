package com.aqiang.bsms.service.impl;

import org.junit.Test;

import com.aqiang.bsms.entities.Group;
import com.aqiang.bsms.entities.Student;
import com.aqiang.gdms.BaseTest;

public class StudentServiceImplTest4 extends BaseTest {

	@Test
	public void testIsConfirmByTeacher() {
		Student student = new Student();
		Group group = new Group();
		student.setGroup(group);
		boolean confirmByTeacher = studentService.isConfirmByTeacher(student);
		isTrue(Boolean.TRUE.equals(confirmByTeacher));
	}

	@Test
	public void testIsConfirmByTeacher2() {
		Student student = new Student();
		boolean confirmByTeacher = studentService.isConfirmByTeacher(student);
		isTrue(Boolean.FALSE.equals(confirmByTeacher));
	}

	@Test
	public void testIsConfirmByTeacher3() {
		Student student = new Student();
		Group group = new Group();
		groupService.saveEntitiy(group);
		student.setGroup(group);
		studentService.saveEntitiy(student);
		boolean confirmByTeacher = studentService
				.isConfirmByTeacher(studentService.getAll().get(0));
		isTrue(Boolean.TRUE.equals(confirmByTeacher));
	}

	@Test
	public void testIsConfirmByTeacher4() {
		boolean confirmByTeacher = studentService.isConfirmByTeacher(null);
		isTrue(Boolean.FALSE.equals(confirmByTeacher));
	}
}

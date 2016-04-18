package com.aqiang.bsms.service.impl;

import org.junit.Test;

import com.aqiang.bsms.entities.Student;
import com.aqiang.bsms.entities.User;
import com.aqiang.bsms.exception.UsernameHasUsedException;
import com.aqiang.gdms.BaseTest;

public class StudentServiceImplTest6 extends BaseTest {

	@Test
	public void testAddNewStudent() throws UsernameHasUsedException {
		Student student = new Student();
		student.setUsername("aqiang");
		student.setCompellation("李志强");
		student.setNumber("1200802022");
		studentService.addNewStudent(student);
		User user = userService.getUserByUsernameAndPassword("aqiang", "123456");
		isTrue(user != null);
		Student getStudent = (Student) user;
		isTrue(getStudent.getNumber().equals("1200802022"));
		isTrue(user.getCompellation().equals("李志强"));
		isTrue(getStudent.getCompellation().equals("李志强"));
	}

	@Test(expected = UsernameHasUsedException.class)
	public void testAddNewStudent2() throws UsernameHasUsedException {
		User user = new User();
		user.setUsername("aqiang");
		userService.saveEntitiy(user);
		Student student = new Student();
		student.setUsername("aqiang");
		student.setCompellation("李志强");
		student.setNumber("1200802022");
		studentService.addNewStudent(student);
		isTrue(userService.getUserByUsernameAndPassword("aqiang", "123456") == null);
		isTrue(!(userService.getUserByUsername("aqiang") instanceof Student));
		isTrue(userService.getUserByUsername("aqiang") instanceof User);
	}

}

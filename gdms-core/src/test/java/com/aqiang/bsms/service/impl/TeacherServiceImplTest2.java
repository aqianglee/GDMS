package com.aqiang.bsms.service.impl;

import org.junit.Test;

import com.aqiang.bsms.entities.JobType;
import com.aqiang.bsms.entities.Teacher;
import com.aqiang.bsms.entities.User;
import com.aqiang.bsms.exception.UsernameHasUsedException;
import com.aqiang.gdms.BaseTest;

public class TeacherServiceImplTest2 extends BaseTest {

	@Test
	public void testAddNewTeacher() throws UsernameHasUsedException {
		Teacher teacher = new Teacher();
		teacher.setUsername("aqiang");
		teacher.setCompellation("李志强");
		teacher.setJob(JobType.ASSISTANT);
		teacherService.addNewTeacher(teacher);
		User user = userService.getUserByUsernameAndPassword("aqiang", "123456");
		isTrue(user != null);
		Teacher getTeacher = (Teacher) user;
		isTrue(getTeacher.getJob().equals(JobType.ASSISTANT));
		isTrue(user.getCompellation().equals("李志强"));
		isTrue(getTeacher.getCompellation().equals("李志强"));
	}

	@Test(expected = UsernameHasUsedException.class)
	public void testAddNewTeacher2() throws UsernameHasUsedException {
		User user = new User();
		user.setUsername("aqiang");
		userService.saveEntitiy(user);
		Teacher teacher = new Teacher();
		teacher.setUsername("aqiang");
		teacher.setCompellation("李志强");
		teacher.setJob(JobType.ASSISTANT);
		teacherService.addNewTeacher(teacher);
		isTrue(userService.getUserByUsernameAndPassword("aqiang", "123456") == null);
		isTrue(!(userService.getUserByUsername("aqiang") instanceof Teacher));
		isTrue(userService.getUserByUsername("aqiang") instanceof User);
	}

}

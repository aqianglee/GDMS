package com.aqiang.bsms.service.impl;

import org.junit.Test;

import com.aqiang.bsms.entities.CollegeManager;
import com.aqiang.bsms.entities.JobType;
import com.aqiang.bsms.entities.Student;
import com.aqiang.bsms.entities.SystemManager;
import com.aqiang.bsms.entities.Teacher;
import com.aqiang.bsms.entities.User;
import com.aqiang.gdms.BaseTest;

public class UserServiceImplTest extends BaseTest {

	@Test
	public void testGetUserByUsername() {
		User user = userService.getUserByUsername("aqiang");
		isTrue(user == null);
	}

	@Test
	public void testGetUserByUsername2() {
		User user = new User();
		user.setUsername("aqiang");
		userService.saveEntitiy(user);
		User findUser = userService.getUserByUsername("aqiang");
		isTrue(findUser != null);
	}

	@Test
	public void testGetUserByUsername3() {
		SystemManager systemManager = new SystemManager();
		systemManager.setUsername("aqiang");
		systemManagerService.saveEntitiy(systemManager);
		User user = userService.getUserByUsername("aqiang");
		isTrue(user != null);
	}

	@Test
	public void testGetUserByUsername4() {
		CollegeManager collegeManager = new CollegeManager();
		collegeManager.setUsername("aqiang");
		collegeManagerService.saveEntitiy(collegeManager);
		User user = userService.getUserByUsername("aqiang");
		isTrue(user != null);
	}

	@Test
	public void testGetUserByUsername5() {
		Teacher teacher = new Teacher();
		teacher.setUsername("aqiang");
		teacher.setJob(JobType.ASSISTANT);
		teacherService.saveEntitiy(teacher);
		User user = userService.getUserByUsername("aqiang");
		isTrue(user != null);
		isTrue(JobType.ASSISTANT.equals(((Teacher) user).getJob()));
	}

	@Test
	public void testGetUserByUsername6() {
		Student student = new Student();
		student.setUsername("aqiang");
		student.setNumber("1200802022");
		studentService.saveEntitiy(student);
		User user = userService.getUserByUsername("aqiang");
		isTrue(user != null);
		isTrue("1200802022".equals(((Student) user).getNumber()));
	}
}

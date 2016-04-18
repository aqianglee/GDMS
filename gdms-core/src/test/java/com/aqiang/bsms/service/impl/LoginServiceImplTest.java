package com.aqiang.bsms.service.impl;

import org.junit.Test;

import com.aqiang.bsms.entities.CollegeManager;
import com.aqiang.bsms.entities.JobType;
import com.aqiang.bsms.entities.Student;
import com.aqiang.bsms.entities.SystemManager;
import com.aqiang.bsms.entities.Teacher;
import com.aqiang.bsms.entities.User;
import com.aqiang.bsms.exception.UsernameHasUsedException;
import com.aqiang.gdms.BaseTest;

public class LoginServiceImplTest extends BaseTest {

	@Test
	public void testValidateUserNoUser() {
		User user = new User();
		user.setUsername("aqiang");
		user.setPassword("123456");
		User validateUser = loginService.validateUser(user);
		isTrue(validateUser == null);
	}

	@Test
	public void testValidateUserGetDefaultSystemManager() {
		User user = new User();
		user.setUsername("admin");
		user.setPassword("123456");
		User validateUser = loginService.validateUser(user);
		isTrue(validateUser != null);
		isTrue(validateUser instanceof SystemManager);
	}

	@Test
	public void testValidateUserHasSystemManager() throws UsernameHasUsedException {
		systemManagerService.createSystemManager("aqiang", "123456");
		User user = new User();
		user.setUsername("aqiang");
		user.setPassword("123456");
		User validateUser = loginService.validateUser(user);
		isTrue(validateUser != null);
		isTrue(validateUser instanceof SystemManager);
	}

	@Test
	public void testValidateUserGetSystemManagerError() throws UsernameHasUsedException {
		systemManagerService.createSystemManager("xiaoqiangzi", "123456");
		User user = new User();
		user.setUsername("aqiang");
		user.setPassword("123456");
		User validateUser = loginService.validateUser(user);
		isTrue(validateUser == null);
	}

	@Test
	public void testValidateUserHasCollegeManager() throws UsernameHasUsedException {
		CollegeManager collegeManager = new CollegeManager();
		collegeManager.setUsername("liuzhiqiang");
		collegeManager.setCompellation("刘志强");
		collegeManagerService.createNewCollegeManager(collegeManager);
		User user = new User();
		user.setUsername("liuzhiqiang");
		user.setPassword("123456");
		User validateUser = loginService.validateUser(user);
		isTrue(validateUser != null);
		isTrue(validateUser instanceof CollegeManager);
	}

	@Test
	public void testValidateUserGetCollegeManagerError() throws UsernameHasUsedException {
		CollegeManager collegeManager = new CollegeManager();
		collegeManager.setUsername("liuzhiqiang");
		collegeManager.setCompellation("刘志强");
		collegeManagerService.createNewCollegeManager(collegeManager);
		User user = new User();
		user.setUsername("liuzhiqiang");
		user.setPassword("1234567");
		User validateUser = loginService.validateUser(user);
		isTrue(validateUser == null);
	}

	@Test
	public void testValidateUserHasTeacher() throws UsernameHasUsedException {
		Teacher teacher = new Teacher();
		teacher.setCompellation("曹腾飞");
		teacher.setUsername("caotengfei");
		teacher.setJob(JobType.ASSISTANT);
		teacherService.addNewTeacher(teacher);
		User user = new User();
		user.setUsername("caotengfei");
		user.setPassword("123456");
		User validateUser = loginService.validateUser(user);
		isTrue(validateUser != null);
		isTrue(validateUser instanceof Teacher);
		isTrue(((Teacher) validateUser).getJob().equals(JobType.ASSISTANT));
	}

	@Test
	public void testValidateUserGetTeacherError() throws UsernameHasUsedException {
		Teacher teacher = new Teacher();
		teacher.setCompellation("曹腾飞");
		teacher.setUsername("caotengfei");
		teacherService.addNewTeacher(teacher);
		User user = new User();
		user.setUsername("aqiang");
		user.setPassword("123456789");
		User validateUser = loginService.validateUser(user);
		isTrue(validateUser == null);
	}

	@Test
	public void testValidateUserHasStudent() throws UsernameHasUsedException {
		Student student = new Student();
		student.setUsername("aqiang");
		student.setCompellation("李志强");
		student.setNumber("1200802022");
		studentService.addNewStudent(student);
		User user = new User();
		user.setUsername("aqiang");
		user.setPassword("123456");
		User validateUser = loginService.validateUser(user);
		isTrue(validateUser != null);
		isTrue(validateUser instanceof Student);
		isTrue(((Student) validateUser).getNumber().equals("1200802022"));
	}

	@Test
	public void testValidateUserGetStudentError() throws UsernameHasUsedException {
		Student student = new Student();
		student.setUsername("aqiang");
		student.setCompellation("李志强");
		User user = new User();
		user.setUsername("aqiang");
		user.setPassword("123455");
		User validateUser = loginService.validateUser(user);
		isTrue(validateUser == null);
	}

}

package com.aqiang.bsms.service.impl;

import org.junit.Test;

import com.aqiang.bsms.entities.Student;
import com.aqiang.gdms.BaseTest;

public class BaseServiceImplTest extends BaseTest {

	@Test
	public void testDeleteAll() {
		studentService.saveEntitiy(new Student());
		studentService.saveEntitiy(new Student());
		studentService.saveEntitiy(new Student());
		studentService.saveEntitiy(new Student());
		studentService.saveEntitiy(new Student());
		isTrue(studentService.getAll().size() == 5);
		studentService.deleteAll();
		isTrue(studentService.getAll().size() == 0);
	}
}

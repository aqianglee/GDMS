package com.aqiang.bsms.service.impl;

import java.util.List;

import org.junit.Test;

import com.aqiang.bsms.entities.College;
import com.aqiang.bsms.entities.Teacher;
import com.aqiang.gdms.BaseTest;

public class TeacherServiceImplTest extends BaseTest {

	@Test
	public void testGetTeachersByCollege() {
		College college = new College();
		college.setCollegeName("计算机技术与应用系");
		collegeService.saveEntitiy(college);

		Teacher teacher = new Teacher();
		teacher.setCollege(college);
		teacher.setCompellation("曹腾飞");

		Teacher teacher2 = new Teacher();
		teacher2.setCollege(college);
		teacher2.setCompellation("李东");

		teacherService.saveEntitiy(teacher);
		teacherService.saveEntitiy(teacher2);

		List<Teacher> teachersByCollege = teacherService.getTeachersByCollege(college);

		isTrue(teachersByCollege.size() == 2);
		isTrue(teachersByCollege.get(0).getCompellation().equals("曹腾飞"));
		isTrue(teachersByCollege.get(1).getCompellation().equals("李东"));
	}

}

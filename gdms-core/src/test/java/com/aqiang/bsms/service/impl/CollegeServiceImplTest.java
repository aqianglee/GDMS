package com.aqiang.bsms.service.impl;

import org.junit.Test;

import com.aqiang.bsms.entities.College;
import com.aqiang.gdms.BaseTest;

public class CollegeServiceImplTest extends BaseTest {

	@Test
	public void testGetCollegeByName() {
		College college = new College();
		college.setCollegeName("计算机技术与应用系");
		collegeService.saveEntitiy(college);

		College collegeByName = collegeService.getCollegeByName("计算机技术与应用系");
		isTrue(collegeByName.getCollegeName().equals("计算机技术与应用系"));
	}
}

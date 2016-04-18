package com.aqiang.bsms.service.impl;

import org.junit.Test;

import com.aqiang.bsms.entities.College;
import com.aqiang.gdms.BaseTest;

public class CollegeServiceImplTest2 extends BaseTest {

	@Test
	public void testCreateCollege() {
		College college = new College();
		college.setCollegeName("计算机科学与技术");
		collegeService.createCollege(college);

		isTrue(collegeService.getAllColleges().size() == 1);
		College college2 = collegeService.getAllColleges().get(0);
		isTrue(college2.getCollegeName().equals("计算机科学与技术"));
	}

	@Test
	public void testCreateCollege2() {
		College college = new College();
		college.setCollegeName("计算机科学与技术");
		college.setDescription("computer");
		college.setSimpleName("计算机系");
		collegeService.saveEntitiy(college);

		College college3 = new College();
		college3.setCollegeName("计算机科学与技术");

		collegeService.createCollege(college3);
		isTrue(collegeService.getAllColleges().size() == 1);

		College college2 = collegeService.getAllColleges().get(0);
		isTrue(college2.getCollegeName().equals("计算机科学与技术"));
	}

}

package com.aqiang.bsms.service.impl;

import org.junit.Test;

import com.aqiang.bsms.entities.College;
import com.aqiang.bsms.entities.CollegeManager;
import com.aqiang.bsms.exception.UsernameHasUsedException;
import com.aqiang.gdms.BaseTest;

public class CollegeServiceImplTest3 extends BaseTest {

	@Test
	public void testChangeCollegeManager() throws UsernameHasUsedException {
		College college = new College();
		college.setCollegeName("计算机系");
		CollegeManager collegeManager = new CollegeManager();
		collegeManager.setUsername("aqiang");
		collegeManager.setCompellation("李志强");
		collegeManagerService.saveEntitiy(collegeManager);
		college.setCollegeManager(collegeManager);
		collegeService.saveEntitiy(college);
		College college2 = collegeService.getAll().get(0);
		isTrue(college2.getCollegeManager().getUsername().equals("aqiang"));
		isTrue(college2.getCollegeManager().getCompellation().equals("李志强"));
		isTrue(collegeService.getAll().size() == 1);
		CollegeManager newCollegeManager = new CollegeManager();
		newCollegeManager.setCompellation("刘志强");
		newCollegeManager.setUsername("liuzhiqiang");
		collegeService.changeCollegeManager(college2, newCollegeManager);
		college2 = collegeService.getAll().get(0);
		CollegeManager collegeManager2 = college2.getCollegeManager();
		isTrue(collegeManager2 != null);
		isTrue(collegeManager2.getCompellation().equals("刘志强"));
	}

}

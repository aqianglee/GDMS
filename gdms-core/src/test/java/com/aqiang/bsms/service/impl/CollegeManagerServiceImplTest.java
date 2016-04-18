package com.aqiang.bsms.service.impl;

import org.junit.Test;

import com.aqiang.bsms.entities.CollegeManager;
import com.aqiang.bsms.entities.User;
import com.aqiang.bsms.exception.UsernameHasUsedException;
import com.aqiang.bsms.utils.Md5Util;
import com.aqiang.gdms.BaseTest;

public class CollegeManagerServiceImplTest extends BaseTest {

	@Test
	public void testCreateNewCollegeManager() throws UsernameHasUsedException {
		CollegeManager collegeManager = new CollegeManager();
		collegeManager.setUsername("aqiang");
		collegeManager.setCompellation("李志强");
		collegeManagerService.createNewCollegeManager(collegeManager);
		CollegeManager findEntity = collegeManagerService.getAll().get(0);
		isTrue(findEntity.getUsername().equals("aqiang"));
		isTrue(findEntity.getCompellation().equals("李志强"));
		isTrue(findEntity.getPassword().equals(Md5Util.md5("123456")));
	}

	@Test(expected = UsernameHasUsedException.class)
	public void testCreateNewCollegeManager2() throws UsernameHasUsedException {
		User user = new User();
		user.setUsername("aqiang");
		userService.saveEntitiy(user);
		CollegeManager collegeManager = new CollegeManager();
		collegeManager.setUsername("aqiang");
		collegeManager.setCompellation("李志强");
		collegeManagerService.createNewCollegeManager(collegeManager);
	}

}

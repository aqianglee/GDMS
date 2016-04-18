package com.aqiang.bsms.service.impl;

import org.junit.Test;

import com.aqiang.bsms.entities.User;
import com.aqiang.bsms.exception.UsernameHasUsedException;
import com.aqiang.gdms.BaseTest;

public class SystemManagerServiceImplTest2 extends BaseTest {

	@Test
	public void testCreateSystemManager() throws UsernameHasUsedException {
		systemManagerService.createSystemManager("aqiang", "123456");
		isTrue(userService.getUserByUsernameAndPassword("aqiang", "123456") != null);
	}

	@Test(expected = UsernameHasUsedException.class)
	public void testCreateSystemManager2() throws UsernameHasUsedException {
		User user = new User();
		user.setUsername("aqiang");
		userService.saveEntitiy(user);
		systemManagerService.createSystemManager("aqiang", "123456");
		isTrue(userService.getUserByUsernameAndPassword("aqiang", "123456") == null);
	}

}

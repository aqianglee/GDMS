package com.aqiang.bsms.service.impl;

import org.junit.Test;

import com.aqiang.bsms.entities.SystemManager;
import com.aqiang.bsms.utils.Md5Util;
import com.aqiang.gdms.BaseTest;

public class SystemManagerServiceImplTest extends BaseTest {

	@Test
	public void testCreateDefaultSystemManager() {
		systemManagerService.createDefaultSystemManager();
		SystemManager systemManager = systemManagerService.getAll().get(0);
		isTrue("admin".equals(systemManager.getUsername()));
		isTrue(Md5Util.md5("123456").equals(systemManager.getPassword()));
	}

}

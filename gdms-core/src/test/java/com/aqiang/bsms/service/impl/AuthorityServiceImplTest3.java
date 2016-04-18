package com.aqiang.bsms.service.impl;

import org.junit.Test;

import com.aqiang.bsms.entities.Authority;
import com.aqiang.gdms.BaseTest;

public class AuthorityServiceImplTest3 extends BaseTest {

	@Test
	public void testCreateAuthority2() {
		System.out.println("tester test3");
		Authority authority = new Authority();
		authority.setName("管理学生");
		authority.setUrl("student_list.action");
		authorityService.saveEntitiy(authority);
		logger.info("start create authority");
		authorityService.createAuthority("管理学生", "student_list.action");
		logger.info("finish");
		Authority authority2 = authorityService.getAll().get(0);
		isTrue(authority2 != null);
		isTrue(authority2.getName().equals("管理学生"));
		isTrue(authority2.getUrl().equals("student_list.action"));
	}

}

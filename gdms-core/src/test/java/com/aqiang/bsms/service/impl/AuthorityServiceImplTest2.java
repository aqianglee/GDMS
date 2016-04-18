package com.aqiang.bsms.service.impl;

import org.junit.Test;

import com.aqiang.bsms.entities.Authority;
import com.aqiang.gdms.BaseTest;

public class AuthorityServiceImplTest2 extends BaseTest {

	@Test
	public void testGetAuthorityByName() {
		Authority createAuthority = authorityService.createAuthority("管理学校", "school_list.action");
		isTrue(createAuthority != null);
		Authority authority = authorityService.getAuthorityByName("管理学校");
		isTrue(authority != null);
		isTrue(authority.getName().equals("管理学校"));
		isTrue(authority.getUrl().equals("school_list.action"));
	}

}

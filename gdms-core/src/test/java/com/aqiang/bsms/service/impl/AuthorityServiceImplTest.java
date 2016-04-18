package com.aqiang.bsms.service.impl;

import org.junit.Test;

import com.aqiang.bsms.entities.Authority;
import com.aqiang.gdms.BaseTest;

public class AuthorityServiceImplTest extends BaseTest {

	@Test
	public void testCreateAuthority() {
		Authority createAuthority = authorityService.createAuthority("管理学校", "school_list.action");
		isTrue(createAuthority != null);
		Authority authority = authorityService.findEntity(1);
		isTrue(authority != null);
		isTrue(authority.getName().equals("管理学校"));
		isTrue(authority.getUrl().equals("school_list.action"));
	}

}

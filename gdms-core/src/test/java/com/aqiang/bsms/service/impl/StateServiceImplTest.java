package com.aqiang.bsms.service.impl;

import java.util.List;

import org.junit.Test;

import com.aqiang.bsms.entities.Authority;
import com.aqiang.bsms.entities.State;
import com.aqiang.bsms.entities.StateID;
import com.aqiang.gdms.BaseTest;

public class StateServiceImplTest extends BaseTest {

	@Test
	public void testGetStateByStateId() {
		State state = new State();
		state.setName("院系管理员");
		state.setStateId(StateID.BEGIN_COLLEGE_MANAGER);
		List<Authority> authorities = state.getAuthorities();
		authorities.add(authorityService.createAuthority("管理教师", "teacher_list.action"));
		stateService.saveEntitiy(state);

		State stateByStateId = stateService.getStateByStateId(StateID.BEGIN_COLLEGE_MANAGER);
		isTrue(stateByStateId.getName().equals("院系管理员"));
		isTrue(stateByStateId.getStateId().equals(StateID.BEGIN_COLLEGE_MANAGER));
		List<Authority> authorities2 = stateByStateId.getAuthorities();
		isTrue(authorities2.size() == 1);
		Authority authority = authorities2.get(0);
		isTrue(authority.getName().equals("管理教师"));
		isTrue(authority.getUrl().equals("teacher_list.action"));
	}

}

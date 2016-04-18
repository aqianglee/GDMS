package com.aqiang.bsms.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.aqiang.bsms.entities.Authority;
import com.aqiang.bsms.entities.State;
import com.aqiang.bsms.entities.StateID;
import com.aqiang.gdms.BaseTest;

public class StateServiceImplTest2 extends BaseTest {

	@Test
	public void testGetStateByStateId() {
		List<Authority> authorities = new ArrayList<Authority>();
		authorities.add(authorityService.createAuthority("管理教师", "teacher_list.action"));
		authorities.add(authorityService.createAuthority("管理学生", "student_list.action"));
		authorities.add(authorityService.createAuthority("管理专业班级", "clazz_list.action"));
		stateService.createState(StateID.BEGIN_COLLEGE_MANAGER, "院系管理员", authorities);

		State stateByStateId = stateService.getStateByStateId(StateID.BEGIN_COLLEGE_MANAGER);
		isTrue(stateByStateId.getName().equals("院系管理员"));
		isTrue(stateByStateId.getStateId().equals(StateID.BEGIN_COLLEGE_MANAGER));
		List<Authority> authorities2 = stateByStateId.getAuthorities();
		isTrue(authorities2.size() == 3);
		Authority authority = authorities2.get(0);
		isTrue(authority.getName().equals("管理教师"));
		isTrue(authority.getUrl().equals("teacher_list.action"));
		Authority authority2 = authorities2.get(1);
		isTrue(authority2.getName().equals("管理学生"));
		isTrue(authority2.getUrl().equals("student_list.action"));
		Authority authority3 = authorities2.get(2);
		isTrue(authority3.getName().equals("管理专业班级"));
		isTrue(authority3.getUrl().equals("clazz_list.action"));
	}

}

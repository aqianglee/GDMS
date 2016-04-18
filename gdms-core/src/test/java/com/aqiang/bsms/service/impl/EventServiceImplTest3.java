package com.aqiang.bsms.service.impl;

import org.junit.Test;

import com.aqiang.bsms.entities.College;
import com.aqiang.gdms.BaseTest;

public class EventServiceImplTest3 extends BaseTest {

	@Test
	public void testGenereteSelectSubjectGuide() {
		College college = new College();
		eventService.genereteSelectsSubjectGuideFile(college);
	}

}

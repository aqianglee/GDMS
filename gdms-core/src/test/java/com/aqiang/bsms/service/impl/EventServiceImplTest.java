package com.aqiang.bsms.service.impl;

import org.junit.Test;

import com.aqiang.bsms.entities.College;
import com.aqiang.bsms.entities.Event;
import com.aqiang.bsms.entities.WorkflowStatus;
import com.aqiang.gdms.BaseTest;

public class EventServiceImplTest extends BaseTest {

	@Test
	public void testGetCurrentEvent() {
		College college = new College();
		college.setCollegeName("计算机系");
		collegeService.saveEntitiy(college);

		Event event = new Event();
		event.setYear("2015");
		event.setWorkFlowStatus(WorkflowStatus.BEGIN);
		event.setActive(true);
		event.setCollege(college);
		eventService.saveEntitiy(event);

		Event currentEvent = eventService.getCurrentEvent(college);
		isTrue(currentEvent.getYear().equals("2015"));
		isTrue(currentEvent.getWorkFlowStatus().equals(WorkflowStatus.BEGIN));
		isTrue(currentEvent.getActive());
	}

}

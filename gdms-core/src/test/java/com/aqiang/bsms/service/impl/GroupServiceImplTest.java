package com.aqiang.bsms.service.impl;

import org.junit.Test;

import com.aqiang.bsms.entities.College;
import com.aqiang.bsms.entities.Event;
import com.aqiang.bsms.entities.Group;
import com.aqiang.gdms.BaseTest;

public class GroupServiceImplTest extends BaseTest {

	@Test
	public void test() {
		College college = createCollege("土木工程学院");
		Event event = createEvent("2015");
		College college2 = createCollege("计算机技术与应用系");
		Event event2 = createEvent("2016");
		createGroup(college, event);
		createGroup(college, event2);
		createGroup(college2, event);
		createGroup(college2, event2);
		isTrue(groupService.getGroupByCollegeAndEvent(college, event).size() == 1);
		isTrue(groupService.getGroupByCollegeAndEvent(college, event2).size() == 1);
		isTrue(groupService.getGroupByCollegeAndEvent(college2, event).size() == 1);
		isTrue(groupService.getGroupByCollegeAndEvent(college2, event2).size() == 1);
	}

	private void createGroup(College college, Event event) {
		Group group = new Group();
		group.setCollege(college);
		group.setEvent(event);
		groupService.saveEntitiy(group);
	}

	private Event createEvent(String year) {
		Event event = new Event();
		event.setYear(year);
		eventService.saveEntitiy(event);
		return event;
	}

	private College createCollege(String collegeName) {
		College college = new College();
		college.setCollegeName(collegeName);
		collegeService.saveEntitiy(college);
		return college;
	}

}

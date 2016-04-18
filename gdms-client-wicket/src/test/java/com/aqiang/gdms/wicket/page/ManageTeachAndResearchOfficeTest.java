package com.aqiang.gdms.wicket.page;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.Session;
import org.junit.Test;

import com.aqiang.bsms.entities.College;
import com.aqiang.bsms.entities.TeachAndResearchOffice;
import com.aqiang.bsms.entities.Teacher;
import com.aqiang.bsms.entities.User;
import com.aqiang.bsms.entities.UserType;
import com.aqiang.bsms.service.TeachAndResearchOfficeService;
import com.aqiang.gdms.wicket.PageTest;
import com.aqiang.gdms.wicket.service.SignService;
import com.ttdev.wicketpagetest.MockableSpringBeanInjector;
import com.ttdev.wicketpagetest.WebPageTestContext;
import com.ttdev.wicketpagetest.WicketSeleniumDriver;

public class ManageTeachAndResearchOfficeTest extends PageTest {

	abstract class MockTeachAndResearchOfficeService implements TeachAndResearchOfficeService {
		private List<TeachAndResearchOffice> teacherAndResearchOffices = new ArrayList<TeachAndResearchOffice>();

		@Override
		public List<TeachAndResearchOffice> getTeachAndResearchOfficeByCollege(College college) {
			if (teacherAndResearchOffices.size() == 0) {
				teacherAndResearchOffices.add(createTeachAndResearchOffice(college, "数据结构教研室", "1"));
				teacherAndResearchOffices.add(createTeachAndResearchOffice(college, "云计算教研室", "2"));
				teacherAndResearchOffices.add(createTeachAndResearchOffice(college, "软件工程教研室", "3"));
			}
			return teacherAndResearchOffices;
		}

		private TeachAndResearchOffice createTeachAndResearchOffice(College college, String name, String number) {
			TeachAndResearchOffice andResearchOffice = new TeachAndResearchOffice();
			andResearchOffice.setName(name);
			andResearchOffice.setNumber(number);
			andResearchOffice.setCollege(college);
			andResearchOffice.setId(teacherAndResearchOffices.size() + 1);
			return andResearchOffice;
		}
	}

	abstract class MockSignService implements SignService {
		@Override
		public User getSignUser(Session session) {
			Teacher teacher = new Teacher();
			teacher.setId(1);
			College college = new College();
			college.setId(1);
			college.setCollegeName("计算机技术与应用系");
			teacher.setCollege(college);
			return teacher;
		}

		@Override
		public College getCollege(Session session) {
			College college = new College();
			college.setId(1);
			college.setCollegeName("计算机技术与应用系");
			return college;
		}

		@Override
		public String getUserType(Session session) {
			return UserType.TEACHER;
		}
	}

	@Test
	public void testDisplayCurrentInfo() {
		MockableSpringBeanInjector.mockBean("signService", factory.implementAbstractMethods(MockSignService.class));
		MockableSpringBeanInjector.mockBean("teachAndResearchOfficeService",
				factory.implementAbstractMethods(MockTeachAndResearchOfficeService.class));
		WicketSeleniumDriver ws = WebPageTestContext.getWicketSelenium();
		ws.openNonBookmarkablePage(ManageTeachAndResearchOffice.class);
		isTrue(ws.isElementPresent("//teachAndResearchOfficeTab"));
		isTrue(ws.isElementPresent("//teachAndResearchOfficeItem"));
		isTrue(ws.getText("//teachAndResearchOfficeItem[0]//number").equals("1"));
		isTrue(ws.getText("//teachAndResearchOfficeItem[1]//number").equals("2"));
		isTrue(ws.getText("//teachAndResearchOfficeItem[2]//number").equals("3"));
		isTrue(ws.getText("//teachAndResearchOfficeItem[0]//name").equals("数据结构教研室"));
		isTrue(ws.getText("//teachAndResearchOfficeItem[1]//name").equals("云计算教研室"));
		isTrue(ws.getText("//teachAndResearchOfficeItem[2]//name").equals("软件工程教研室"));
	}
}

package com.aqiang.gdms.wicket.page;

import org.apache.wicket.Session;
import org.junit.Test;

import com.aqiang.bsms.entities.College;
import com.aqiang.bsms.entities.CollegeManager;
import com.aqiang.bsms.entities.User;
import com.aqiang.bsms.entities.UserType;
import com.aqiang.gdms.wicket.PageTest;
import com.aqiang.gdms.wicket.service.SignService;
import com.ttdev.wicketpagetest.MockableSpringBeanInjector;
import com.ttdev.wicketpagetest.WebPageTestContext;
import com.ttdev.wicketpagetest.WicketSeleniumDriver;

public class BorderPageTest extends PageTest {
	abstract class MockSignService implements SignService {
		@Override
		public College getCollege(Session session) {
			College college = new College();
			college.setCollegeName("计算机技术与应用系");
			return college;
		}

		@Override
		public String getUserType(Session session) {
			return UserType.COLLEGE_MANAGER;
		}

		@Override
		public User getSignUser(Session session) {
			CollegeManager collegeManager = new CollegeManager();
			collegeManager.setCompellation("刘志强");
			return collegeManager;
		}
	}

	@Test
	public void testBorderPage() {
		MockableSpringBeanInjector.mockBean("loginService",
				factory.implementAbstractMethods(MockSignService.class));
		WicketSeleniumDriver ws = WebPageTestContext.getWicketSelenium();
		ws.openNonBookmarkablePage(BorderPage.class);
	}
}

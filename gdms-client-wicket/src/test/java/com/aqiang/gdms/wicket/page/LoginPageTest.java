package com.aqiang.gdms.wicket.page;

import org.apache.wicket.Session;
import org.junit.Test;

import com.aqiang.bsms.entities.College;
import com.aqiang.bsms.entities.Event;
import com.aqiang.bsms.entities.SystemManager;
import com.aqiang.bsms.entities.User;
import com.aqiang.bsms.entities.UserType;
import com.aqiang.bsms.entities.WorkflowStatus;
import com.aqiang.bsms.service.EventService;
import com.aqiang.bsms.service.LoginService;
import com.aqiang.gdms.wicket.PageTest;
import com.aqiang.gdms.wicket.service.SignService;
import com.ttdev.wicketpagetest.MockableSpringBeanInjector;
import com.ttdev.wicketpagetest.WebPageTestContext;
import com.ttdev.wicketpagetest.WicketSeleniumDriver;

public class LoginPageTest extends PageTest {

	abstract class MockLoginService implements LoginService {
		@Override
		public User validateUser(User model) {
			return new User();
		}
	}

	abstract class MockEventService implements EventService {
		@Override
		public Event getCurrentEvent(College college) {
			Event event = new Event();
			event.setYear("2016");
			event.setWorkFlowStatus(WorkflowStatus.SELECT_SUBJECT);
			return event;
		}
	}

	abstract class MockSignService implements SignService {
		@Override
		public User getSignUser(Session session) {
			SystemManager systemManager = new SystemManager();
			systemManager.setId(1);
			return systemManager;
		}

		@Override
		public College getCollege(Session session) {
			College college = new College();
			college.setCollegeName("计算机科学与技术");
			return college;
		}

		@Override
		public String getUserType(Session session) {
			return UserType.SYSTEM_MANAGER;
		}
	}

	@Test
	public void testLoginPageDisplay() {
		MockableSpringBeanInjector.mockBean("loginService",
				factory.implementAbstractMethods(MockLoginService.class));
		MockableSpringBeanInjector.mockBean("signService",
				factory.implementAbstractMethods(MockSignService.class));
		MockableSpringBeanInjector.mockBean("eventService",
				factory.implementAbstractMethods(MockEventService.class));
		WicketSeleniumDriver ws = WebPageTestContext.getWicketSelenium();
		ws.openNonBookmarkablePage(LoginPage.class);
		isTrue(true == ws.isElementPresent("//form"));
		isTrue(true == ws.isElementPresent("//username"));
		isTrue(true == ws.isElementPresent("//password"));
		ws.sendKeys("//username", "abcdef");
		ws.sendKeys("//password", "abcdef");
		isTrue(true == ws.isElementPresent("//submit"));
		ws.click("//submit");
		pause(1);
	}

}

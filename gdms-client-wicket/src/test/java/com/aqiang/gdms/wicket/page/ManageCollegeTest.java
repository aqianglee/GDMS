package com.aqiang.gdms.wicket.page;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;

import com.aqiang.bsms.entities.College;
import com.aqiang.bsms.service.CollegeService;
import com.aqiang.gdms.wicket.PageTest;
import com.ttdev.wicketpagetest.MockableSpringBeanInjector;
import com.ttdev.wicketpagetest.WebPageTestContext;
import com.ttdev.wicketpagetest.WicketSeleniumDriver;

public class ManageCollegeTest extends PageTest {
	
	abstract class MockCollegeService implements CollegeService {
		@Override
		public List<College> getAllColleges() {
			College college = createCollege("地质工程系", "地质系", "1");
			College college2 = createCollege("计算机技术与应用系", "计算机系", "2");
			College college3 = createCollege("土木工程学院", "土院", "3");
			return Arrays.asList(college, college2, college3);
		}

		private College createCollege(String collegeName, String simpleName, String number) {
			College college = new College();
			college.setCollegeName(collegeName);
			college.setSimpleName(simpleName);
			college.setNumber(number);
			return college;
		}
	}

	@Test
	public void testManageCollegeDisplay() {
		MockableSpringBeanInjector.mockBean("collegeService",
				factory.implementAbstractMethods(MockCollegeService.class));
		final WicketSeleniumDriver ws = WebPageTestContext.getWicketSelenium();
		ws.openNonBookmarkablePage(ManageCollege.class);
		ws.wait(new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver arg0) {
				return ws.isElementPresent("//collegeItem");
			}
		});
		ws.findWicketElement("//collegeItem");
		isTrue(ws.getText("//collegeItem[0]//collegeName").equals("地质工程系"));
		isTrue(ws.getText("//collegeItem[1]//collegeName").equals("计算机技术与应用系"));
		isTrue(ws.getText("//collegeItem[2]//collegeName").equals("土木工程学院"));
		isTrue(ws.getText("//collegeItem[0]//simpleName").equals("地质系"));
		isTrue(ws.getText("//collegeItem[1]//simpleName").equals("计算机系"));
		isTrue(ws.getText("//collegeItem[2]//simpleName").equals("土院"));
		isTrue(ws.getText("//collegeItem[0]//number").equals("1"));
		isTrue(ws.getText("//collegeItem[1]//number").equals("2"));
		isTrue(ws.getText("//collegeItem[2]//number").equals("3"));
	}
}

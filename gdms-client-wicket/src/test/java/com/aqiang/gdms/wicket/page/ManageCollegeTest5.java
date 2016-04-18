package com.aqiang.gdms.wicket.page;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;

import com.aqiang.bsms.entities.College;
import com.aqiang.bsms.entities.CollegeManager;
import com.aqiang.bsms.service.CollegeService;
import com.aqiang.gdms.wicket.PageTest;
import com.ttdev.wicketpagetest.MockableSpringBeanInjector;
import com.ttdev.wicketpagetest.WebPageTestContext;
import com.ttdev.wicketpagetest.WicketSeleniumDriver;

public class ManageCollegeTest5 extends PageTest {

	abstract class MockCollegeService implements CollegeService {

		private List<College> colleges = new ArrayList<College>();

		@Override
		public List<College> getAllColleges() {
			if (colleges.size() == 0) {
				College college = createCollege("地质工程系", "地质系", "1");
				colleges.add(college);

				College college2 = createCollege("计算机技术与应用系", "计算机系", "2");
				CollegeManager collegeManager2 = createCollegeManager("liuzhiqiang", "刘志强");
				college2.setCollegeManager(collegeManager2);
				colleges.add(college2);

				College college3 = createCollege("土木工程学院", "土院", "3");
				colleges.add(college3);
			}
			return colleges;
		}

		private CollegeManager createCollegeManager(String username, String compellation) {
			CollegeManager collegeManager = new CollegeManager();
			collegeManager.setUsername(username);
			collegeManager.setCompellation(compellation);
			return collegeManager;
		}

		@Override
		public void margeEntity(College t) {
			for (College college : colleges) {
				if (college.getId() == t.getId()) {
					college.setCollegeName(t.getCollegeName());
					college.setSimpleName(t.getSimpleName());
					college.setNumber(t.getNumber());
					college.setDescription(t.getDescription());
				}
			}
		}

		@Override
		public void deleteEntity(int id) {
			for (College college : colleges) {
				if (college.getId() == id) {
					colleges.remove(college);
				}
			}
		}

		private College createCollege(String collegeName, String simpleName, String number) {
			College college = new College();
			college.setId(colleges.size() + 1);
			college.setCollegeName(collegeName);
			college.setSimpleName(simpleName);
			college.setNumber(number);
			return college;
		}

		@Override
		public College createCollege(College college) {
			college.setId(colleges.size() + 1);
			colleges.add(college);
			return college;
		}

		@Override
		public void changeCollegeManager(College college, CollegeManager collegeManager) {
			for (College college2 : colleges) {
				if (college2.getId() == college.getId()) {
					college2.setCollegeManager(collegeManager);
				}
			}
		}
	}

	@Test
	public void testChangeCollegeManager() {
		MockableSpringBeanInjector.mockBean("collegeService",
				factory.implementAbstractMethods(MockCollegeService.class));
		final WicketSeleniumDriver ws = WebPageTestContext.getWicketSelenium();
		ws.openNonBookmarkablePage(ManageCollege.class);
		isTrue(ws.getText("//collegeItem[1]//managerCollegeManager").equals("刘志强"));
		isTrue(ws.isElementPresent("//editCollegeManagerForm"));
		isTrue(ws.isElementPresent("//editCollegeManagerForm//username"));
		isTrue(ws.isElementPresent("//editCollegeManagerForm//compellation"));
		ws.click("//collegeItem[0]//managerCollegeManager");
		ws.wait(new ExpectedCondition<Boolean>() {

			@Override
			public Boolean apply(WebDriver arg0) {
				return ws.isElementPresent("//editCollegeManagerForm");
			}
		});
		ws.sendKeys("//editCollegeManagerForm//username", "chendawen");
		ws.sendKeys("editCollegeManagerForm//compellation", "陈大文");
		ws.click("//editCollegeManagerSubmit");
		ws.wait(new ExpectedCondition<Boolean>() {

			@Override
			public Boolean apply(WebDriver arg0) {
				return ws.isElementPresent("//collegeItem");
			}
		});
		isTrue(ws.getText("//collegeItem[0]//managerCollegeManager").equals("陈大文"));
		ws.click("//collegeItem[1]//managerCollegeManager");
		ws.wait(new ExpectedCondition<Boolean>() {

			@Override
			public Boolean apply(WebDriver arg0) {
				return ws.isElementPresent("//editCollegeManagerForm");
			}
		});
		isTrue(ws.getValue("//editCollegeManagerForm//username").equals("liuzhiqiang"));
		isTrue(ws.getValue("//editCollegeManagerForm//compellation").equals("刘志强"));
		ws.clear("//editCollegeManagerForm//username");
		ws.sendKeys("//editCollegeManagerForm//username", "lizhiqiang");
		ws.clear("//editCollegeManagerForm//compellation");
		ws.sendKeys("editCollegeManagerForm//compellation", "李志强");
		isTrue(ws.isElementPresent("//editCollegeManagerSubmit"));
		ws.click("//editCollegeManagerSubmit");
		ws.wait(new ExpectedCondition<Boolean>() {

			@Override
			public Boolean apply(WebDriver arg0) {
				return ws.isElementPresent("//collegeItem");
			}
		});
		isTrue(ws.getText("//collegeItem[1]//managerCollegeManager").equals("李志强"));
	}
}

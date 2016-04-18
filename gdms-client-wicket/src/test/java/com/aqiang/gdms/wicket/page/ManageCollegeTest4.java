package com.aqiang.gdms.wicket.page;

import java.util.ArrayList;
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

public class ManageCollegeTest4 extends PageTest {

	abstract class MockCollegeService implements CollegeService {

		private List<College> colleges = new ArrayList<College>();

		@Override
		public List<College> getAllColleges() {
			if (colleges.size() == 0) {
				colleges.add(createCollege("地质工程系", "地质系", "1"));
				colleges.add(createCollege("计算机技术与应用系", "计算机系", "2"));
				colleges.add(createCollege("土木工程学院", "土院", "3"));
			}
			return colleges;
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
	}

	@Test
	public void testDeleteCollege() {
		MockableSpringBeanInjector.mockBean("collegeService",
				factory.implementAbstractMethods(MockCollegeService.class));
		final WicketSeleniumDriver ws = WebPageTestContext.getWicketSelenium();
		ws.openNonBookmarkablePage(ManageCollege.class);
		isTrue(ws.getText("//collegeItem[0]//collegeName").equals("地质工程系"));
		isTrue(ws.getText("//collegeItem[1]//collegeName").equals("计算机技术与应用系"));
		isTrue(ws.getText("//collegeItem[2]//collegeName").equals("土木工程学院"));
		isTrue(ws.getText("//collegeItem[0]//simpleName").equals("地质系"));
		isTrue(ws.getText("//collegeItem[1]//simpleName").equals("计算机系"));
		isTrue(ws.getText("//collegeItem[2]//simpleName").equals("土院"));
		isTrue(ws.getText("//collegeItem[0]//number").equals("1"));
		isTrue(ws.getText("//collegeItem[1]//number").equals("2"));
		isTrue(ws.getText("//collegeItem[2]//number").equals("3"));
		ws.click("//collegeItem[1]//delete");
		pause(1);
		ws.getSelenium().switchTo().alert().accept();
		ws.wait(new ExpectedCondition<Boolean>() {

			@Override
			public Boolean apply(WebDriver arg0) {
				return ws.isElementPresent("//collegeItem");
			}
		});
		isTrue(ws.getText("//collegeItem[0]//collegeName").equals("地质工程系"));
		isTrue(ws.getText("//collegeItem[1]//collegeName").equals("土木工程学院"));
		isTrue(ws.getText("//collegeItem[0]//simpleName").equals("地质系"));
		isTrue(ws.getText("//collegeItem[1]//simpleName").equals("土院"));
		isTrue(ws.getText("//collegeItem[0]//number").equals("1"));
		isTrue(ws.getText("//collegeItem[1]//number").equals("3"));
	}
}

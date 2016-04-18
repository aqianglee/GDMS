package com.aqiang.gdms.wicket.page;

import org.junit.Test;

import com.aqiang.gdms.wicket.PageTest;
import com.ttdev.wicketpagetest.WebPageTestContext;
import com.ttdev.wicketpagetest.WicketSeleniumDriver;

public class ManageChangeSubjectTest extends PageTest {

	@Test
	public void testCurrentInfoDisplay() {
		WicketSeleniumDriver ws = WebPageTestContext.getWicketSelenium();
		ws.openNonBookmarkablePage(ManageChangeSubject.class);
		isTrue(ws.isElementPresent("//return"));
	}

}

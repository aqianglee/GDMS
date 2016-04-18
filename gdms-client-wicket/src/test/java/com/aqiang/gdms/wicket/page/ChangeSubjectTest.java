package com.aqiang.gdms.wicket.page;

import org.junit.Test;

import com.aqiang.gdms.wicket.PageTest;
import com.ttdev.wicketpagetest.WebPageTestContext;
import com.ttdev.wicketpagetest.WicketSeleniumDriver;

public class ChangeSubjectTest extends PageTest {

	@Test
	public void testCurrentInfoDisplay() {
		WicketSeleniumDriver ws = WebPageTestContext.getWicketSelenium();
		ws.openNonBookmarkablePage(ChangeSubject.class);
		isTrue(ws.isElementPresent("//return"));
	}
}

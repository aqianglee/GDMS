package com.aqiang.gdms.wicket;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Before;

import com.ttdev.wicketpagetest.ChangeResistantMockFactory;
import com.ttdev.wicketpagetest.MockableSpringBeanInjector;

public class PageTest {
	protected ChangeResistantMockFactory factory;

	@Before
	public void init() {
		factory = new ChangeResistantMockFactory(this);
		MockableSpringBeanInjector.clearMockBeans();
	}

	public void pause(long seconds) {
		try {
			Thread.sleep(seconds * 1000);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	public void isTrue(boolean result) {
		assertTrue(result);
	}

	public void isNull(Object object) {
		assertNull(object);
	}

	public void isNotNull(Object object) {
		assertNotNull(object);
	}

	public void isEq(Object expected, Object actual) {
		assertEquals(expected, actual);
	}

	public Date date(String dateStr, String format) {
		Date date = null;
		try {
			date = new SimpleDateFormat(format).parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
}

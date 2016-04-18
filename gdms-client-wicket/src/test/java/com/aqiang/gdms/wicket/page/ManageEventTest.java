package com.aqiang.gdms.wicket.page;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.aqiang.bsms.entities.College;
import com.aqiang.bsms.entities.Event;
import com.aqiang.bsms.entities.WorkflowStatus;
import com.aqiang.bsms.service.EventService;
import com.aqiang.gdms.wicket.PageTest;
import com.ttdev.wicketpagetest.MockableSpringBeanInjector;
import com.ttdev.wicketpagetest.WebPageTestContext;
import com.ttdev.wicketpagetest.WicketSeleniumDriver;

public class ManageEventTest extends PageTest {

	abstract class MockEventService implements EventService {

		private List<Event> events = new ArrayList<Event>();

		@Override
		public Event getCurrentEvent(College college) {
			for (Event event : events) {
				if (event.getActive()) {
					return event;
				}
			}
			return null;
		}

		private Event createEvent(College college, String year, String status, String beginDate, String endDate,
				Boolean active) {
			Event event = new Event();
			event.setCollege(college);
			event.setActive(active);
			event.setYear(year);
			event.setWorkFlowStatus(status);
			event.setBeginDate(date(beginDate, "yyyy-MM-dd"));
			event.setEndDate(date(endDate, "yyyy-MM-dd"));
			event.setId(events.size() + 1);
			return event;
		}

		@Override
		public boolean createNewEvent(Event event) {

			return false;
		}

		@Override
		public List<Event> getEvents(College college) {
			if (events.size() == 0) {
				events.add(createEvent(college, "2014", WorkflowStatus.SELECT_SUBJECT, "2014-03-01", "2014-06-01", true));
				events.add(createEvent(college, "2015", WorkflowStatus.SELECT_SUBJECT, "2015-03-01", "2015-06-01",
						false));
				events.add(createEvent(college, "2016", WorkflowStatus.SELECT_SUBJECT, "2016-03-01", "2016-06-01",
						false));
			}
			return events;
		}

		@Override
		public void changeToActive(Event event) {
			for (Event e : events) {
				e.setActive(false);
			}

			for (Event e : events) {
				if (e.getId() == event.getId()) {
					e.setActive(true);
					return;
				}
			}
		}
	}

	@Test
	public void testDesplayInfo() {
		MockableSpringBeanInjector.mockBean("eventService",
				factory.implementAbstractMethods(MockEventService.class));
		WicketSeleniumDriver ws = WebPageTestContext.getWicketSelenium();
		ws.openNonBookmarkablePage(ManageEvent.class);
		pause(10);
		// TODO complete this test
	}
}

package com.aqiang.gdms.wicket.page;

import java.util.List;

import org.apache.wicket.Session;
import org.junit.Test;

import com.aqiang.bsms.entities.College;
import com.aqiang.bsms.entities.Event;
import com.aqiang.bsms.entities.Specialty;
import com.aqiang.bsms.entities.Student;
import com.aqiang.bsms.entities.User;
import com.aqiang.bsms.entities.UserType;
import com.aqiang.bsms.service.EventService;
import com.aqiang.bsms.service.SpecialtyService;
import com.aqiang.bsms.service.UserService;
import com.aqiang.gdms.wicket.PageTest;
import com.aqiang.gdms.wicket.service.SignService;
import com.ttdev.wicketpagetest.MockableSpringBeanInjector;
import com.ttdev.wicketpagetest.WebPageTestContext;
import com.ttdev.wicketpagetest.WicketSeleniumDriver;

public class StudentAboutMeTest extends PageTest {
	abstract class MockEventService implements EventService {
		@Override
		public Event getCurrentEvent(College college) {
			return null;
		}
	}

	abstract class MockSignService implements SignService {
		@Override
		public User getSignUser(Session session) {
			Student student = new Student();
			student.setCompellation("陈大文");
			return student;
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

	abstract class MockUserService implements UserService {
		@Override
		public boolean modefyPassword(User user, String password, String newPassword) {

			return false;
		}

		@Override
		public void margeEntity(User t) {

		}
	}

	abstract class MockSpecialtyService implements SpecialtyService {

		private List<Specialty> specialties;

		@Override
		public List<Specialty> getSpecialtiesByCollege(College college) {
			if (specialties.size() == 0) {
				specialties.add(createSpecialty(college, "计算机科学与技术2012级", "1"));
				specialties.add(createSpecialty(college, "化工学院", "2"));
				specialties.add(createSpecialty(college, "软件工程", "3"));
			}
			return specialties;
		}

		private Specialty createSpecialty(College college, String name, String num) {
			Specialty specialty = new Specialty();
			specialty.setCollege(college);
			specialty.setName(name);
			specialty.setNum(num);
			specialty.setId(specialties.size() + 1);
			return specialty;
		}
	}

	@Test
	public void testCurrentInfoDisplay() {
		MockableSpringBeanInjector.mockBean("signService", factory.implementAbstractMethods(MockSignService.class));
		MockableSpringBeanInjector.mockBean("eventService", factory.implementAbstractMethods(MockEventService.class));
		MockableSpringBeanInjector.mockBean("userService", factory.implementAbstractMethods(MockUserService.class));
		MockableSpringBeanInjector.mockBean("specialtyService",
				factory.implementAbstractMethods(MockSpecialtyService.class));
		WicketSeleniumDriver ws = WebPageTestContext.getWicketSelenium();
		ws.openNonBookmarkablePage(StudentAboutMe.class);
	}

}

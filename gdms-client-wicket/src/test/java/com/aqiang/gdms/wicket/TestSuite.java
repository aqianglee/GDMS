package com.aqiang.gdms.wicket;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.aqiang.gdms.wicket.page.ChangeSubjectTest;
import com.aqiang.gdms.wicket.page.ManageChangeSubjectTest;
import com.ttdev.wicketpagetest.Configuration;
import com.ttdev.wicketpagetest.WebPageTestContext;

@RunWith(Suite.class)
@SuiteClasses({
//	BorderPageTest.class,
	ChangeSubjectTest.class,
//	DesktopTest.class ,
//	DesktopTest2.class ,
//	DesktopTest3.class ,
//	DesktopTest4.class ,
//	DesktopTest5.class ,
//	DesktopTest6.class ,
//	DesktopTest7.class ,
//	DesktopTest8.class ,
//	EditSubjectTest.class ,
//	EditTaskTest.class ,
//	EditTaskTest2.class ,
//	ExamSubjectTest.class,
//	GuideGraduadeDesignTest.class,
//	LoginPageTest.class ,
	ManageChangeSubjectTest.class,
//	ManageCollegeTest.class ,
//	ManageCollegeTest2.class ,
//	ManageCollegeTest3.class ,
//	ManageCollegeTest4.class ,
//	ManageCollegeTest5.class ,
//	ManageEventTest.class ,
//	ManageScheduleTest.class ,
//	ManageScheduleTest2.class ,
//	ManageStudentTest.class ,
//	ManageStudentTest2.class ,
//	ManageSubjectTest.class ,
//	ManageTeacherTest.class ,
//	ManageTeachAndResearchOfficeTest.class ,
//	MyGraduadeDesignTest.class ,
//	MyGraduadeDesignTest2.class ,
//	MyGraduadeDesignTest3.class ,
//	StudentAboutMeTest.class,
//	SelectSubjectTest.class
})
public class TestSuite {
	@BeforeClass
	static public void setUp() throws Exception {
		Configuration cfg = new Configuration();
		cfg.setOverrideWebXml("web-test.xml");
		WebPageTestContext.beforePageTests(cfg);
	}

	@AfterClass
	static public void tearDown() throws Exception {
		WebPageTestContext.afterPageTests();
	}
}

package com.aqiang.gdms;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.After;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.aqiang.bsms.service.AuthorityService;
import com.aqiang.bsms.service.CollegeManagerService;
import com.aqiang.bsms.service.CollegeService;
import com.aqiang.bsms.service.DownloadService;
import com.aqiang.bsms.service.EventService;
import com.aqiang.bsms.service.FileService;
import com.aqiang.bsms.service.GenerateFile;
import com.aqiang.bsms.service.GroupService;
import com.aqiang.bsms.service.LoginService;
import com.aqiang.bsms.service.MessageService;
import com.aqiang.bsms.service.ParameterService;
import com.aqiang.bsms.service.ScheduleItemService;
import com.aqiang.bsms.service.ScheduleService;
import com.aqiang.bsms.service.SpecialtyService;
import com.aqiang.bsms.service.StateService;
import com.aqiang.bsms.service.StudentService;
import com.aqiang.bsms.service.SubjectService;
import com.aqiang.bsms.service.SystemManagerService;
import com.aqiang.bsms.service.TaskService;
import com.aqiang.bsms.service.TeachAndResearchOfficeService;
import com.aqiang.bsms.service.TeacherService;
import com.aqiang.bsms.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:beans-test.xml")
public class BaseTest extends AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	protected AuthorityService authorityService;
	@Autowired
	protected CollegeManagerService collegeManagerService;
	@Autowired
	protected CollegeService collegeService;
	@Autowired
	protected DownloadService downloadService;
	@Autowired
	protected EventService eventService;
	@Autowired
	protected FileService fileService;
	@Autowired
	protected GenerateFile generateFile;
	@Autowired
	protected GroupService groupService;
	@Autowired
	protected LoginService loginService;
	@Autowired
	protected MessageService messageService;
	@Autowired
	protected ParameterService parameterService;
	@Autowired
	protected ScheduleService scheduleService;
	@Autowired
	protected ScheduleItemService scheduleItemService;
	@Autowired
	protected SpecialtyService specialtyService;
	@Autowired
	protected StateService stateService;
	@Autowired
	protected StudentService studentService;
	@Autowired
	protected SubjectService subjectService;
	@Autowired
	protected SystemManagerService systemManagerService;
	@Autowired
	protected TaskService taskService;
	@Autowired
	protected TeachAndResearchOfficeService teachAndResearchOfficeService;
	@Autowired
	protected TeacherService teacherService;
	@Autowired
	protected UserService userService;

	protected Logger logger = LoggerFactory.getLogger(BaseTest.class);

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

	@After
	public void destroy() {
		clearDB();
	}

	private void clearDB() {
		messageService.deleteAll();
		parameterService.deleteAll();
		taskService.deleteAll();
		subjectService.deleteAll();
		scheduleItemService.deleteAll();
		scheduleService.deleteAll();
		studentService.deleteAll();
		groupService.deleteAll();
		specialtyService.deleteAll();
		eventService.deleteAll();
		systemManagerService.deleteAll();
		teachAndResearchOfficeService.deleteAll();
		teacherService.deleteAll();
		collegeService.deleteAll();
		collegeManagerService.deleteAll();
		stateService.deleteAll();
		authorityService.deleteAll();
		fileService.deleteAll();
		userService.deleteAll();
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

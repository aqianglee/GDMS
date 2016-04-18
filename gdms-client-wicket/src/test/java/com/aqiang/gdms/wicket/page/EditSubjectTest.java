package com.aqiang.gdms.wicket.page;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.Session;
import org.junit.Test;
import org.openqa.selenium.support.ui.Select;
import org.springframework.util.StringUtils;

import com.aqiang.bsms.entities.College;
import com.aqiang.bsms.entities.Event;
import com.aqiang.bsms.entities.JobType;
import com.aqiang.bsms.entities.Parameter;
import com.aqiang.bsms.entities.Specialty;
import com.aqiang.bsms.entities.Subject;
import com.aqiang.bsms.entities.SubjectApplyType;
import com.aqiang.bsms.entities.TeachAndResearchOffice;
import com.aqiang.bsms.entities.Teacher;
import com.aqiang.bsms.entities.User;
import com.aqiang.bsms.entities.UserType;
import com.aqiang.bsms.service.ParameterService;
import com.aqiang.bsms.service.SpecialtyService;
import com.aqiang.bsms.service.SubjectService;
import com.aqiang.bsms.service.TeachAndResearchOfficeService;
import com.aqiang.bsms.service.TeacherService;
import com.aqiang.gdms.wicket.PageTest;
import com.aqiang.gdms.wicket.service.SignService;
import com.ttdev.wicketpagetest.MockableSpringBeanInjector;
import com.ttdev.wicketpagetest.WebPageTestContext;
import com.ttdev.wicketpagetest.WicketSeleniumDriver;

public class EditSubjectTest extends PageTest {
	private Subject subject;

	abstract class MockSignService implements SignService {
		@Override
		public College getCollege(Session session) {
			College college = new College();
			college.setCollegeName("计算机科学与技术");
			return college;
		}

		@Override
		public String getUserType(Session session) {
			return UserType.STUDENT;
		}

		@Override
		public User getSignUser(Session session) {
			Teacher teacher = new Teacher();
			teacher.setCollege(getCollege(session));
			teacher.setCompellation("陈大文");
			return teacher;
		}
	}

	abstract class MockSpecialtyService implements SpecialtyService {
		private List<Specialty> specialtys = new ArrayList<Specialty>();

		@Override
		public List<Specialty> getSpecialtiesByCollege(College college) {
			if (specialtys.size() == 0) {
				specialtys.add(createSpecialty(college, "软件工程", "1"));
				specialtys.add(createSpecialty(college, "计算机科学与技术", "2"));
			}
			return specialtys;
		}

		private Specialty createSpecialty(College college, String name, String num) {
			Specialty specialty = new Specialty();
			specialty.setCollege(college);
			specialty.setName(name);
			specialty.setNum(num);
			specialty.setId(specialtys.size() + 1);
			return specialty;
		}

	}

	abstract class MockTeachAndResearchOfficeService implements TeachAndResearchOfficeService {
		private List<TeachAndResearchOffice> teacherAndResearchOffices = new ArrayList<TeachAndResearchOffice>();

		@Override
		public List<TeachAndResearchOffice> getTeachAndResearchOfficeByCollege(College college) {
			if (teacherAndResearchOffices.size() == 0) {
				teacherAndResearchOffices.add(createTeachAndResearchOffice(college, "数据结构教研室", "1"));
				teacherAndResearchOffices.add(createTeachAndResearchOffice(college, "云计算教研室", "2"));
				teacherAndResearchOffices.add(createTeachAndResearchOffice(college, "软件工程教研室", "3"));
			}
			return teacherAndResearchOffices;
		}

		private TeachAndResearchOffice createTeachAndResearchOffice(College college, String name, String number) {
			TeachAndResearchOffice andResearchOffice = new TeachAndResearchOffice();
			andResearchOffice.setName(name);
			andResearchOffice.setNumber(number);
			andResearchOffice.setCollege(college);
			andResearchOffice.setId(teacherAndResearchOffices.size() + 1);
			return andResearchOffice;
		}
	}

	abstract class MockSubjectService implements SubjectService {

		@Override
		public void editSubject(Subject subject) {
			EditSubjectTest.this.subject = subject;
		}

	};

	abstract class MockParameterService implements ParameterService {
		@Override
		public Parameter getParameterByName(String name) {
			Parameter parameter = new Parameter();
			parameter.setValue("D://test/");
			return parameter;
		}
	}

	abstract class MockTeacherService implements TeacherService {
		@Override
		public String getJobType(String jobType) {
			if (jobType == null || StringUtils.isEmpty(jobType)) {
				return "";
			}
			if (jobType.equals(JobType.ASSISTANT)) {
				return "助教";
			}
			if (jobType.equals(JobType.LECTURER)) {
				return "讲师";
			}
			if (jobType.equals(JobType.ASSOCIATE_PROFESSOR)) {
				return "副教授";
			}
			return "教授";
		}
	}

	@Test
	public void testCreateNewSubject() {
		MockableSpringBeanInjector.mockBean("signService", factory.implementAbstractMethods(MockSignService.class));
		MockableSpringBeanInjector.mockBean("specialtyService",
				factory.implementAbstractMethods(MockSpecialtyService.class));
		MockableSpringBeanInjector.mockBean("teachAndResearchOfficeService",
				factory.implementAbstractMethods(MockTeachAndResearchOfficeService.class));
		MockableSpringBeanInjector.mockBean("subjectService",
				factory.implementAbstractMethods(MockSubjectService.class));
		MockableSpringBeanInjector.mockBean("parameterService",
				factory.implementAbstractMethods(MockParameterService.class));
		MockableSpringBeanInjector.mockBean("teacherService",
				factory.implementAbstractMethods(MockTeacherService.class));

		WicketSeleniumDriver ws = WebPageTestContext.getWicketSelenium();
		Subject subject = new Subject();
		College college = new College();
		college.setCollegeName("计算机技术与应用系");
		subject.setCollege(college);
		subject.setSubjectApplyType(SubjectApplyType.COMPANY_SUBJECT);
		Event event = new Event();
		event.setYear("2016");
		event.setBeginDate(date("2016-3-1", "yyyy-MM-dd"));
		event.setEndDate(date("2016-6-1", "yyyy-MM-dd"));
		subject.setEvent(event);
		ws.openNonBookmarkablePage(EditSubject.class, subject);
		isTrue(ws.isElementPresent("//return"));
		isTrue(ws.isElementPresent("//form"));
		isTrue(ws.isElementPresent("//subjectApplyType"));
		isTrue(!ws.findWicketElement("//subjectApplyType").isSelected());
		Select subjectApplyType = new Select(ws.findWicketElement("//subjectApplyType"));
		isTrue(subjectApplyType.getOptions().size() == 5);
		isTrue(ws.isElementPresent("//subjectType"));
		isTrue(!ws.findWicketElement("//subjectType").isSelected());
		Select subjectType = new Select(ws.findWicketElement("//subjectType"));
		isTrue(subjectType.getOptions().size() == 3);
		subjectType.selectByIndex(2);
		isTrue(ws.isElementPresent("//subjectSource"));
		isTrue(!ws.findWicketElement("//subjectSource").isSelected());
		Select subjectSource = new Select(ws.findWicketElement("//subjectSource"));
		isTrue(subjectSource.getOptions().size() == 5);
		subjectSource.selectByIndex(3);
		isTrue(ws.isElementPresent("//college.collegeName"));
		isTrue("计算机技术与应用系".equals(ws.getValue("//college.collegeName")));
		isTrue(ws.isElementPresent("//teachAndResearchOffice"));
		isTrue(!ws.findWicketElement("//teachAndResearchOffice").isSelected());
		Select teachAndResearchOffice = new Select(ws.findWicketElement("//teachAndResearchOffice"));
		isTrue(teachAndResearchOffice.getOptions().size() == 4);
		teachAndResearchOffice.selectByIndex(1);
		isTrue(ws.isElementPresent("//topicChoosingWay"));
		ws.sendKeys("//topicChoosingWay", "信息技术方向——基于敏捷开发的校园超市系统");
		isTrue(ws.isElementPresent("//don"));
		ws.sendKeys("//don", "王晓英");
		isTrue(ws.isElementPresent("//donJob"));
		isTrue(!ws.findWicketElement("//donJob").isSelected());
		Select donJob = new Select(ws.findWicketElement("//donJob"));
		isTrue(donJob.getOptions().size() == 5);
		donJob.selectByIndex(4);
		isTrue(ws.isElementPresent("//tutorName"));
		ws.sendKeys("//tutorName", "王晓英");
		isTrue(ws.isElementPresent("//tutorJob"));
		isTrue(!ws.findWicketElement("//tutorJob").isSelected());
		Select tutorJob = new Select(ws.findWicketElement("//tutorJob"));
		isTrue(tutorJob.getOptions().size() == 5);
		tutorJob.selectByIndex(1);
		isTrue(ws.isElementPresent("//beginTime"));
		ws.sendKeys("//beginTime", "2016-03-01");
		isTrue(ws.isElementPresent("//endTime"));
		ws.sendKeys("//endTime", "2016-06-01");
		isTrue(ws.isElementPresent("//specialty"));
		isTrue(!ws.findWicketElement("//specialty").isSelected());
		Select specialty = new Select(ws.findWicketElement("//specialty"));
		isTrue(specialty.getOptions().size() == 3);
		specialty.selectByIndex(1);
		isTrue(ws.isElementPresent("//guideNumber"));
		ws.sendKeys("//guideNumber", "1");
		isTrue(ws.isElementPresent("//taskFromAndTheoryBasis"));
		ws.sendKeys("//taskFromAndTheoryBasis",
				"课题是源自中青年基金“基于web技术和虚拟现实的土族文化传播系统的研究与实现”，课题基于HTML5、CSS3和THREE.JS技术。主要设计基于web的土族风情园的3D概貌与建筑，并实现在网页中能自由浏览每个建筑物内部景观。");
		isTrue(ws.isElementPresent("//content"));
		ws.sendKeys("//content", "本设计主要内容包括相关资源的采集、预处理和模型的建立。目前设计的实验设备和实验环境已经具备，通过毕业设计，锻炼学生信息的获取和处理能力。");
		isTrue(ws.isElementPresent("//workload"));
		ws.sendKeys("//workload",
				"设计工作量：搜集资料、系统设计、系统分析、系统实现等环节共计14周学生工作分配：1）掌握通过WEBGL技术在网页中画三维模型的能力；2）掌握三维模型的构建方法；3）掌握将三维网页移植到移动终端的方法。");
		isTrue(ws.isElementPresent("//budget"));
		ws.sendKeys("//budget", "0");
		isTrue(ws.isElementPresent("//teachAndResearchOfficeComment"));
		ws.sendKeys("//teachAndResearchOfficeComment", "0");
		isTrue(ws.isElementPresent("//collegeComment"));
		ws.sendKeys("//collegeComment", "0");
		isTrue(ws.isElementPresent("//submit"));
		ws.click("//submit");
	}
}

package com.aqiang.bsms.service.impl;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.aqiang.bsms.entities.College;
import com.aqiang.bsms.entities.JobType;
import com.aqiang.bsms.entities.Parameter;
import com.aqiang.bsms.entities.ParameterKey;
import com.aqiang.bsms.entities.Specialty;
import com.aqiang.bsms.entities.Subject;
import com.aqiang.bsms.entities.TeachAndResearchOffice;
import com.aqiang.bsms.service.CollegeService;
import com.aqiang.bsms.service.ParameterService;
import com.aqiang.bsms.service.SpecialtyService;
import com.aqiang.bsms.service.SubjectService;
import com.aqiang.bsms.service.TeachAndResearchOfficeService;
import com.aqiang.gdms.BaseTest;

public class SubjectServiceImplTest extends BaseTest {

	@Autowired
	private SubjectService service;
	@Autowired
	private TeachAndResearchOfficeService teachAndResearchOfficeService;
	@Autowired
	private CollegeService collegeService;
	@Autowired
	private SpecialtyService specialtyService;
	@Autowired
	private ParameterService parameterService;

	@Test
	public void testEditSubjectWithEdit() {
		Parameter parameter = new Parameter();
		parameter.setName(ParameterKey.FILE_ROOT_DIR);
		parameter.setValue("D://123/");
		parameterService.saveEntitiy(parameter);
		Subject subject = new Subject();
		TeachAndResearchOffice teachAndResearchOffice = new TeachAndResearchOffice();
		teachAndResearchOffice.setName("计算机教研室");
		teachAndResearchOfficeService.saveEntitiy(teachAndResearchOffice);
		subject.setTeachAndResearchOffice(teachAndResearchOffice);
		subject.setBeginTime(date("2015-12-1", "yyyy-MM-dd"));
		subject.setEndTime(date("2016-6-1", "yyyy-MM-dd"));
		subject.setBudget("0");
		College college = new College();
		college.setCollegeName("计算机技术与应用系");
		collegeService.saveEntitiy(college);
		subject.setCollege(college);
		Specialty specialty = new Specialty();
		specialty.setName("计算机科学与技术");
		specialtyService.saveEntitiy(specialty);
		subject.setTutorJob(JobType.PROFESSOR);
		subject.setDonJob(JobType.LECTURER);
		subject.setSpecialty(specialty);
		service.saveEntitiy(subject);
	}
}

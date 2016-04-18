package com.aqiang.bsms.service.impl;

import org.junit.Test;

import com.aqiang.bsms.entities.College;
import com.aqiang.bsms.entities.JobType;
import com.aqiang.bsms.entities.Specialty;
import com.aqiang.bsms.entities.Subject;
import com.aqiang.bsms.entities.TeachAndResearchOffice;
import com.aqiang.gdms.BaseTest;

public class GenerateFileImplTest extends BaseTest {

	@Test
	public void testFormatSubject() {
		Subject subject = new Subject();
		TeachAndResearchOffice teachAndResearchOffice = new TeachAndResearchOffice();
		teachAndResearchOffice.setName("计算机教研室");
		subject.setTeachAndResearchOffice(teachAndResearchOffice);
		subject.setBeginTime(date("2015-12-1", "yyyy-MM-dd"));
		subject.setEndTime(date("2016-6-1", "yyyy-MM-dd"));
		subject.setBudget("0");
		College college = new College();
		college.setCollegeName("计算机技术与应用系");
		subject.setCollege(college);
		Specialty specialty = new Specialty();
		specialty.setName("计算机科学与技术");
		subject.setTutorJob(JobType.PROFESSOR);
		subject.setDonJob(JobType.LECTURER);
		subject.setSpecialty(specialty);
		generateFile.generateSubject(subject, "D://doc1.doc");
	}

}

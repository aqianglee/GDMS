package com.aqiang.bsms.service.impl;

import java.util.List;

import org.junit.Test;

import com.aqiang.bsms.entities.College;
import com.aqiang.bsms.entities.TeachAndResearchOffice;
import com.aqiang.gdms.BaseTest;

public class TeachAndResearchOfficeServiceImplTest extends BaseTest {

	@Test
	public void testGetTeacherAndResearchOfficeByCollege() {
		College college = createCollege("计算机系");
		College college2 = createCollege("地址工程系");

		createTeachAndResearchOffice("计算机教研室", "1", college);
		createTeachAndResearchOffice("测绘工程教研室", "10", college2);
		createTeachAndResearchOffice("地址工程教研室", "11", college2);
		createTeachAndResearchOffice("资源勘测教研室", "12", college2);

		List<TeachAndResearchOffice> teacherAndResearchOfficeByCollege = teachAndResearchOfficeService
				.getTeachAndResearchOfficeByCollege(college);
		List<TeachAndResearchOffice> teacherAndResearchOfficeByCollege2 = teachAndResearchOfficeService
				.getTeachAndResearchOfficeByCollege(college2);

		isTrue(teacherAndResearchOfficeByCollege.get(0).getName()
				.equals("计算机教研室"));
		isTrue(teacherAndResearchOfficeByCollege2.get(0).getName()
				.equals("测绘工程教研室"));
		isTrue(teacherAndResearchOfficeByCollege2.get(1).getName()
				.equals("地址工程教研室"));
		isTrue(teacherAndResearchOfficeByCollege2.get(2).getName()
				.equals("资源勘测教研室"));
	}

	private void createTeachAndResearchOffice(String name, String number,
			College college) {
		TeachAndResearchOffice teachAndResearchOffice = new TeachAndResearchOffice();
		teachAndResearchOffice.setNumber(number);
		teachAndResearchOffice.setName(name);
		teachAndResearchOffice.setCollege(college);
		teachAndResearchOfficeService.saveEntitiy(teachAndResearchOffice);
	}

	private College createCollege(String name) {
		College college = new College();
		college.setCollegeName(name);
		collegeService.saveEntitiy(college);
		return college;
	}

}

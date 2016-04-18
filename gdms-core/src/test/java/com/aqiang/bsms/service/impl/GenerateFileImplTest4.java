package com.aqiang.bsms.service.impl;

import org.junit.Test;

import com.aqiang.bsms.entities.Specialty;
import com.aqiang.bsms.entities.Student;
import com.aqiang.bsms.entities.Subject;
import com.aqiang.bsms.entities.Task;
import com.aqiang.bsms.entities.Teacher;
import com.aqiang.gdms.BaseTest;

public class GenerateFileImplTest4 extends BaseTest {

	@Test
	public void testGenerateTask() {
		Subject subject = new Subject();
		subject.setTopicChoosingWay("信息技术方向");
		subjectService.saveEntitiy(subject);

		Specialty specialty = new Specialty();
		specialty.setName("计算机科学与技术2012级");
		specialtyService.saveEntitiy(specialty);

		Student student = new Student();
		student.setCompellation("李志强");
		student.setSpecialty(specialty);
		studentService.saveEntitiy(student);

		Teacher teacher = new Teacher();
		teacher.setCompellation("曹腾飞");
		teacherService.saveEntitiy(teacher);

		Task task = new Task();
		task.setSubject(subject);
		task.setStudent(student);
		task.setTeacher(teacher);
		task.setCompany("青海大学");
		task.setDissertationMainContent("基于本题目，应设计和实现一套校园超市购物系统设计与实现，包括如下功能：\n "
				+ "1. 数据字典：支持商品分类、订单、购物车、客户信息等模块的管理。\n " + "2. 商品管理：支持对类别、名称、标签、商品的增、删、改、查功能。 \n "
				+ "3. 分类管理：支持对商品一级二级分类的管理功能。\n " + "4. 用户管理：针对管理员和使用者几种用户实现分类管理功能。\n "
				+ "5. 订单管理：实现对货物下订单、查询订单、删除修改订单等管理功能。\n " + "6. 混合管理方式：同时支持Android和桌面两种访问方式。");
		task.setMainTask(" 1.	搭建测试驱动开发的项目，并使用测试驱动开发的开发方式，实现可以满足基本需求的项目。\n " + "2.	根据敏捷开发的原则重构代码，使代码可以更好的重用，和可扩展。\n "
				+ "3.	根据项目细化需求，对项目进行迭代更新。整个开发过程都使用测试驱动开发，对代码进行全覆盖测试，以保证代码质量。\n " + "4.	根据项目设计、开发测试等思路撰写毕业设计说明书。\n ");
		String fileName = "D://abc.doc";
		generateFile.generateTask(task, fileName);
	}

}

package com.aqiang.bsms.service.impl;

import java.util.List;

import org.junit.Test;

import com.aqiang.bsms.entities.Specialty;
import com.aqiang.bsms.entities.Student;
import com.aqiang.bsms.entities.Subject;
import com.aqiang.bsms.entities.Task;
import com.aqiang.bsms.entities.Teacher;
import com.aqiang.gdms.BaseTest;

public class TaskServiceImplTest extends BaseTest {

	@Test
	public void testEditTask() {
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
		task.setDissertationMainContent("");
		task.setMainTask("");

		taskService.editTask(task);
		List<Task> tasks = taskService.getAll();
		isEq(tasks.size(), 1);
		Task task2 = tasks.get(0);
		isEq(task2.getCompany(), "青海大学");
	}

}

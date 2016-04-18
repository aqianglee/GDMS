package com.aqiang.bsms.service;

import java.util.List;

import com.aqiang.bsms.entities.College;
import com.aqiang.bsms.entities.Event;
import com.aqiang.bsms.entities.Group;
import com.aqiang.bsms.entities.Student;
import com.aqiang.bsms.entities.Subject;
import com.aqiang.bsms.exception.UsernameHasUsedException;

public interface StudentService extends BaseService<Student> {

	public void addNewStudent(Student student) throws UsernameHasUsedException;

	public List<Student> getStudentsByCollege(College college);

	public void deleteUnconfirmedSubjects(Student student, Subject subject);

	public void deleteUnConfirmedStudents(Student student, Subject subject);

	public boolean isConfirmByTeacher(Student student);

	public List<Student> getStudentsByGroup(Group group);

	public List<Student> getStudentsByCollegeAndCurrnetEvent(College college, Event currentEvent);

	public boolean hasSelected(Student student, Subject subject);

}

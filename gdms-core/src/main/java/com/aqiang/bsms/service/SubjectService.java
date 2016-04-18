package com.aqiang.bsms.service;

import java.util.List;

import com.aqiang.bsms.entities.College;
import com.aqiang.bsms.entities.Event;
import com.aqiang.bsms.entities.Student;
import com.aqiang.bsms.entities.Subject;
import com.aqiang.bsms.entities.User;

public interface SubjectService extends BaseService<Subject> {

	public List<Subject> getMyAllSubject(User user);

	public void deleteSubject(Subject subject);

	public void addStudentToSelectList(Student student, Subject subject);

	public Subject getSubjectById(int id);

	public void confirmeStudent(Subject subject, Student student);

	public List<Subject> getAllSubjectByCollege(College college);

	public void passAll(List<Subject> object);

	public List<Subject> getAllSubjects(College college, Event currentEvent);

	public void editSubject(Subject subject);

	public List<Subject> getSelectAbleSubject(College college);

	public List<Student> getSelectedStudents(Subject subject);

}

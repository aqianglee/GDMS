package com.aqiang.bsms.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 附件4实体
 * 
 * @author developer
 * 
 */
//@Entity
public class ChangeSubject implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private Integer id;
	private Teacher oldTeacher;
	private Subject oldSubject;
	private Teacher newTeacher;
	private Subject newSubject;
	private String reason;
	private String teacherComment;
	private String collegeComment;
	private Boolean collegeResult;
	private Boolean oldTeacherResult;
	private Boolean newTeacherResult;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Teacher getOldTeacher() {
		return oldTeacher;
	}

	public void setOldTeacher(Teacher oldTeacher) {
		this.oldTeacher = oldTeacher;
	}

	public Subject getOldSubject() {
		return oldSubject;
	}

	public void setOldSubject(Subject oldSubject) {
		this.oldSubject = oldSubject;
	}

	public Teacher getNewTeacher() {
		return newTeacher;
	}

	public void setNewTeacher(Teacher newTeacher) {
		this.newTeacher = newTeacher;
	}

	public Subject getNewSubject() {
		return newSubject;
	}

	public void setNewSubject(Subject newSubject) {
		this.newSubject = newSubject;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getTeacherComment() {
		return teacherComment;
	}

	public void setTeacherComment(String teacherComment) {
		this.teacherComment = teacherComment;
	}

	public String getCollegeComment() {
		return collegeComment;
	}

	public void setCollegeComment(String collegeComment) {
		this.collegeComment = collegeComment;
	}

	public Boolean getCollegeResult() {
		return collegeResult;
	}

	public void setCollegeResult(Boolean collegeResult) {
		this.collegeResult = collegeResult;
	}

	public Boolean getOldTeacherResult() {
		return oldTeacherResult;
	}

	public void setOldTeacherResult(Boolean oldTeacherResult) {
		this.oldTeacherResult = oldTeacherResult;
	}

	public Boolean getNewTeacherResult() {
		return newTeacherResult;
	}

	public void setNewTeacherResult(Boolean newTeacherResult) {
		this.newTeacherResult = newTeacherResult;
	}

}

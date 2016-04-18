package com.aqiang.bsms.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 学生实体
 * 
 * @author Administrator
 */
@Entity
@Table(name = "STUDENTS")
public class Student extends User {
	private static final long serialVersionUID = 1L;
	// 院系
	private College college;
	// 专业班级
	private Specialty specialty;
	private String number;
	private List<Subject> subjects = new ArrayList<Subject>();
	private Group group;
	private Event event;
	private String work;
	private String likeArea;

	// 密保问题及答案，每个用户可以设置3个密保问题

	@ManyToOne
	@JoinColumn(name = "COLLEGE_ID")
	public College getCollege() {
		return college;
	}

	public void setCollege(College college) {
		this.college = college;
	}

	@ManyToOne
	@JoinColumn(name = "SPECIALTY_ID")
	public Specialty getSpecialty() {
		return specialty;
	}

	public void setSpecialty(Specialty specialty) {
		this.specialty = specialty;
	}

	@Column(length = 10)
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	@ManyToMany
	@JoinTable(name = "STUDENTS_SUBJECTS", joinColumns = { @JoinColumn(name = "STUDENT_ID", referencedColumnName = "ID") }, inverseJoinColumns = { @JoinColumn(name = "SUBJECT_ID", referencedColumnName = "ID") })
	public List<Subject> getSubjects() {
		return subjects;
	}

	public void setSubjects(List<Subject> subjects) {
		this.subjects = subjects;
	}

	@ManyToOne
	@JoinColumn(name = "EVENT_ID")
	public Event getEvent() {
		return event;
	}

	public void setEvent(Event currentEvent) {
		this.event = currentEvent;
	}

	public String getLikeArea() {
		return likeArea;
	}

	public void setLikeArea(String likeArea) {
		this.likeArea = likeArea;
	}

	@ManyToOne
	@JoinColumn(name = "GROUP_ID")
	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public String getWork() {
		return work;
	}

	public void setWork(String work) {
		this.work = work;
	}

}

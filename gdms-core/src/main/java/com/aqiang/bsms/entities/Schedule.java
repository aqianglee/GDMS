package com.aqiang.bsms.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * 进度表
 * 
 * @author aqiang
 */
@Entity
@Table(name = "SCJEDULES")
public class Schedule implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private College college;
	private Specialty specialty;
	private Teacher teacher;
	private String title;
	private Student student;
	private Group group;
	private File file;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

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

	@ManyToOne
	@JoinColumn(name = "TEACHER_ID")
	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@OneToOne
	@JoinColumn(name = "STUDENT_ID", unique = true)
	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	@OneToOne
	@JoinColumn(name = "GROUP_ID", unique = true)
	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	@OneToOne
	@JoinColumn(name = "FILE_ID", unique = true)
	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

}

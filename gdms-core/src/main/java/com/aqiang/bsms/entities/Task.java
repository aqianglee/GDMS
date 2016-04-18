package com.aqiang.bsms.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * 任务书实体
 * 
 * @author Administrator
 */

@Entity
@Table(name = "TASKS")
public class Task implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Student student;
	private Specialty specialty;

	private Teacher teacher;
	// 工作单位
	private String company;
	// 论文题目
	private Subject subject;
	private String dissertationMainContent;
	private String mainTask;
	private String teacherComment;
	private String collegeComment;
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
	@JoinColumn(name = "STUDENT_ID")
	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
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

	@Column(length = 32)
	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	@ManyToOne
	@JoinColumn(name = "SUBJECT_ID")
	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	@Column(length = 4096)
	public String getDissertationMainContent() {
		return dissertationMainContent;
	}

	public void setDissertationMainContent(String dissertationMainContent) {
		this.dissertationMainContent = dissertationMainContent;
	}

	@Column(length = 4096)
	public String getMainTask() {
		return mainTask;
	}

	public void setMainTask(String mainTask) {
		this.mainTask = mainTask;
	}

	@Column(length = 2048)
	public String getTeacherComment() {
		return teacherComment;
	}

	public void setTeacherComment(String teacherComment) {
		this.teacherComment = teacherComment;
	}

	@Column(length = 2048)
	public String getCollegeComment() {
		return collegeComment;
	}

	public void setCollegeComment(String collegeComment) {
		this.collegeComment = collegeComment;
	}

	@OneToOne
	@JoinColumn(name = "File_ID", unique = true)
	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

}

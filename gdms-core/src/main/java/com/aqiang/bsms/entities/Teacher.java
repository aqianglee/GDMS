package com.aqiang.bsms.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 教师实体
 * 
 * @author aqiang
 */
@Entity
@Table(name = "TEACHERS")
public class Teacher extends User {
	private static final long serialVersionUID = 1L;
	private College college;
	// 姓名
	private String job;
	// 研究领域
	private String researchArea;

	@ManyToOne
	@JoinColumn(name = "COLLEGE_ID")
	public College getCollege() {
		return college;
	}

	public void setCollege(College college) {
		this.college = college;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	@Column(length = 50)
	public String getResearchArea() {
		return researchArea;
	}

	public void setResearchArea(String researchArea) {
		this.researchArea = researchArea;
	}

}

package com.aqiang.bsms.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "events")
public class Event implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;
	private Boolean active;
	private String workFlowStatus;
	private College college;
	private String year;
	private Date beginDate;
	private Date endDate;
	private String description;
	private File selectSubjectGuide;
	private File selectSubjectSummary;

	@Id
	@GeneratedValue
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public String getWorkFlowStatus() {
		return workFlowStatus;
	}

	public void setWorkFlowStatus(String workFlowStatus) {
		this.workFlowStatus = workFlowStatus;
	}

	@Column(length = 4)
	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	@ManyToOne
	@JoinColumn(name = "COLLEGE_ID")
	public College getCollege() {
		return college;
	}

	public void setCollege(College college) {
		this.college = college;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Column(length = 400)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@OneToOne
	@JoinColumn(name = "SELECT_SUBJECT_GUIDE")
	public File getSelectSubjectGuide() {
		return selectSubjectGuide;
	}

	public void setSelectSubjectGuide(File selectSubjectGuide) {
		this.selectSubjectGuide = selectSubjectGuide;
	}

	@OneToOne
	@JoinColumn(name = "SELECT_SUBJECT_SUMMARY")
	public File getSelectSubjectSummary() {
		return selectSubjectSummary;
	}

	public void setSelectSubjectSummary(File selectSubjectSummary) {
		this.selectSubjectSummary = selectSubjectSummary;
	}

}

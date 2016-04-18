package com.aqiang.bsms.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * 院系实体
 * 
 * @author Administrator
 */
@Entity
@Table(name = "COLLEGES")
public class College implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String collegeName;
	private String simpleName;
	private String number;
	private String description;
	// 学院管理员（教学干事）
	private CollegeManager collegeManager;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(length = 24)
	public String getCollegeName() {
		return collegeName;
	}

	public void setCollegeName(String collegeName) {
		this.collegeName = collegeName;
	}

	public String getSimpleName() {
		return simpleName;
	}

	public void setSimpleName(String simpleName) {
		this.simpleName = simpleName;
	}

	@Column(length = 10)
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	@Column(length = 1024)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@OneToOne(mappedBy = "college")
	public CollegeManager getCollegeManager() {
		return collegeManager;
	}

	public void setCollegeManager(CollegeManager collegeManager) {
		this.collegeManager = collegeManager;
	}

}

package com.aqiang.bsms.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 专业班级实体
 * 
 * @author Administrator
 * 
 */
@Entity
@Table(name = "SPECIALTY")
public class Specialty implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String name;
	private String num;
	private College college;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(length = 50)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(length = 18)
	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	@ManyToOne
	@JoinColumn(name = "COLLEGE_ID")
	public College getCollege() {
		return college;
	}

	public void setCollege(College college) {
		this.college = college;
	}

}

package com.aqiang.bsms.entities;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * 院系管理员（教学干事）
 * 
 * @author aqiang
 */
@Entity
@Table(name = "COLLEGE_MANAGERS")
public class CollegeManager extends User {
	private static final long serialVersionUID = 1L;
	private College college;

	// 姓名
	/**
	 * 配置一对一关联关系
	 */
	@JoinColumn(name = "COLLEGE_ID", unique = true)
	@OneToOne
	public College getCollege() {
		return college;
	}

	public void setCollege(College college) {
		this.college = college;
	}

}

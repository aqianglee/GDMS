package com.aqiang.bsms.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * 角色实体
 * 
 * @author Administrator
 * 
 */

@Entity
@Table(name = "STATES")
public class State implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;
	private String stateId;
	private String name;
	private List<Authority> authorities = new ArrayList<Authority>();

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getStateId() {
		return stateId;
	}

	public void setStateId(String stateId) {
		this.stateId = stateId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@ManyToMany
	@JoinTable(name = "STATES_AUTHORITIES", joinColumns = { @JoinColumn(name = "STATE_ID", referencedColumnName = "ID") }, inverseJoinColumns = { @JoinColumn(name = "AUTHORTY_ID", referencedColumnName = "ID") })
	public List<Authority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(List<Authority> authorities) {
		this.authorities = authorities;
	}

}

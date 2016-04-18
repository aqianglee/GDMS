package com.aqiang.bsms.entities;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 系统管理员实体
 * 
 * @author aqiang
 */
@Entity
@Table(name = "SYSTEM_MANAGERS")
public class SystemManager extends User {
	private static final long serialVersionUID = 1L;

}

package com.aqiang.bsms.dao.impl;

import org.springframework.stereotype.Repository;

import com.aqiang.bsms.dao.SystemManagerDao;
import com.aqiang.bsms.entities.SystemManager;

@Repository("systemManagerDao")
public class SystemManagerDaoImpl extends BaseDaoImpl<SystemManager> implements
		SystemManagerDao {

}

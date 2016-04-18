package com.aqiang.bsms.dao.impl;

import org.springframework.stereotype.Repository;

import com.aqiang.bsms.dao.DissertationDao;
import com.aqiang.bsms.entities.Dissertation;

@Repository("dissertationDao")
public class DissertationDaoImpl extends BaseDaoImpl<Dissertation> implements
		DissertationDao {
}

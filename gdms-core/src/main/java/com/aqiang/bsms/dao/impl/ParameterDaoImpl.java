package com.aqiang.bsms.dao.impl;

import org.springframework.stereotype.Repository;

import com.aqiang.bsms.dao.ParameterDao;
import com.aqiang.bsms.entities.Parameter;

@Repository("parameterDao")
public class ParameterDaoImpl extends BaseDaoImpl<Parameter> implements
		ParameterDao {

}

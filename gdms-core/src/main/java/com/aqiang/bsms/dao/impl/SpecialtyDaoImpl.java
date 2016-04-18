package com.aqiang.bsms.dao.impl;

import org.springframework.stereotype.Repository;

import com.aqiang.bsms.dao.SpecialtyDao;
import com.aqiang.bsms.entities.Specialty;

@Repository("specialtyDao")
public class SpecialtyDaoImpl extends BaseDaoImpl<Specialty> implements
		SpecialtyDao {

}

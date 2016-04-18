package com.aqiang.bsms.service.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.aqiang.bsms.dao.BaseDao;
import com.aqiang.bsms.entities.College;
import com.aqiang.bsms.entities.Specialty;
import com.aqiang.bsms.service.SpecialtyService;

@Service("specialtyService")
@Transactional
public class SpecialtyServiceImpl extends BaseServiceImpl<Specialty> implements
		SpecialtyService {

	@Resource(name = "specialtyDao")
	@Override
	public void setDao(BaseDao<Specialty> dao) {
		this.dao = dao;
	}

	public List<Specialty> getSpecialtiesByCollege(College college) {
		String jpql = "From Specialty s where s.college = ?";
		List<Specialty> specialties = this.findEntityByJpql(jpql, college);

		return specialties;
	}

	public void addNewSpecialty(Specialty model) {
		this.saveEntitiy(model);
	}
}

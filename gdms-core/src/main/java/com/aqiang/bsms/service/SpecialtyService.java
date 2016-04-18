package com.aqiang.bsms.service;

import java.util.List;

import com.aqiang.bsms.entities.College;
import com.aqiang.bsms.entities.Specialty;

public interface SpecialtyService extends BaseService<Specialty> {

	public void addNewSpecialty(Specialty model);

	public List<Specialty> getSpecialtiesByCollege(College college);

}

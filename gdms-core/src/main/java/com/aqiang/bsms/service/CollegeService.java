package com.aqiang.bsms.service;

import java.util.List;

import com.aqiang.bsms.entities.College;
import com.aqiang.bsms.entities.CollegeManager;
import com.aqiang.bsms.exception.UsernameHasUsedException;

public interface CollegeService extends BaseService<College> {
	public List<College> getAllColleges();

	public College createCollege(College college);

	public College getCollegeByName(String name);

	public void changeCollegeManager(College college, CollegeManager collegeManager) throws UsernameHasUsedException;

}

package com.aqiang.bsms.service;

import com.aqiang.bsms.entities.College;
import com.aqiang.bsms.entities.CollegeManager;
import com.aqiang.bsms.exception.UsernameHasUsedException;

public interface CollegeManagerService extends BaseService<CollegeManager> {

	public void createNewCollegeManager(CollegeManager collegeManager) throws UsernameHasUsedException;

	public CollegeManager getCollegeManager(College college);

}

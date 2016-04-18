package com.aqiang.bsms.service.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aqiang.bsms.dao.BaseDao;
import com.aqiang.bsms.entities.College;
import com.aqiang.bsms.entities.CollegeManager;
import com.aqiang.bsms.entities.User;
import com.aqiang.bsms.exception.UsernameHasUsedException;
import com.aqiang.bsms.service.AuthorityService;
import com.aqiang.bsms.service.CollegeManagerService;
import com.aqiang.bsms.service.StateService;
import com.aqiang.bsms.service.UserService;
import com.aqiang.bsms.utils.Md5Util;

@Service("collegeManagerService")
@Transactional
public class CollegeManagerServiceImpl extends BaseServiceImpl<CollegeManager> implements CollegeManagerService {

	@Autowired
	private StateService stateService;
	@Autowired
	private AuthorityService authorityService;
	@Autowired
	private UserService userService;

	@Resource(name = "collegeManagerDao")
	@Override
	public void setDao(BaseDao<CollegeManager> dao) {
		this.dao = dao;
	}

	public List<CollegeManager> getAllCollegeManagers() {
		String jpql = "From CollegeManager";
		List<CollegeManager> collegeManagers = this.findEntityByJpql(jpql);
		return collegeManagers;
	}

	public void createNewCollegeManager(CollegeManager collegeManager) throws UsernameHasUsedException {
		User user = userService.getUserByUsername(collegeManager.getUsername());
		if (user != null) {
			throw new UsernameHasUsedException("The username has used");
		}
		collegeManager.setPassword(Md5Util.md5("123456"));
		this.saveEntitiy(collegeManager);
	}

	public CollegeManager getCollegeManager(College college) {
		if (college == null) {
			return null;
		}
		return findEntity(college.getId());
	}
}

package com.aqiang.bsms.service.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aqiang.bsms.dao.BaseDao;
import com.aqiang.bsms.entities.College;
import com.aqiang.bsms.entities.CollegeManager;
import com.aqiang.bsms.exception.UsernameHasUsedException;
import com.aqiang.bsms.service.AuthorityService;
import com.aqiang.bsms.service.CollegeManagerService;
import com.aqiang.bsms.service.CollegeService;
import com.aqiang.bsms.service.StateService;

@Service("collegeService")
@Transactional
public class CollegeServiceImpl extends BaseServiceImpl<College> implements CollegeService {

	@Autowired
	private CollegeManagerService collegeManagerService;
	@Autowired
	private StateService stateService;
	@Autowired
	private AuthorityService authorityService;
	private static final Logger logger = LoggerFactory.getLogger(CollegeServiceImpl.class);

	@Resource(name = "collegeDao")
	@Override
	public void setDao(BaseDao<College> dao) {
		this.dao = dao;
	}

	public List<College> getAllColleges() {
		String jpql = "From College";
		List<College> colleges = this.findEntityByJpql(jpql);
		return colleges;
	}

	public College createCollege(College college) {
		College collegeByName = getCollegeByName(college.getCollegeName());
		if (collegeByName == null) {
			saveEntitiy(college);
			return college;
		}
		return collegeByName;
	}

	public College getCollegeByName(String name) {
		String jpql = "From College c where c.collegeName = ?";
		List<College> colleges = findEntityByJpql(jpql, name);
		return colleges.isEmpty() ? null : colleges.get(0);
	}

	@Override
	public void changeCollegeManager(College college, CollegeManager collegeManager) throws UsernameHasUsedException {
		College college2 = findEntity(college.getId());
		if (college2.getCollegeManager() != null) {
			logger.info("has college manager");
			collegeManagerService.deleteEntity(college2.getCollegeManager().getId());
		}
		collegeManagerService.createNewCollegeManager(collegeManager);
		college2.setCollegeManager(collegeManager);
	}

}

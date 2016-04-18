package com.aqiang.bsms.service.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.aqiang.bsms.dao.BaseDao;
import com.aqiang.bsms.entities.College;
import com.aqiang.bsms.entities.TeachAndResearchOffice;
import com.aqiang.bsms.service.TeachAndResearchOfficeService;

@Service
@Transactional
public class TeachAndResearchOfficeServiceImpl extends
		BaseServiceImpl<TeachAndResearchOffice> implements
		TeachAndResearchOfficeService {
	@Resource(name = "teachAndResearchOfficeDao")
	@Override
	public void setDao(BaseDao<TeachAndResearchOffice> dao) {
		this.dao = dao;
	}

	@Override
	public List<TeachAndResearchOffice> getTeachAndResearchOfficeByCollege(
			College college) {
		return findEntityByJpql(
				"From TeachAndResearchOffice t where t.college = ?", college);
	}

}

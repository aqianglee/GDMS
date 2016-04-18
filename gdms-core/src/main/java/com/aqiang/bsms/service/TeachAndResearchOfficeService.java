package com.aqiang.bsms.service;

import java.util.List;

import com.aqiang.bsms.entities.College;
import com.aqiang.bsms.entities.TeachAndResearchOffice;

public interface TeachAndResearchOfficeService extends
		BaseService<TeachAndResearchOffice> {

	List<TeachAndResearchOffice> getTeachAndResearchOfficeByCollege(
			College college);

}

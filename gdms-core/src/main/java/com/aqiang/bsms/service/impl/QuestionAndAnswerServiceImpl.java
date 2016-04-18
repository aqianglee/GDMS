package com.aqiang.bsms.service.impl;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.aqiang.bsms.dao.BaseDao;
import com.aqiang.bsms.entities.QuestionAndAnswer;
import com.aqiang.bsms.service.QuestionAndAnswerService;

@Service("questionAndAnswerService")
@Transactional
public class QuestionAndAnswerServiceImpl extends
		BaseServiceImpl<QuestionAndAnswer> implements QuestionAndAnswerService {

	@Resource(name = "questionAndAnswerDao")
	@Override
	public void setDao(BaseDao<QuestionAndAnswer> dao) {
		this.dao = dao;
	}

}

package com.aqiang.bsms.dao.impl;

import org.springframework.stereotype.Repository;

import com.aqiang.bsms.dao.QuestionAndAnswerDao;
import com.aqiang.bsms.entities.QuestionAndAnswer;

@Repository("questionAndAnswerDao")
public class QuestionAndAnswerDaoImpl extends BaseDaoImpl<QuestionAndAnswer>
		implements QuestionAndAnswerDao {

}

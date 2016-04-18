package com.aqiang.bsms.service.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.aqiang.bsms.dao.BaseDao;
import com.aqiang.bsms.entities.Message;
import com.aqiang.bsms.entities.User;
import com.aqiang.bsms.service.MessageService;

@Service
@Transactional
public class MessageServiceImpl extends BaseServiceImpl<Message> implements
		MessageService {

	@Resource(name = "messageDao")
	@Override
	public void setDao(BaseDao<Message> dao) {
		this.dao = dao;
	}

	@Override
	public List<Message> getMyMessage(User user) {
		String jpql = "From Message m where m.to = ?";
		List<Message> messages = findEntityByJpql(jpql, user);
		return messages;
	}

}

package com.aqiang.bsms.service.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aqiang.bsms.dao.BaseDao;
import com.aqiang.bsms.dao.MessageDao;
import com.aqiang.bsms.dao.QuestionAndAnswerDao;
import com.aqiang.bsms.entities.Message;
import com.aqiang.bsms.entities.SystemManager;
import com.aqiang.bsms.entities.User;
import com.aqiang.bsms.exception.UsernameHasUsedException;
import com.aqiang.bsms.service.AuthorityService;
import com.aqiang.bsms.service.StateService;
import com.aqiang.bsms.service.SystemManagerService;
import com.aqiang.bsms.service.UserService;
import com.aqiang.bsms.utils.Md5Util;

@Service("systemManagerService")
@Transactional
public class SystemManagerServiceImpl extends BaseServiceImpl<SystemManager> implements SystemManagerService {
	private static final Logger logger = LoggerFactory.getLogger(SystemManagerServiceImpl.class);
	@Autowired
	private MessageDao messageDao;
	@Autowired
	private QuestionAndAnswerDao questionAndAnswerDao;
	@Autowired
	private StateService stateService;
	@Autowired
	private AuthorityService authorityService;
	@Autowired
	private UserService userService;

	@Override
	@Resource(name = "systemManagerDao")
	public void setDao(BaseDao<SystemManager> dao) {
		this.dao = dao;
	}

	public List<Message> getMyMessages(SystemManager systemManager) {
		String jpql = "From Message m where m.to.id = ? and hasRead = ?";
		List<Message> messages = messageDao.findEntityByJpql(jpql, systemManager.getId(), false);
		for (Message m : messages) {
			m.getFrom().getId();
			m.getTo().getId();
		}
		return messages;
	}

	@Override
	public boolean hasSystemManager() {
		String jpql = "From SystemManager";
		return findEntityByJpql(jpql).isEmpty();
	}

	@Override
	public void createDefaultSystemManager() {
		try {
			createSystemManager("admin", "123456");
		} catch (UsernameHasUsedException e) {
			logger.error(e.getMessage());
		}
	}

	@Override
	public SystemManager createSystemManager(String username, String password) throws UsernameHasUsedException {
		User user = userService.getUserByUsername(username);
		if (user != null) {
			throw new UsernameHasUsedException("The username has used");
		}
		SystemManager systemManager = new SystemManager();
		systemManager.setUsername(username);
		systemManager.setPassword(Md5Util.md5(password));
		saveEntitiy(systemManager);
		return systemManager;
	}

}

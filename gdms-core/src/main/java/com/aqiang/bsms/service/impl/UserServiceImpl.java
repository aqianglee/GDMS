package com.aqiang.bsms.service.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.aqiang.bsms.dao.BaseDao;
import com.aqiang.bsms.entities.QuestionAndAnswer;
import com.aqiang.bsms.entities.User;
import com.aqiang.bsms.service.UserService;
import com.aqiang.bsms.utils.Md5Util;

@Service
@Transactional
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {

	@Resource(name = "userDao")
	@Override
	public void setDao(BaseDao<User> dao) {
		this.dao = dao;
	}

	@Override
	public boolean modefyPassword(User user, String password, String newPassword) {
		if (Md5Util.md5(password).equals(user.getPassword())) {
			user.setPassword(Md5Util.md5(newPassword));
			margeEntity(user);
			return true;
		}
		return false;
	}

	@Override
	public void setQuestionAndAnswer(User user, List<QuestionAndAnswer> questionAndAnswers) {

	}

	@Override
	public User getUserByUsernameAndPassword(String username, String password) {
		String jpql = "From User s where s.username = ? and s.password = ?";
		List<User> users = findEntityByJpql(jpql, username, Md5Util.md5(password));
		return users.isEmpty() ? null : users.get(0);
	}

	@Override
	public User getUserByUsername(String username) {
		String jpql = "From User s where s.username = ?";
		List<User> users = findEntityByJpql(jpql, username);
		return users.isEmpty() ? null : users.get(0);
	}

}

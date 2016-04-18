package com.aqiang.bsms.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aqiang.bsms.dao.UserDao;
import com.aqiang.bsms.entities.User;
import com.aqiang.bsms.service.LoginService;
import com.aqiang.bsms.service.SystemManagerService;
import com.aqiang.bsms.service.UserService;
import com.aqiang.bsms.utils.Md5Util;

@Service("loginService")
@Transactional
public class LoginServiceImpl implements LoginService {

	@Autowired
	private UserService userService;
	@Autowired
	private SystemManagerService systemManagerService;

	@Override
	public User validateUser(User user) {
		if (systemManagerService.hasSystemManager()) {
			systemManagerService.createDefaultSystemManager();
		}
		return userService.getUserByUsernameAndPassword(user.getUsername(),
				user.getPassword());
	}
}

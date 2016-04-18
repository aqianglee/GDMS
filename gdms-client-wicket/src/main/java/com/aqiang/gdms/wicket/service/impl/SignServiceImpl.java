package com.aqiang.gdms.wicket.service.impl;

import org.apache.wicket.Session;
import org.springframework.stereotype.Service;

import com.aqiang.bsms.entities.College;
import com.aqiang.bsms.entities.User;
import com.aqiang.gdms.wicket.MySession;
import com.aqiang.gdms.wicket.service.SignService;

@Service
public class SignServiceImpl implements SignService {

	@Override
	public User getSignUser(Session session) {
		MySession mySession = (MySession) session;
		User user = mySession.getUser();
		if(user == null) {
			throw new RuntimeException();
		}
		return user;
	}

	@Override
	public College getCollege(Session session) {
		MySession mySession = (MySession) session;
		return mySession.getCollege();
	}

	@Override
	public String getUserType(Session session) {
		MySession mySession = (MySession) session;
		return mySession.getUserType();
	}

}

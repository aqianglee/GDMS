package com.aqiang.gdms.wicket.service;

import org.apache.wicket.Session;

import com.aqiang.bsms.entities.College;
import com.aqiang.bsms.entities.User;

public interface SignService {

	User getSignUser(Session session);

	College getCollege(Session session);

	String getUserType(Session session);

}

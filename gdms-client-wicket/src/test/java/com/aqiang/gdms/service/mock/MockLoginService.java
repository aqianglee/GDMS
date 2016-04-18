package com.aqiang.gdms.service.mock;

import com.aqiang.bsms.entities.User;
import com.aqiang.bsms.service.LoginService;

public class MockLoginService implements LoginService {

	@Override
	public User validateUser(User model) {
		return null;
	}

}

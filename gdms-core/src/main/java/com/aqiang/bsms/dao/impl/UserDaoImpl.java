package com.aqiang.bsms.dao.impl;

import org.springframework.stereotype.Repository;

import com.aqiang.bsms.dao.UserDao;
import com.aqiang.bsms.entities.User;

@Repository("userDao")
public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao {

}

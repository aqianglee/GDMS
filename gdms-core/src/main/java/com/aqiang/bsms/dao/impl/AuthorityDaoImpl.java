package com.aqiang.bsms.dao.impl;

import org.springframework.stereotype.Repository;

import com.aqiang.bsms.dao.AuthorityDao;
import com.aqiang.bsms.entities.Authority;

@Repository("authorityDao")
public class AuthorityDaoImpl extends BaseDaoImpl<Authority> implements
		AuthorityDao {

}

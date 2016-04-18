package com.aqiang.bsms.service.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.aqiang.bsms.dao.BaseDao;
import com.aqiang.bsms.entities.Authority;
import com.aqiang.bsms.service.AuthorityService;

@Service("authorityService")
@Transactional
public class AuthorityServiceImpl extends BaseServiceImpl<Authority> implements
		AuthorityService {

	@Resource(name = "authorityDao")
	@Override
	public void setDao(BaseDao<Authority> dao) {
		this.dao = dao;
	}

	public List<Authority> getAllAuthorties() {
		String jpql = "From Authority";
		List<Authority> authorities = this.findEntityByJpql(jpql);
		return authorities;
	}

	public Authority createAuthority(String name, String url) {
		Authority authority = getAuthorityByName(name);
		if (authority != null) {
			authority.setUrl(url);
			return authority;
		}
		authority = new Authority();
		authority.setName(name);
		authority.setUrl(url);
		this.saveEntitiy(authority);
		return authority;
	}

	public Authority getAuthorityByName(String name) {
		String jpql = "From Authority where name = ?";
		List<Authority> authorities = this.findEntityByJpql(jpql, name);
		return authorities.isEmpty() ? null : authorities.get(0);
	}

}

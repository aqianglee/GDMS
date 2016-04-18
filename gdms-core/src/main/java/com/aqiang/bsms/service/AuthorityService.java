package com.aqiang.bsms.service;

import java.util.List;

import com.aqiang.bsms.entities.Authority;

public interface AuthorityService extends BaseService<Authority> {

	public List<Authority> getAllAuthorties();

	public Authority createAuthority(String name, String url);

	public Authority getAuthorityByName(String name);

}

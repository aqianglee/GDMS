package com.aqiang.bsms.service;

import java.util.List;

import com.aqiang.bsms.entities.Authority;
import com.aqiang.bsms.entities.State;

public interface StateService extends BaseService<State> {

	public List<State> getAllRoses();

	public State getRoseWithAuthority(Integer id);

	public void addAuthority(Integer id, Integer aid);

	public void deleteAuthority(Integer id, Integer aid);

	public State createState(String stateId, String name,
			List<Authority> authorities);

	public State getStateByStateId(String stateId);

	public String getStateId(String userType, String status);

}

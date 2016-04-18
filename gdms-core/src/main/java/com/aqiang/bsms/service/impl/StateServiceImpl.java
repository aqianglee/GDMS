package com.aqiang.bsms.service.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aqiang.bsms.dao.AuthorityDao;
import com.aqiang.bsms.dao.BaseDao;
import com.aqiang.bsms.entities.Authority;
import com.aqiang.bsms.entities.State;
import com.aqiang.bsms.service.StateService;

@Service("stateService")
@Transactional
public class StateServiceImpl extends BaseServiceImpl<State> implements
		StateService {

	@Resource(name = "stateDao")
	@Override
	public void setDao(BaseDao<State> dao) {
		this.dao = dao;
	}

	@Autowired
	AuthorityDao authorityDao;

	public List<State> getAllRoses() {
		String jpql = "From State";
		List<State> roses = this.findEntityByJpql(jpql);
		return roses;
	}

	public State getRoseWithAuthority(Integer id) {
		State rose = this.findEntity(id);
		return rose;
	}

	public void addAuthority(Integer id, Integer aid) {
		State rose = findEntity(id);
		Authority authority = authorityDao.findEntity(aid);
		rose.getAuthorities().add(authority);
	}

	public void deleteAuthority(Integer id, Integer aid) {
		State rose = findEntity(id);
		List<Authority> authorities = rose.getAuthorities();
		Authority authority = authorityDao.findEntity(aid);
		authorities.remove(authority);
	}

	public State createState(String stateId, String name,
			List<Authority> authorities) {
		State stateByStateId = getStateByStateId(stateId);
		if (stateByStateId != null) {
			setStateProperties(stateId, name, authorities, stateByStateId);
			return stateByStateId;
		}
		stateByStateId = new State();
		setStateProperties(stateId, name, authorities, stateByStateId);
		saveEntitiy(stateByStateId);
		return stateByStateId;
	}

	private void setStateProperties(String stateId, String name,
			List<Authority> authorities, State stateByStateId) {
		stateByStateId.setStateId(stateId);
		stateByStateId.setName(name);
		stateByStateId.setAuthorities(authorities);
	}

	public State getStateByStateId(String stateId) {
		String jpql = "From State s where s.stateId = ?";
		List<State> states = findEntityByJpql(jpql, stateId);
		if (!states.isEmpty()) {
			State state = states.get(0);
			state.getAuthorities().size();
			return state;
		}
		return null;
	}

	@Override
	public String getStateId(String userType, String status) {
		return String.format("%s_%s", status, userType);
	}

}

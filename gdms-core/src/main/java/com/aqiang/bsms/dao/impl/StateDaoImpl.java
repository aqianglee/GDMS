package com.aqiang.bsms.dao.impl;

import org.springframework.stereotype.Repository;

import com.aqiang.bsms.dao.StateDao;
import com.aqiang.bsms.entities.State;

@Repository("stateDao")
public class StateDaoImpl extends BaseDaoImpl<State> implements StateDao {

}

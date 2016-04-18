package com.aqiang.bsms.dao.impl;

import org.springframework.stereotype.Repository;

import com.aqiang.bsms.dao.EventDao;
import com.aqiang.bsms.entities.Event;

@Repository("eventDao")
public class EventDaoImpl extends BaseDaoImpl<Event> implements EventDao {

}

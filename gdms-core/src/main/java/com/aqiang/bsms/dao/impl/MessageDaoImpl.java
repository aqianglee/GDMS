package com.aqiang.bsms.dao.impl;

import org.springframework.stereotype.Repository;

import com.aqiang.bsms.dao.MessageDao;
import com.aqiang.bsms.entities.Message;

@Repository("messageDao")
public class MessageDaoImpl extends BaseDaoImpl<Message> implements MessageDao {

}

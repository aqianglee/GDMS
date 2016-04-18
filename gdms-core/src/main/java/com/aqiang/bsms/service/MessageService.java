package com.aqiang.bsms.service;

import java.util.List;

import com.aqiang.bsms.entities.Message;
import com.aqiang.bsms.entities.User;

public interface MessageService extends BaseService<Message> {
	public List<Message> getMyMessage(User user);

}

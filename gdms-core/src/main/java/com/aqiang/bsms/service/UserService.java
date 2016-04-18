package com.aqiang.bsms.service;

import java.util.List;

import com.aqiang.bsms.entities.QuestionAndAnswer;
import com.aqiang.bsms.entities.User;

public interface UserService extends BaseService<User> {

	boolean modefyPassword(User user, String password, String newPassword);

	public void setQuestionAndAnswer(User user,
			List<QuestionAndAnswer> questionAndAnswers);

	public User getUserByUsernameAndPassword(String username, String password);

	public User getUserByUsername(String username);

}

package com.aqiang.bsms.service.impl;

import java.util.List;

import org.junit.Test;

import com.aqiang.bsms.entities.Message;
import com.aqiang.bsms.entities.Student;
import com.aqiang.gdms.BaseTest;

public class MessageServiceImplTest extends BaseTest {

	@Test
	public void testGetMyMessage() {
		Student student = new Student();
		student.setCompellation("李志强");
		studentService.saveEntitiy(student);
		Message message = new Message();
		message.setContext("l love you");
		message.setTo(student);
		message.setHasRead(false);
		messageService.saveEntitiy(message);

		List<Message> myMessages = messageService.getMyMessage(student);
		isTrue(1 == myMessages.size());
	}

}

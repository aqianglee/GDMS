package com.aqiang.bsms.service.impl;

import org.junit.Test;

import com.aqiang.gdms.BaseTest;

public class FileServiceImplTest3 extends BaseTest {

	@Test
	public void test() {
		isEq(fileService.getPostfixName("abc"), "");
		isEq(fileService.getPostfixName("abc.doc"), "doc");
		isEq(fileService.getPostfixName("abc.java.txt"), "txt");
		isEq(fileService.getPostfixName("HelloWorld.java"), "java");
	}

}

package com.aqiang.bsms.service.impl;

import java.util.Date;

import org.junit.Test;

import com.aqiang.gdms.BaseTest;

public class GenerateFileImplTest5 extends BaseTest {

	@Test
	public void testWordToPDF() {
		long time = new Date().getTime();
		generateFile.wordToPDF("D://abc.doc", "d://123.pdf");
		long time2 = new Date().getTime();
		System.out.println(time2 - time);
	}

}

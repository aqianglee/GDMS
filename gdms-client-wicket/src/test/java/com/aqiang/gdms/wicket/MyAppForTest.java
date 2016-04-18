package com.aqiang.gdms.wicket;

import org.apache.wicket.Page;
import org.apache.wicket.Session;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;

import com.aqiang.gdms.wicket.page.LoginPage;
import com.ttdev.wicketpagetest.MockableSpringBeanInjector;

public class MyAppForTest extends WebApplication{
	@Override
	public Class<? extends Page> getHomePage() {
		return LoginPage.class;
	}

	@Override
	protected void init() {
		super.init();
		MockableSpringBeanInjector.installInjector(this);
		getMarkupSettings().setDefaultMarkupEncoding("utf-8");
	}
	
	@Override
	public Session newSession(Request request, Response response) {
		return new MySession(request);
	}
}

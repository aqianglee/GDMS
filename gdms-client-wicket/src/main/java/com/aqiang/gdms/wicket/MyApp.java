package com.aqiang.gdms.wicket;

import org.apache.wicket.Page;
import org.apache.wicket.Session;
import org.apache.wicket.markup.html.IPackageResourceGuard;
import org.apache.wicket.markup.html.SecurePackageResourceGuard;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;

import com.aqiang.gdms.wicket.page.LoginPage;

public class MyApp extends WebApplication {

	@Override
	public Class<? extends Page> getHomePage() {
		return LoginPage.class;
	}

	@Override
	protected void init() {
		super.init();
		enableServerFontLoadable();
		getComponentInstantiationListeners().add(
				new SpringComponentInjector(this));
		getMarkupSettings().setDefaultMarkupEncoding("utf-8");
	}

	@Override
	public Session newSession(Request request, Response response) {
		return new MySession(request);
	}

	private void enableServerFontLoadable() {
		IPackageResourceGuard guard = getResourceSettings()
				.getPackageResourceGuard();
		if (guard instanceof SecurePackageResourceGuard) {
			SecurePackageResourceGuard secureGuard = (SecurePackageResourceGuard) guard;
			secureGuard.addPattern("+**.ttf");
			secureGuard.addPattern("+**.eot");
			secureGuard.addPattern("+**.svg");
			secureGuard.addPattern("+**.woff");
			secureGuard.addPattern("+**.GIF");
			secureGuard.addPattern("+**.gif");
		}
	}

}

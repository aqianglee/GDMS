package com.aqiang.gdms.wicket.page;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aqiang.bsms.entities.College;
import com.aqiang.bsms.entities.User;
import com.aqiang.gdms.wicket.componet.ConfirmLink;
import com.aqiang.gdms.wicket.service.SignService;

public class BorderPage extends WebPage {
	private static final long serialVersionUID = 1L;
	protected Logger LOGGER = LoggerFactory.getLogger(BorderPage.class);
	
	protected User signUser;
	@SpringBean
	private SignService signService;
	protected College college;
	protected String userType;

	public BorderPage() {
		try {
			signUser = signService.getSignUser(getSession());
			college = signService.getCollege(getSession());
			userType = signService.getUserType(getSession());
			LOGGER.info("Login UserType: {}", userType);
		} catch (Exception e) {
			setResponsePage(LoginPage.class);
		}

		AjaxLink<Void> messageLink = new AjaxLink<Void>("showMessage") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {

			}
		};
		messageLink.add(new Label("messageCount"));
		add(messageLink);
		
		ConfirmLink<Void> logout = new ConfirmLink<Void>("logout") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				getSession().clear();
				setResponsePage(LoginPage.class);
			}
			
			@Override
			protected String getMessageKey() {
				return "confirm.logout";
			}
		};
		add(logout);
	}

	protected void onPrev() {

	}

	protected Link<Void> createReturnLink() {
		return new Link<Void>("return") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				onPrev();
			}
		};
	}

}

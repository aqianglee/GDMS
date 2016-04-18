package com.aqiang.bsms.service.impl;

import java.beans.PropertyVetoException;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.Test;

import com.aqiang.gdms.BaseTest;

public class DBConnectTest extends BaseTest {

	@Test
	public void testDBConnect() throws SQLException, ClassNotFoundException,
			PropertyVetoException {
		isTrue(applicationContext.getBean(DataSource.class).getConnection() != null);
	}
}

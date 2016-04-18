package com.aqiang.bsms.service;

import com.aqiang.bsms.entities.SystemManager;
import com.aqiang.bsms.exception.UsernameHasUsedException;

public interface SystemManagerService extends BaseService<SystemManager> {

	public SystemManager createSystemManager(String username, String password) throws UsernameHasUsedException;

	public boolean hasSystemManager();

	public void createDefaultSystemManager();

}

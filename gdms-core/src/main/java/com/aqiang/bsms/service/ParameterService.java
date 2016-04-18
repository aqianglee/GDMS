package com.aqiang.bsms.service;

import java.util.Map;

import com.aqiang.bsms.entities.Parameter;

public interface ParameterService extends BaseService<Parameter> {

	public Parameter getParameterByName(String name);

	public Map<String, String> getParameters();

	public void updateParameters(Map<String, String> parameters);

}

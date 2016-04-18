package com.aqiang.bsms.service.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.aqiang.bsms.dao.BaseDao;
import com.aqiang.bsms.entities.Parameter;
import com.aqiang.bsms.service.ParameterService;

@Service("parameterService")
@Transactional
public class ParameterServiceImpl extends BaseServiceImpl<Parameter> implements ParameterService {

	@Resource(name = "parameterDao")
	@Override
	public void setDao(BaseDao<Parameter> dao) {
		this.dao = dao;
	}

	@Override
	public Parameter getParameterByName(String name) {
		List<Parameter> parameters = findEntityByJpql("From Parameter p where p.name = ?", name);
		if (!parameters.isEmpty()) {
			return parameters.get(0);
		}
		throw new RuntimeException(String.format("not find parameter with name %s", name));
	}

	@Override
	public Map<String, String> getParameters() {
		List<Parameter> parameters = findEntityByJpql("From Parameter");
		Map<String, String> parametersMap = new HashMap<String, String>();
		for (Parameter parameter : parameters) {
			parametersMap.put(parameter.getName(), parameter.getValue());
		}
		return parametersMap;
	}

	@Override
	public void updateParameters(Map<String, String> parameters) {
		Iterator<String> iterator = parameters.keySet().iterator();
		while (iterator.hasNext()) {
			String name = iterator.next();
			Parameter parameterByName;
			try {
				parameterByName = getParameterByName(name);
				parameterByName.setValue(parameters.get(name));
			} catch (Exception e) {
				Parameter parameter = new Parameter();
				parameter.setName(name);
				parameter.setValue(parameters.get(name));
				saveEntitiy(parameter);
			}
		}
	}
}

package com.aqiang.bsms.service;

import java.util.List;

import com.aqiang.bsms.entities.College;
import com.aqiang.bsms.entities.Event;

public interface EventService extends BaseService<Event> {

	public Event getCurrentEvent(College college);

	public Event getEventByYear(String year, College college);

	public boolean createNewEvent(Event event);

	public List<Event> getEvents(College college);

	public void changeToActive(Event event);

	public void changeWorkFlowStatus(Event event, String status);

	public void deleteSubjectsSelectGuideFile(Event event);

	public void genereteSelectsSubjectGuideFile(College college);

	public void updateSubjectsSelectGuideFile(College college);

	public void deleteSubjectsSelectSummaryFile(Event event);

	public void genereteSubjectsSelectSummaryFile(College college);

	public void updateSubjectsSelectSummaryFile(College college);
}

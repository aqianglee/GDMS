package com.aqiang.bsms.service;

import java.util.List;

import com.aqiang.bsms.entities.College;
import com.aqiang.bsms.entities.Event;
import com.aqiang.bsms.entities.Group;
import com.aqiang.bsms.entities.Subject;
import com.aqiang.bsms.entities.Task;

public interface GenerateFile {

	public void generateSubject(Subject subject, String fileName);

	public void generateSubjectSelectGuide(College college, Event currentEvent,
			List<Subject> subjects, String fileName);

	public void generateSubjectSelectSummary(College college,
			Event currentEvent, List<Group> groups, String fileName);

	public void generateTask(Task task, String string);

	public void wordToPDF(String wordFile, String pdfFile);

}

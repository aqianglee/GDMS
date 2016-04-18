package com.aqiang.bsms.service;

import java.io.OutputStream;

import com.aqiang.bsms.entities.College;
import com.aqiang.bsms.entities.Event;
import com.aqiang.bsms.entities.Subject;

public interface DownloadService {
	public void downloadSubject(Subject subject, OutputStream output);

	public void downloadSubjectsSelectFile(College college, Event currentEvent, OutputStream output);

	public void downloadSubjectsSelectSummary(College college, Event currentEvent, OutputStream output);

}

package com.aqiang.bsms.service.impl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aqiang.bsms.entities.College;
import com.aqiang.bsms.entities.Event;
import com.aqiang.bsms.entities.File;
import com.aqiang.bsms.entities.ParameterKey;
import com.aqiang.bsms.entities.Subject;
import com.aqiang.bsms.service.DownloadService;
import com.aqiang.bsms.service.EventService;
import com.aqiang.bsms.service.ParameterService;
import com.aqiang.bsms.service.SubjectService;

@Service
@Transactional
public class DownloadServiceImpl implements DownloadService {

	@Autowired
	private ParameterService parameterService;
	@Autowired
	private SubjectService subjectService;
	@Autowired
	private EventService eventService;
	private static final Logger logger = LoggerFactory.getLogger(DownloadServiceImpl.class);

	@Override
	public void downloadSubject(Subject subject, OutputStream output) {
		File file = subject.getFile();
		readFileToOutputStream(output, file);
	}

	@Override
	public void downloadSubjectsSelectFile(College college, Event currentEvent, OutputStream output) {
		String rootdir = parameterService.getParameterByName(ParameterKey.FILE_ROOT_DIR).getValue();
		List<Subject> subjects = subjectService.getAllSubjects(college, currentEvent);
		ZipOutputStream zos = new ZipOutputStream(output);
		try {
			eventService.updateSubjectsSelectGuideFile(college);
			File file = eventService.getCurrentEvent(college).getSelectSubjectGuide();
			FileInputStream in = new FileInputStream(new java.io.File(rootdir + file.getFilePath()));
			zip(zos, in, file.getFileName());
			for (Subject subject : subjects) {
				file = subject.getFile();
				in = new FileInputStream(new java.io.File(rootdir + file.getFilePath()));
				zip(zos, in, currentEvent.getYear() + "/" + file.getFileName());
			}
			zos.closeEntry();
			zos.flush();
			zos.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private void zip(ZipOutputStream zos, FileInputStream in, String entryName) throws IOException {
		ZipEntry attachment = new ZipEntry(entryName);
		zos.putNextEntry(attachment);
		byte[] buffer = new byte[in.available()];
		in.read(buffer);
		zos.write(buffer);
		in.close();
		zos.closeEntry();
	}

	@Override
	public void downloadSubjectsSelectSummary(College college, Event currentEvent, OutputStream output) {
		eventService.updateSubjectsSelectSummaryFile(college);
		File file = eventService.getCurrentEvent(college).getSelectSubjectSummary();
		readFileToOutputStream(output, file);
	}

	private void readFileToOutputStream(OutputStream output, File file) {
		try {
			String rootdir = parameterService.getParameterByName(ParameterKey.FILE_ROOT_DIR).getValue();
			logger.info("file path is {}", file.getFilePath());
			InputStream in = new FileInputStream(new java.io.File(rootdir + file.getFilePath()));
			byte[] buffer = new byte[in.available()];
			in.read(buffer);
			output.write(buffer);
			in.close();
			output.flush();
			output.close();
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}

}

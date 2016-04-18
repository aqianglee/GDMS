package com.aqiang.bsms.service.impl;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aqiang.bsms.entities.College;
import com.aqiang.bsms.entities.Event;
import com.aqiang.bsms.entities.Group;
import com.aqiang.bsms.entities.Student;
import com.aqiang.bsms.entities.Subject;
import com.aqiang.bsms.entities.SubjectSource;
import com.aqiang.bsms.entities.SubjectType;
import com.aqiang.bsms.entities.Task;
import com.aqiang.bsms.service.GenerateFile;
import com.aqiang.bsms.service.StudentService;
import com.aqiang.bsms.service.TeacherService;
import com.aqiang.bsms.utils.MSWordManager;

@Service
public class GenerateFileImpl implements GenerateFile {
	private static final Logger logger = LoggerFactory
			.getLogger(GenerateFileImpl.class);
	@Autowired
	private TeacherService teacherService;
	@Autowired
	private StudentService studentService;

	@Override
	public void generateSubject(Subject subject, String fileName) {
		MSWordManager msWordManager = new MSWordManager(true);
		msWordManager.createNewDocument();
		msWordManager.moveStart();
		msWordManager.insertFormatTextToWord("附件1", 12, true, 3, "宋体",
				"0,0,0,0", false);
		msWordManager.moveEnd();
		msWordManager.insertText("\n");

		msWordManager.insertFormatTextToWord("青海大学本科生毕业论文（设计）选题方向审批表", 16,
				true, 1, "宋体", "0,0,0,0", false);
		msWordManager.insertText("\n");
		msWordManager.insertFormatTextToWord("院、系（部）："
				+ subject.getCollege().getCollegeName(), 14, false, 3, "宋体",
				"0,0,0,0", false);
		msWordManager.insertText("\t\t");
		msWordManager.insertFormatTextToWord("教研室："
				+ subject.getTeachAndResearchOffice().getName(), 14, false, 3,
				"宋体", "0,0,0,0", false);
		msWordManager.insertText("\n");
		msWordManager.createTable(9, 8);
		int[] ColWidth = { 85, 52, 46, 49, 49, 52, 46, 49 };
		msWordManager.setCellsWidth(1, ColWidth);
		int[] RowHeight = { 35, 35, 35, 65, 65, 70, 90, 70, 90 };
		msWordManager.setCellsHight(1, RowHeight);
		msWordManager.mergeRowCell(1, 1, 2, 7);
		msWordManager.insertFormatTextToTable(1, 1, 1, "选题方向", "10.5", false,
				1, "宋体", "0,0,0,0", 1);
		msWordManager.insertFormatTextToTable(1, 1, 2,
				subject.getTopicChoosingWay(), "10.5", false, 1, "宋体",
				"0,0,0,0", 1);
		msWordManager.insertFormatTextToTable(1, 2, 1, "指导教师及职称", "10.5",
				false, 1, "宋体", "0,0,0,0", 1);
		msWordManager.insertFormatTextToTable(1, 2, 2, subject.getDon(),
				"10.5", false, 1, "宋体", "0,0,0,0", 1);
		msWordManager.insertFormatTextToTable(1, 2, 3, "职  称", "10.5", false,
				1, "宋体", "0,0,0,0", 1);
		msWordManager.insertFormatTextToTable(1, 2, 4,
				teacherService.getJobType(subject.getDonJob()), "10.5", false,
				1, "宋体", "0,0,0,0", 1);
		msWordManager.insertFormatTextToTable(1, 2, 5, "辅导教师", "10.5", false,
				1, "宋体", "0,0,0,0", 1);
		msWordManager.insertFormatTextToTable(1, 2, 6, subject.getTutorName(),
				"10.5", false, 1, "宋体", "0,0,0,0", 1);
		msWordManager.insertFormatTextToTable(1, 2, 7, "职  称", "10.5", false,
				1, "宋体", "0,0,0,0", 1);
		msWordManager.insertFormatTextToTable(1, 2, 8,
				teacherService.getJobType(subject.getTutorJob()), "10.5",
				false, 1, "宋体", "0,0,0,0", 1);
		msWordManager.insertFormatTextToTable(1, 3, 1, "设计起止时间", "10.5", false,
				1, "宋体", "0,0,0,0", 1);
		msWordManager.insertFormatTextToTable(1, 3, 2, new SimpleDateFormat(
				"yyyy-MM").format(subject.getBeginTime())
				+ "-\n"
				+ new SimpleDateFormat("yyyy-MM").format(subject.getEndTime()),
				"10.5", false, 1, "宋体", "0,0,0,0", 1);
		msWordManager.insertFormatTextToTable(1, 3, 3, "指导班级", "10.5", false,
				1, "宋体", "0,0,0,0", 1);
		msWordManager.mergeRowCell(1, 3, 4, 2);
		msWordManager.mergeRowCell(1, 3, 6, 2);
		msWordManager.insertFormatTextToTable(1, 3, 4, subject.getSpecialty()
				.getName(), "10.5", false, 1, "宋体", "0,0,0,0", 1);
		msWordManager.insertFormatTextToTable(1, 3, 5, "指导人数", "10.5", false,
				1, "宋体", "0,0,0,0", 1);
		msWordManager.insertFormatTextToTable(1, 3, 6,
				"" + subject.getGuideNumber(), "10.5", false, 1, "宋体",
				"0,0,0,0", 1);
		msWordManager.insertFormatTextToTable(1, 4, 1, "论文（设计）题目的来源和理论依据",
				"10.5", false, 1, "宋体", "0,0,0,0", 1);
		msWordManager.mergeRowCell(1, 4, 2, 7);
		msWordManager.insertFormatTextToTable(1, 4, 2,
				subject.getTaskFromAndTheoryBasis(), "10.5", false, 3, "宋体",
				"0,0,0,0", 1);
		msWordManager.insertFormatTextToTable(1, 5, 1,
				"主要的论文（设计）内容（含现有基础设施情况）", "10.5", false, 1, "宋体", "0,0,0,0", 1);
		msWordManager.mergeRowCell(1, 5, 2, 7);
		msWordManager.insertFormatTextToTable(1, 5, 2, subject.getContent(),
				"10.5", false, 3, "宋体", "0,0,0,0", 1);
		msWordManager.insertFormatTextToTable(1, 6, 1, "论文（设计）工作量及学生工作分工",
				"10.5", false, 1, "宋体", "0,0,0,0", 1);
		msWordManager.mergeRowCell(1, 6, 2, 7);
		msWordManager.insertFormatTextToTable(1, 6, 2, subject.getWorkload(),
				"10.5", false, 3, "宋体", "0,0,0,0", 1);
		msWordManager.insertFormatTextToTable(1, 7, 1, "论文（设计）过程经费预算", "10.5",
				false, 1, "宋体", "0,0,0,0", 1);
		msWordManager.mergeRowCell(1, 7, 2, 7);
		msWordManager.insertFormatTextToTable(1, 7, 2, subject.getBudget(),
				"10.5", false, 3, "宋体", "0,0,0,0", 1);
		msWordManager.insertFormatTextToTable(1, 8, 1, "教研室审批意见", "10.5",
				false, 1, "宋体", "0,0,0,0", 1);
		msWordManager.mergeRowCell(1, 8, 2, 7);
		msWordManager.insertFormatTextToTable(1, 8, 2,
				subject.getTeachAndResearchOfficeComment(), "10.5", false, 3,
				"宋体", "0,0,0,0", 1);
		msWordManager.insertFormatTextToTable(1, 9, 1, "系（部）学术委员会或教学指导委员会审批意见",
				"10.5", false, 1, "宋体", "0,0,0,0", 1);
		msWordManager.mergeRowCell(1, 9, 2, 7);
		msWordManager.insertFormatTextToTable(1, 9, 2,
				subject.getCollegeComment(), "10.5", false, 3, "宋体", "0,0,0,0",
				1);
		msWordManager.save(fileName);
		msWordManager.close();
	}

	@Override
	public void generateSubjectSelectGuide(College college, Event currentEvent,
			List<Subject> subjects, String fileName) {
		MSWordManager msWordManager = new MSWordManager(true);
		msWordManager.createNewDocument();
		msWordManager.setPageOrientation(1);
		msWordManager.moveStart();
		msWordManager.insertFormatTextToWord("附件2", 12, true, 3, "宋体",
				"0,0,0,0", false);
		msWordManager.moveEnd();
		msWordManager.insertText("\n");

		msWordManager.insertFormatTextToWord(
				String.format("青海大学%s届本科生毕业论文（设计）选题方向指南",
						currentEvent.getYear()), 16, true, 1, "宋体", "0,0,0,0",
				false);
		msWordManager.insertText("\n");
		msWordManager.insertFormatTextToWord(
				"院、系（部）：" + college.getCollegeName(), 14, false, 3, "宋体",
				"0,0,0,0", false);
		msWordManager.insertText("\n");
		msWordManager.createTable(1, 6);
		int[] width = { 50, 330, 80, 60, 100, 60 };
		msWordManager.setCellsWidth(1, width);
		msWordManager.insertFormatTextToTable(1, 1, 1, "序号", "10.5", false, 1,
				"宋体", "0,0,0,0", 1);
		msWordManager.insertFormatTextToTable(1, 1, 2, "毕业论文（设计）题目", "10.5",
				false, 1, "宋体", "0,0,0,0", 1);
		msWordManager.insertFormatTextToTable(1, 1, 3, "指导教师姓名", "10.5", false,
				1, "宋体", "0,0,0,0", 1);
		msWordManager.insertFormatTextToTable(1, 1, 4, "职称", "10.5", false, 1,
				"宋体", "0,0,0,0", 1);
		msWordManager.insertFormatTextToTable(1, 1, 5, "专业", "10.5", false, 1,
				"宋体", "0,0,0,0", 1);
		msWordManager.insertFormatTextToTable(1, 1, 6, "备注", "10.5", false, 1,
				"宋体", "0,0,0,0", 1);

		int row = 2;
		for (Subject subject : subjects) {
			msWordManager.addRow(1);
			msWordManager.insertFormatTextToTable(1, row, 1,
					String.valueOf(row - 1), "10.5", false, 1, "宋体", "0,0,0,0",
					1);
			msWordManager.insertFormatTextToTable(1, row, 2,
					subject.getTopicChoosingWay(), "10.5", false, 0, "宋体",
					"0,0,0,0", 1);
			msWordManager.insertFormatTextToTable(1, row, 3, subject.getDon(),
					"10.5", false, 1, "宋体", "0,0,0,0", 1);
			msWordManager.insertFormatTextToTable(1, row, 4,
					teacherService.getJobType(subject.getDonJob()), "10.5",
					false, 1, "宋体", "0,0,0,0", 1);
			msWordManager.insertFormatTextToTable(1, row, 5, subject
					.getSpecialty().getName(), "10.5", false, 1, "宋体",
					"0,0,0,0", 1);
			msWordManager.insertFormatTextToTable(1, row, 6, "", "10.5", false,
					1, "宋体", "0,0,0,0", 1);
			row++;
		}
		msWordManager.save(fileName);
		msWordManager.close();
	}

	@Override
	public void generateSubjectSelectSummary(College college,
			Event currentEvent, List<Group> groups, String fileName) {
		MSWordManager msWordManager = new MSWordManager(true);
		msWordManager.createNewDocument();
		msWordManager.setPageOrientation(1);
		msWordManager.moveStart();
		msWordManager.insertFormatTextToWord("附件3", 12, true, 3, "宋体",
				"0,0,0,0", false);
		msWordManager.moveEnd();
		msWordManager.insertText("\n");
		msWordManager.insertFormatTextToWord(String.format(
				"青海大学%s届本科生毕业论文（设计）选题汇总表", currentEvent.getYear()), 16, true,
				1, "宋体", "0,0,0,0", false);
		msWordManager.insertText("\n");
		msWordManager.insertFormatTextToWord(
				"院、系（部）：" + college.getCollegeName(), 14, false, 3, "宋体",
				"0,0,0,0", false);
		msWordManager.insertText("\n");
		msWordManager.createTable(2, 15);

		int[] width = { 40, 135, 40, 45, 35, 45, 35, 35, 35, 35, 35, 35, 35,
				60, 55 };
		msWordManager.setCellsWidth(1, width);
		msWordManager.mergeColCell(1, 1, 1, 2);
		msWordManager.mergeColCell(1, 1, 2, 2);
		msWordManager.mergeRowCell(1, 1, 3, 3);
		msWordManager.mergeRowCell(1, 1, 4, 2);
		msWordManager.mergeRowCell(1, 1, 5, 2);
		msWordManager.mergeRowCell(1, 1, 6, 4);
		msWordManager.mergeCell(1, 1, 7, 2, 14);
		msWordManager.mergeCell(1, 1, 8, 2, 15);
		msWordManager.insertFormatTextToTable(1, 1, 1, "序号", "12", false, 1,
				"宋体", "0,0,0,0", 1);
		msWordManager.insertFormatTextToTable(1, 1, 2, "毕业论文（设计）题目", "12",
				false, 1, "宋体", "0,0,0,0", 1);
		msWordManager.insertFormatTextToTable(1, 1, 3, "学   生", "12", false, 1,
				"宋体", "0,0,0,0", 1);
		msWordManager.insertFormatTextToTable(1, 1, 4, "指导教师", "12", false, 1,
				"宋体", "0,0,0,0", 1);
		msWordManager.insertFormatTextToTable(1, 1, 5, "类  型", "12", false, 1,
				"宋体", "0,0,0,0", 1);
		msWordManager.insertFormatTextToTable(1, 1, 6, "选题来源", "12", false, 1,
				"宋体", "0,0,0,0", 1);
		msWordManager.insertFormatTextToTable(1, 1, 7, "起止时间", "12", false, 1,
				"宋体", "0,0,0,0", 1);
		msWordManager.insertFormatTextToTable(1, 1, 8, "试验上 机时数", "12", false,
				1, "宋体", "0,0,0,0", 1);
		msWordManager.insertFormatTextToTable(1, 2, 3, "学号", "12", false, 1,
				"宋体", "0,0,0,0", 1);
		msWordManager.insertFormatTextToTable(1, 2, 4, "姓名", "12", false, 1,
				"宋体", "0,0,0,0", 1);
		msWordManager.insertFormatTextToTable(1, 2, 5, "班级", "12", false, 1,
				"宋体", "0,0,0,0", 1);
		msWordManager.insertFormatTextToTable(1, 2, 6, "姓名", "12", false, 1,
				"宋体", "0,0,0,0", 1);
		msWordManager.insertFormatTextToTable(1, 2, 7, "职称", "12", false, 1,
				"宋体", "0,0,0,0", 1);
		msWordManager.insertFormatTextToTable(1, 2, 8, "设计", "12", false, 1,
				"宋体", "0,0,0,0", 1);
		msWordManager.insertFormatTextToTable(1, 2, 9, "论文", "12", false, 1,
				"宋体", "0,0,0,0", 1);
		msWordManager.insertFormatTextToTable(1, 2, 10, "科研", "12", false, 1,
				"宋体", "0,0,0,0", 1);
		msWordManager.insertFormatTextToTable(1, 2, 11, "生产", "12", false, 1,
				"宋体", "0,0,0,0", 1);
		msWordManager.insertFormatTextToTable(1, 2, 12, "教学", "12", false, 1,
				"宋体", "0,0,0,0", 1);
		msWordManager.insertFormatTextToTable(1, 2, 13, "其它", "12", false, 1,
				"宋体", "0,0,0,0", 1);
		int row = 3;
		logger.info("find {} groups", groups.size());
		for (Group group : groups) {
			Subject subject = group.getSubject();
			List<Student> students = studentService.getStudentsByGroup(group);
			for (Student student : students) {
				msWordManager.addRow(1);
				msWordManager.insertFormatTextToTable(1, row, 1,
						String.valueOf(row - 2), "12", false, 1, "宋体",
						"0,0,0,0", 1);
				msWordManager.insertFormatTextToTable(1, row, 2,
						subject.getTopicChoosingWay(), "12", false, 0, "宋体",
						"0,0,0,0", 1);
				msWordManager
						.insertFormatTextToTable(1, row, 3,
								student.getNumber(), "12", false, 0, "宋体",
								"0,0,0,0", 1);
				msWordManager.insertFormatTextToTable(1, row, 4,
						student.getCompellation(), "12", false, 0, "宋体",
						"0,0,0,0", 1);
				msWordManager.insertFormatTextToTable(1, row, 5, student
						.getSpecialty().getName(), "12", false, 0, "宋体",
						"0,0,0,0", 1);
				msWordManager.insertFormatTextToTable(1, row, 6,
						subject.getDon(), "12", false, 1, "宋体", "0,0,0,0", 1);
				msWordManager.insertFormatTextToTable(1, row, 7,
						teacherService.getJobType(subject.getDonJob()), "12",
						false, 1, "宋体", "0,0,0,0", 1);
				if (subject.getSubjectType().equals(SubjectType.DESIGN)) {
					msWordManager.insertFormatTextToTable(1, row, 8, "√", "12",
							false, 1, "宋体", "0,0,0,0", 1);
				}
				if (subject.getSubjectType().equals(SubjectType.PAPER)) {
					msWordManager.insertFormatTextToTable(1, row, 9, "√", "12",
							false, 1, "宋体", "0,0,0,0", 1);
				}
				if (subject.getSubjectSource().equals(
						SubjectSource.SCIENTIFIC_RESEARCH)) {
					msWordManager.insertFormatTextToTable(1, row, 10, "√",
							"12", false, 1, "宋体", "0,0,0,0", 1);
				}
				if (subject.getSubjectSource().equals(SubjectSource.PRODUCTION)) {
					msWordManager.insertFormatTextToTable(1, row, 11, "√",
							"12", false, 1, "宋体", "0,0,0,0", 1);
				}
				if (subject.getSubjectSource().equals(SubjectSource.TEACHING)) {
					msWordManager.insertFormatTextToTable(1, row, 12, "√",
							"12", false, 1, "宋体", "0,0,0,0", 1);
				}
				if (subject.getSubjectSource().equals(SubjectSource.OTHERS)) {
					msWordManager.insertFormatTextToTable(1, row, 13, "√",
							"12", false, 1, "宋体", "0,0,0,0", 1);
				}
				msWordManager.insertFormatTextToTable(
						1,
						row,
						14,
						new SimpleDateFormat("yyyy-MM").format(subject
								.getBeginTime())
								+ "-\n"
								+ new SimpleDateFormat("yyyy-MM")
										.format(subject.getEndTime()), "12",
						false, 1, "宋体", "0,0,0,0", 1);
				row++;
			}
		}
		msWordManager.save(fileName);
		msWordManager.close();
	}

	@Override
	public void generateTask(Task task, String fileName) {
		MSWordManager msWordManager = new MSWordManager(true);
		msWordManager.createNewDocument();
		msWordManager.moveStart();
		msWordManager.insertFormatTextToWord("附件5", 12, true, 3, "宋体",
				"0,0,0,0", false);
		msWordManager.moveEnd();
		msWordManager.insertText("\n");
		msWordManager.insertFormatTextToWord("青海大学本科毕业论文（设计）任务书", 16, true, 1,
				"宋体", "0,0,0,0", false);
		msWordManager.insertText("\n");
		msWordManager.createTable(7, 4);
		int[] ColWidth = { 110, 90, 100, 120 };
		msWordManager.setCellsWidth(1, ColWidth);
		msWordManager.mergeRowCell(1, 3, 2, 3);
		msWordManager.mergeRowCell(1, 4, 1, 4);
		msWordManager.mergeRowCell(1, 5, 1, 4);
		msWordManager.mergeRowCell(1, 6, 1, 4);
		msWordManager.mergeRowCell(1, 7, 1, 4);
		msWordManager.insertFormatTextToTable(1, 1, 1, "学生姓名", "12", false, 1,
				"宋体", "0,0,0,0", 1);
		msWordManager.insertFormatTextToTable(1, 1, 2, task.getStudent()
				.getCompellation(), "12", false, 1, "宋体", "0,0,0,0", 1);
		msWordManager.insertFormatTextToTable(1, 1, 3, "专业、班级", "12", false, 1,
				"宋体", "0,0,0,0", 1);
		msWordManager.insertFormatTextToTable(1, 1, 4, task.getStudent()
				.getSpecialty().getName(), "12", false, 1, "宋体", "0,0,0,0", 1);
		msWordManager.insertFormatTextToTable(1, 2, 1, "指导老师", "12", false, 1,
				"宋体", "0,0,0,0", 1);
		msWordManager.insertFormatTextToTable(1, 2, 2, task.getTeacher()
				.getCompellation(), "12", false, 1, "宋体", "0,0,0,0", 1);
		msWordManager.insertFormatTextToTable(1, 2, 3, "工作单位", "12", false, 1,
				"宋体", "0,0,0,0", 1);
		msWordManager.insertFormatTextToTable(1, 2, 4, task.getCompany(), "12",
				false, 1, "宋体", "0,0,0,0", 1);

		msWordManager.insertFormatTextToTable(1, 3, 1, "论文（设计）题目：", "12",
				false, 3, "宋体", "0,0,0,0", 1);
		msWordManager.insertFormatTextToTable(1, 3, 2, task.getSubject()
				.getTopicChoosingWay(), "12", false, 1, "宋体", "0,0,0,0", 1);
		String tab = "\t\t\t\t\t\t\t\t\t\t\t";
		String mainContent = "论文（设计）主要内容：\n"
				+ task.getDissertationMainContent();
		String mainTask = "要求完成的主要任务：\n" + task.getMainTask() + "\n" + tab
				+ "指导教师签名 ：\n\n" + tab + "学生签名 ：\n";
		String txt3 = "教研室意见：\n\n\n\n\n" + tab + "教研室主任签名 ：\n" + tab
				+ "年\t\t月\t\t日";
		String txt4 = "系（部）意见：\n" + "\n\n" + tab + "系（部）负责人（盖章） ：\n" + tab
				+ "年\t\t月\t\t日";
		msWordManager.insertFormatTextToTable(1, 4, 1, mainContent, "12",
				false, 3, "宋体", "0,0,0,0", 1);
		msWordManager.insertFormatTextToTable(1, 5, 1, mainTask, "12", false,
				3, "宋体", "0,0,0,0", 1);
		msWordManager.insertFormatTextToTable(1, 6, 1, txt3, "12", false, 3,
				"宋体", "0,0,0,0", 1);
		msWordManager.insertFormatTextToTable(1, 7, 1, txt4, "12", false, 3,
				"宋体", "0,0,0,0", 1);
		msWordManager.save(fileName);
		msWordManager.close();
	}

	@Override
	public void wordToPDF(String sfileName, String toFileName) {
		MSWordManager msWordManager = new MSWordManager(false);
		msWordManager.openDocument(sfileName);
		File tofile = new File(toFileName);
		if (tofile.exists()) {
			tofile.delete();
		}
		msWordManager.saveAs(toFileName, 17);
		msWordManager.close();
	}

}

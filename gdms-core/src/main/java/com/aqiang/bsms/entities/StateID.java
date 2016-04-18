package com.aqiang.bsms.entities;

public interface StateID {

	// 初始化
	public static final String BEGIN_SYSTEM_MANAGER = "begin_system_manager";
	public static final String BEGIN_COLLEGE_MANAGER = "begin_college_manager";
	public static final String BEGIN_TEACHER = "begin_teacher";
	public static final String BEGIN_STUDENT = "begin_student";

	// 报题阶段
	public static final String APPLY_SUBJECT_COLLEGE_MANAGER = "apply_subject_college_manager";
	public static final String APPLY_SUBJECT_TEACHER = "apply_subject_teacher";
	// 选题阶段
	public static final String SELECT_SUBJECT_COLLEGE_MANAGER = "select_subject_college_manager";
	public static final String SELECT_SUBJECT_TEACHER = "select_subject_teacher";
	public static final String SELECT_SUBJECT_STUDENT = "select_subject_student";
	// 毕设阶段
	// 答辩阶段
}

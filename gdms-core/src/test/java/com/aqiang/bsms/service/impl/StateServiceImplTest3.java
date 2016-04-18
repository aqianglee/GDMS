package com.aqiang.bsms.service.impl;

import org.junit.Test;

import com.aqiang.bsms.entities.StateID;
import com.aqiang.bsms.entities.UserType;
import com.aqiang.bsms.entities.WorkflowStatus;
import com.aqiang.gdms.BaseTest;

public class StateServiceImplTest3 extends BaseTest {
	@Test
	public void testGetStateId() {
		isTrue(stateService.getStateId(UserType.STUDENT, WorkflowStatus.BEGIN).equals(StateID.BEGIN_STUDENT));
		isTrue(stateService.getStateId(UserType.TEACHER, WorkflowStatus.BEGIN).equals(StateID.BEGIN_TEACHER));
		isTrue(stateService.getStateId(UserType.COLLEGE_MANAGER, WorkflowStatus.BEGIN).equals(
				StateID.BEGIN_COLLEGE_MANAGER));
		isTrue(stateService.getStateId(UserType.SYSTEM_MANAGER, WorkflowStatus.BEGIN).equals(
				StateID.BEGIN_SYSTEM_MANAGER));
		isTrue(stateService.getStateId(UserType.TEACHER, WorkflowStatus.APPLY_SUBJECT).equals(
				StateID.APPLY_SUBJECT_TEACHER));
		isTrue(stateService.getStateId(UserType.COLLEGE_MANAGER, WorkflowStatus.APPLY_SUBJECT).equals(
				StateID.APPLY_SUBJECT_COLLEGE_MANAGER));
		isTrue(stateService.getStateId(UserType.STUDENT, WorkflowStatus.SELECT_SUBJECT).equals(
				StateID.SELECT_SUBJECT_STUDENT));
		isTrue(stateService.getStateId(UserType.TEACHER, WorkflowStatus.SELECT_SUBJECT).equals(
				StateID.SELECT_SUBJECT_TEACHER));
		isTrue(stateService.getStateId(UserType.COLLEGE_MANAGER, WorkflowStatus.SELECT_SUBJECT).equals(
				StateID.SELECT_SUBJECT_COLLEGE_MANAGER));
	}

}

package com.aqiang.bsms.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * 选题实体
 * 
 * @author aqiang
 */
@Entity
@Table(name = "SUBJECTS")
public class Subject implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	// 院、系、部门
	private College college;
	// 教研室
	private TeachAndResearchOffice teachAndResearchOffice;
	// 选题方向
	private String topicChoosingWay;
	// 信息方向
	private String infoDirection;
	// 指导教师
	private String don;

	private String donJob;
	// 辅导教师
	private String tutorName;

	private String tutorJob;
	// 开始时间
	private Date beginTime;
	// 结束时间
	private Date endTime;
	// 指导班级
	private Specialty specialty;
	// 指导人数
	private Integer guideNumber;
	// 论文（设计）题目的来源和理论依据
	private String taskFromAndTheoryBasis;
	// 主要的论文（设计）内容（含现有基础设施情况）
	private String content;
	// 论文（设计）工作量及学生工作分工
	private String workload;
	// 论文（设计）过程经费预算
	private String budget;
	// 教研室审批意见
	private String teachAndResearchOfficeComment;
	// 系（部）学术委员会或教学指导委员会审批意见
	private String collegeComment;
	// 教师
	private User user;
	// 选择该题的所有学生
	private List<Student> students = new ArrayList<Student>();
	// 最终选择该题目的学生小组
	private Group group;
	// 是否审批
	private Boolean commented;
	private File file;

	private Event event;

	private String subjectType;
	private String subjectSource;
	private String subjectApplyType;

	@OneToOne
	@JoinColumn(name = "File_ID", unique = true)
	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne
	@JoinColumn(name = "COLLEGE_ID")
	public College getCollege() {
		return college;
	}

	public void setCollege(College college) {
		this.college = college;
	}

	@ManyToOne
	@JoinColumn(name = "TEACH_AND_RESEAR_CHOFFICE_id")
	public TeachAndResearchOffice getTeachAndResearchOffice() {
		return teachAndResearchOffice;
	}

	public void setTeachAndResearchOffice(
			TeachAndResearchOffice teachAndResearchOffice) {
		this.teachAndResearchOffice = teachAndResearchOffice;
	}

	public String getInfoDirection() {
		return infoDirection;
	}

	public void setInfoDirection(String infoDirection) {
		this.infoDirection = infoDirection;
	}

	public String getDon() {
		return don;
	}

	public void setDon(String don) {
		this.don = don;
	}

	public String getDonJob() {
		return donJob;
	}

	public void setDonJob(String donJob) {
		this.donJob = donJob;
	}

	public String getTutorName() {
		return tutorName;
	}

	public void setTutorName(String tutorName) {
		this.tutorName = tutorName;
	}

	public String getTutorJob() {
		return tutorJob;
	}

	public void setTutorJob(String tutorJob) {
		this.tutorJob = tutorJob;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	@ManyToOne
	@JoinColumn(name = "SPECIALTY_ID")
	public Specialty getSpecialty() {
		return specialty;
	}

	public void setSpecialty(Specialty specialty) {
		this.specialty = specialty;
	}

	public Integer getGuideNumber() {
		return guideNumber;
	}

	public void setGuideNumber(Integer guideNumber) {
		this.guideNumber = guideNumber;
	}

	@Column(length = 1024)
	public String getTaskFromAndTheoryBasis() {
		return taskFromAndTheoryBasis;
	}

	public void setTaskFromAndTheoryBasis(String taskFromAndTheoryBasis) {
		this.taskFromAndTheoryBasis = taskFromAndTheoryBasis;
	}

	@Column(length = 1024)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(length = 1024)
	public String getWorkload() {
		return workload;
	}

	public void setWorkload(String workload) {
		this.workload = workload;
	}

	@Column(length = 1024)
	public String getBudget() {
		return budget;
	}

	public void setBudget(String budget) {
		this.budget = budget;
	}

	@Column(length = 1024)
	public String getTeachAndResearchOfficeComment() {
		return teachAndResearchOfficeComment;
	}

	public void setTeachAndResearchOfficeComment(
			String teachAndResearchOfficeComment) {
		this.teachAndResearchOfficeComment = teachAndResearchOfficeComment;
	}

	@Column(length = 1024)
	public String getCollegeComment() {
		return collegeComment;
	}

	public void setCollegeComment(String collegeComment) {
		this.collegeComment = collegeComment;
	}

	@ManyToOne
	@JoinColumn(name = "USER_ID")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@ManyToMany(mappedBy = "subjects")
	public List<Student> getStudents() {
		return students;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	}

	@OneToOne
	@JoinColumn(name = "GROUP_ID", unique = true)
	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public Boolean isCommented() {
		return commented;
	}

	public Boolean getCommented() {
		return commented;
	}

	public void setCommented(Boolean commented) {
		this.commented = commented;
	}

	public String getTopicChoosingWay() {
		return topicChoosingWay;
	}

	public void setTopicChoosingWay(String topicChoosingWay) {
		this.topicChoosingWay = topicChoosingWay;
	}

	@ManyToOne
	@JoinColumn(name = "EVENT_ID")
	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public String getSubjectType() {
		return subjectType;
	}

	public void setSubjectType(String subjectType) {
		this.subjectType = subjectType;
	}

	public String getSubjectSource() {
		return subjectSource;
	}

	public void setSubjectSource(String subjectSource) {
		this.subjectSource = subjectSource;
	}

	public String getSubjectApplyType() {
		return subjectApplyType;
	}

	public void setSubjectApplyType(String subjectApplyType) {
		this.subjectApplyType = subjectApplyType;
	}

}

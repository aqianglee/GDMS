package com.aqiang.bsms.dao.impl;

import org.springframework.stereotype.Repository;

import com.aqiang.bsms.dao.StudentDao;
import com.aqiang.bsms.entities.Student;

@Repository("studentDao")
public class StudentDaoImpl extends BaseDaoImpl<Student> implements StudentDao {

}

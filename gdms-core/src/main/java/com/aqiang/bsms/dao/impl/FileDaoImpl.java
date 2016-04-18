package com.aqiang.bsms.dao.impl;

import org.springframework.stereotype.Repository;

import com.aqiang.bsms.dao.FileDao;
import com.aqiang.bsms.entities.File;

@Repository("fileDao")
public class FileDaoImpl extends BaseDaoImpl<File> implements FileDao {

}

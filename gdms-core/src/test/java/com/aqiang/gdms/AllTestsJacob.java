package com.aqiang.gdms;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.aqiang.bsms.service.impl.DownloadServiceImplTest2;
import com.aqiang.bsms.service.impl.DownloadServiceImplTest3;
import com.aqiang.bsms.service.impl.EventServiceImplTest3;
import com.aqiang.bsms.service.impl.EventServiceImplTest4;
import com.aqiang.bsms.service.impl.EventServiceImplTest5;
import com.aqiang.bsms.service.impl.EventServiceImplTest6;
import com.aqiang.bsms.service.impl.GenerateFileImplTest;
import com.aqiang.bsms.service.impl.GenerateFileImplTest2;
import com.aqiang.bsms.service.impl.GenerateFileImplTest3;
import com.aqiang.bsms.service.impl.GenerateFileImplTest4;
import com.aqiang.bsms.service.impl.GenerateFileImplTest5;
import com.aqiang.bsms.service.impl.TaskServiceImplTest;

@RunWith(Suite.class)
@SuiteClasses({ 
	DownloadServiceImplTest2.class, 
	DownloadServiceImplTest3.class,
	EventServiceImplTest3.class,
	EventServiceImplTest4.class,
	EventServiceImplTest5.class,
	EventServiceImplTest6.class,
	GenerateFileImplTest.class,
	GenerateFileImplTest2.class, 
	GenerateFileImplTest3.class, 
	GenerateFileImplTest4.class, 
	GenerateFileImplTest5.class, 
	TaskServiceImplTest.class,
	
})
public class AllTestsJacob {
	
}

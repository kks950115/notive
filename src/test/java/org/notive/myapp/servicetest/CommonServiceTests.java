package org.notive.myapp.servicetest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.notive.myapp.mapper.CommonMapper;
import org.notive.myapp.service.CommonService;
import org.notive.myapp.service.TrashService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

@NoArgsConstructor
@Log4j2


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {
		"file:src/main/webapp/WEB-INF/spring/root-context.xml",
		"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"
})
public class CommonServiceTests {
	
	@Setter(onMethod_=@Autowired)
	private CommonMapper mapper;
	
	@Setter(onMethod_=@Autowired)
	private CommonService service;
	
	@Setter(onMethod_=@Autowired)
	private TrashService tservice;

	@Before
	public void setup() {
		log.debug("setup() invoked.");
		
		log.info("\t+ mapper : {}", service);

	} // setup

	@Test
	public void testCheckGroupName() {
		log.debug("testCheckGroupName() invoked.");
		
//		int ckName = service.checkGroupName("그룹6");
		tservice.toTrashBinAllUndeleted();
//		log.info(ckName);
		
	} // testCheckGroupName
	
	@After
	public void tearDown() {
		log.debug("tearDown() invoked.");
		
	} // tearDown
	
	
} // end class

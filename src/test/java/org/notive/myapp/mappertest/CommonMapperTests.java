package org.notive.myapp.mappertest;

import static org.junit.Assert.assertNotNull;

import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.notive.myapp.mapper.CommonMapper;
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
		"file:src/main/webapp/WEB-INF/spring/root-context.xml"
})
public class CommonMapperTests {
	
	@Setter(onMethod_=@Autowired)
	private CommonMapper mapper;

	@Before
	public void setup() {
		log.debug("setup() invoked.");
		
		assertNotNull(mapper);
		log.info("\t+ mapper : {}", mapper);

	} // setup
	
	@Test
	public void testSelectCountValue() {
		log.debug("testSelectCountValue() invoked.");
		
		mapper.selectCountValue("tbl_group", "group_name", "그룹6");
		

	} // testSelectCountValue
	
	@Test
	public void testSelectMap() {
		log.debug("testSelectMap() invoked.");
		
		List<Map<String, Object>> map = mapper.selectAllByIntegerParam("tbl_directory", "dir_state", 2);
		
		log.info(map);

	} // testSelectMap
	
	@After
	public void tearDown() {
		log.debug("tearDown() invoked.");
		
	} // tearDown
	
	
} // end class

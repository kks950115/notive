package org.notive.myapp.servicetest;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.notive.myapp.domain.GroupVO;
import org.notive.myapp.domain.UserGroupVO;
import org.notive.myapp.mapper.GroupMapper;
import org.notive.myapp.service.GroupService;
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
public class GroupServiceTests {
	
	@Setter(onMethod_=@Autowired)
	private GroupMapper mapper;
	
	@Setter(onMethod_=@Autowired)
	private GroupService service;

	@Before
	public void setup() {
		log.debug("setup() invoked.");
		
		assertNotNull(service);
		log.info("\t+ mapper : {}", service);

	} // setup
	
	@Test
	public void testGetUserGrouplist() {
		log.debug("testGetUserGrouplist() invoked.");
		
		List<GroupVO> groupList = mapper.selectUserGroupList("lbendon2r@tamu.edu");
		
		groupList.forEach(log::info);
		
	} // testGetUserGrouplist
	
	@Test
	public void testCreateGroup() {
		log.debug("testCreateGroup() invoked.");
		
		// 임의의 값으로 그룹 생성 테스트
		service.createGroup("tspellworth6@ezinearticles.com", "그룹366");
		
	} // testCreateGroup
	
	@Test
	public void testJoinGroup() {
		log.debug("testJoinGroup() invoked.");
		
		UserGroupVO userGroup = new UserGroupVO(1, "lcrocker4@disqus.com", "Giant anteater");
		
		// 임의의 값으로 그룹 생성 테스트
		service.joinGroup(userGroup);
		
	} // testJoinGroup
	
	@Test
	public void testRemoveUserGroup() {
		log.debug("testRemoveUserGroup() invoked.");
		
		UserGroupVO userGroup = new UserGroupVO(null, "tspellworth6@ezinearticles.com", "ㅁㄴㄹㅇ");
		
		// 임의의 값으로 그룹 생성 테스트
		service.removeUserGroup(userGroup);
		
	} // testRemoveUserGroup
	
	@After
	public void tearDown() {
		log.debug("tearDown() invoked.");
		
	} // tearDown
	
	
} // end class

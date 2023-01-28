package org.notive.myapp.mappertest;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.notive.myapp.domain.GroupVO;
import org.notive.myapp.domain.UserGroupVO;
import org.notive.myapp.mapper.GroupMapper;
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
public class GroupMapperTests {
	
	@Setter(onMethod_=@Autowired)
	private GroupMapper mapper;

	@Before
	public void setup() {
		log.debug("setup() invoked.");
		
		assertNotNull(mapper);
		log.info("\t+ mapper : {}", mapper);

	} // setup
	
	@Test
	public void testGetGroupList() {
		log.debug("testGetGroupList() invoked.");
		
		List<GroupVO> groupList = mapper.selectUserGroupList("lbendon2r@tamu.edu");
		
		groupList.forEach(log::info);
		
	} // testGetGroupList
	
	@Test
	public void testGetGroupNList() {
		log.debug("testGetGroupNList() invoked.");
		
		List<UserGroupVO> groupList = mapper.selectUserGroupNList("tspellworth6@ezinearticles.com");
		
		groupList.forEach(log::info);
		
	} // testGetGroupNList
	
	@Test
	public void testSelectCountExistUserInGroup() {
		log.debug("selectCountExistUserInGroup() invoked.");
		
		int result = mapper.selectCountExistUserInGroup(340, "tspellworth6@ezinearticles.com");
		
		log.info(result);
		
	} // testGetGroupNList
	
	@Test
	public void testInsertGroup() {
		log.debug("testInsertGroup() invoked.");
		
		int success = mapper.insertGroup("그룹500");
		
		log.info(success);

	} // testInsertGroup
	
	@Test
	public void testInsertGroupSelectKey() {
		log.debug("testInsertGroup() invoked.");
		
		GroupVO group = new GroupVO(null, "그룹223");
		
		int success = mapper.insertGroupSelectKey(group);
		
		log.info(success);

	} // testInsertGroupSelectKey

	@Test
	public void testInsertUserGroup() {
		log.debug("testInsertUserGroup() invoked.");
		
		GroupVO groupVO = new GroupVO(1, "Armadillo, common long-nosed");
		
		UserGroupVO userGroupVO = new UserGroupVO(groupVO.getGroup_no(), "ljohn5@shutterfly.com", groupVO.getGroup_name());
		
		int success = mapper.insertUserGroup(userGroupVO);
		
		log.info(success);

	} // testInsertUserGroup
	
	@Test
	public void testDeleteUserGroup() {
		log.debug("testDeleteUserGroup() invoked.");
		
		UserGroupVO userGroup = new UserGroupVO(null, "tvaughanhughes2i@feedburner.com", "그룹1");
		
		mapper.deleteUserGroup(userGroup);

	} // testDeleteUserGroup
	
	@Test
	public void testDeleteGroup() {
		log.debug("testDeleteGroup() invoked.");
		
		mapper.deleteGroup();

	} // testDeleteGroup
	
	@After
	public void tearDown() {
		log.debug("tearDown() invoked.");
		
	} // tearDown
	
	
} // end class

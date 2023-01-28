package org.notive.myapp.servicetest;

import java.util.Random;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.notive.myapp.domain.UserDTO;
import org.notive.myapp.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@NoArgsConstructor
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"file:src/main/webapp/WEB-INF/spring/root-context.xml",
		"file:src/main/webapp/WEB-INF/spring/*/*.xml"
})
public class UserServiceTests {

	@Autowired
	UserServiceImpl service;
	
	@Autowired
	UserDTO dto;
	
	
	@Test
	public void tempPassTest() throws Exception {
		log.debug("tempPass() invoked.");
		
		Random random = new Random();

		StringBuffer buffer = new StringBuffer();

		while (buffer.length() < 8) {
			int num = random.nextInt(10);

			buffer.append(num);
		} // while

		
		// 2. 난수 사이에 끼워넣을 특수문자열 생성
		StringBuffer buffer2 = new StringBuffer("!@#$%^&*-=?~");

		
		// 3. 난수 사이에 특수문자 2개 끼워넣기
		buffer.setCharAt(((int)(Math.random()*3)+1), buffer2.charAt((int)(Math.random()*buffer.length()-1)));
		buffer.setCharAt(((int)(Math.random()*4)+4), buffer2.charAt((int)(Math.random()*buffer.length()-1)));		

		
		// 4. 임시 비밀번호 암호화 (DB저장용)
		String tempPass = buffer.toString();

		log.info(tempPass);
		
	} //tempPassTest
} // end class

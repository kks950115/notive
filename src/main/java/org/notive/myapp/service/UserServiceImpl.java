package org.notive.myapp.service;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpSession;

import org.notive.myapp.controller.UserLoginController;
import org.notive.myapp.domain.ModifyVO;
import org.notive.myapp.domain.UserDTO;
import org.notive.myapp.domain.UserImageDTO;
import org.notive.myapp.domain.UserImageVO;
import org.notive.myapp.domain.UserVO;
import org.notive.myapp.mapper.UserMapper;
import org.notive.myapp.util.MailUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@NoArgsConstructor

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper mapper;

	@Autowired
	private BCryptPasswordEncoder pwEncoder;

	@Autowired
	private JavaMailSender mailSender;

	// ---------------------회원가입
	@Override
	public void signUp(UserDTO dto, UserImageDTO imgDto) throws Exception {
		log.debug("signUp({},{}) invoked.", dto, imgDto);
		
		assert(mapper!=null);
		
		// 비밀번호 암호화
		String encryptedPass = this.pwEncoder.encode(dto.getUserPass());
		
		// select box로 받은 년/월/일을 Date타입으로 변환
		String userBdayStr = dto.getYear() + "/"
				+ dto.getMonth() + "/"
				+ dto.getDay();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		
        Date userBday = new Date(sdf.parse(userBdayStr).getTime());

        // DTO로 받은 값을 VO에 대입
		UserVO userVO = new UserVO(
				dto.getUserID(),
				encryptedPass,
				dto.getUserName(),
				userBday,
				null,
				null,
				null,
				null,
				null,
				null
				);
		
		this.mapper.createUser(userVO);
		
		// 첨부된 프로필 이미지가 없을경우 메소드 종료
		if (imgDto.getUuid() == null) { 
			log.debug("\t **** userImage is null");
			return;
		} //if
		
		// 첨부된 프로필 이미지가 있을경우 db저장
		log.info("userImage : " + imgDto.getUuid());
		
		UserImageVO imgVO = new UserImageVO(
				imgDto.getUuid(),
				imgDto.getUploadPath(),
				imgDto.getFileName(),
//				imgDto.getFileType(),
				dto.getUserID()
				);
		
		this.mapper.insertUserImage(imgVO);		
	} // signUp
	

	// ---------------------아이디 중복 검사
	@Override
	public int checkID(String userID) {

		return this.mapper.checkID(userID);
	} // checkID

	// ---------------------인증메일 발송
	@Override
	public String sendAuthMail(String userID) {

		// 1. 6자리 난수 인증키 생성
		Random random = new Random();

		StringBuffer buffer = new StringBuffer();

		while (buffer.length() < 6) {
			int num = random.nextInt(10);

			buffer.append(num);
		} // while

		String userAuthKey = buffer.toString();

		// 2. 인증메일 발송
		try {
			MailUtils sendMail = new MailUtils(mailSender);

			sendMail.setSubject("[ Notive ] 회원가입을 위한 확인 요청");
			sendMail.setText(new StringBuffer()
					.append("<table style=\"width: 500px\">")
					.append("<tr>")
					.append("<td style=\"border-top: solid 20px rgb(38, 110, 255); border-bottom: solid 20px rgb(38, 110, 255);\">")
					.append("<h1> Notive</h1>")
					.append("</td> </tr> <tr> <td> <br><br> ")
					.append(userID)
					.append("님, <Br>"
							+ "Notive 계정을 생성해 주셔서 감사합니다. <br><br>"
							+ "아래 링크를 통해 회원가입을 완료해주세요.<br><Br>")
					.append("<a href='http://localhost:8090/user/signUpConfirm?userID=")
					.append(userID)
					.append("&userAuthKey=")
					.append(userAuthKey)
					.append("'target='_blank'>회원가입 완료하기</a>")
					.append("</td></tr></table>")
					.toString()
					);
			
			sendMail.setFrom("notive.adm@gmail.com", "NOTIVE");
			sendMail.setTo(userID);
			sendMail.send();
			
			

		} catch (Exception e) {
			e.printStackTrace();
		} // try-catch

		return userAuthKey;
	} // sendAuthMail

	// ---------------------인증키 업데이트
	@Override
	public void updateAuthKey(UserDTO dto) throws Exception {
		log.debug("updateAuthKey(map) invoked.");

		Map<String, String> map = new HashMap<>();

		map.put("userID", dto.getUserID());
		map.put("userAuthKey", dto.getUserAuthKey());
		log.info("\t+ map: " + map);

		this.mapper.updateAuthKey(map);

	} // updateAuthKey

	// ---------------------인증 여부 업데이트
	@Override
	public void updateAuthStatus(UserDTO dto) throws Exception {
		log.debug("updateAuthStatus(map) invoked.");

		Map<String, String> map = new HashMap<>();

		map.put("userID", dto.getUserID());
		map.put("userAuthKey", dto.getUserAuthKey());
		log.info("\t+ map: " + map);

		this.mapper.updateAuthStatus(map);
	} // updateAuthStatus

	// ---------------------로그인
	@Override
	public UserVO login(UserDTO dto) throws Exception {
		log.debug("login(userDTO) invoked.");

		return this.mapper.login(dto);
	}// login

	// ---------------------remember-me 사용여부 업데이트
	@Override
	public int modifyUserWithRememberMe(String userID, String rememberMe, Date rememberMeMaxAge) throws Exception {
		log.debug("modifyUserWithRememberMe({},{},{}) invoked.", userID, rememberMe, rememberMeMaxAge);

		return this.mapper.updateUserWithRememberMe(userID, rememberMe, rememberMeMaxAge);
	} // modifyUserWithRememberMe

	// ---------------------remember-me 로그인 유지
	@Override
	public UserVO findUserByRememberMe(String rememberMe) throws Exception {
		log.debug("findUserByRememberMe({}) invoked.", rememberMe);

		return this.mapper.selectUserWithRememberMe(rememberMe);
	} // findUserByRememberMe

	// ---------------------로그아웃 (remember-me 관련 컬럼 null로 업데이트)
	@Override
	public void modifyUserWithoutRememberMe(String userID) throws Exception {
		log.debug("updateUserWithoutRememberMe({}) invoked.", userID);

		this.mapper.updateUserWithoutRememberMe(userID);

	} // updateUserWithoutRememberMe

	// ---------------------마이페이지 회원등급(유료결제 서비스) 조회
	@Override
	public String showUserGrade(String userID) throws Exception {
		log.debug("showUserGrade({}) invoked.", userID);

		this.mapper.selectUserGrade(userID);

		return userID;
	} // showUserGrade

	// ---------------------마이페이지 비밀번호 수정
	@Override
	public void modifyUserPass(HttpSession session, UserDTO dto) throws Exception {
		log.debug("modifyUserPass(dto) invoked.");

		UserVO loginUser = (UserVO) session.getAttribute(UserLoginController.loginKey);
		String userID = loginUser.getUserID();

		// 새로 입력받은 비밀번호 암호화
		String encryptedPass = this.pwEncoder.encode(dto.getUserPass());

		ModifyVO modifyVO = new ModifyVO(userID, encryptedPass, null, loginUser.getUserBday(), null);

		this.mapper.updateUserPass(modifyVO);
	} // modifyUserPass

	// ---------------------마이페이지 이름 수정
	@Transactional
	@Override
	public UserVO modifyUserName(HttpSession session, String userName) throws Exception {
		log.debug("modifyUserName(session, dto) invoked.");

		UserVO loginUser = (UserVO) session.getAttribute(UserLoginController.loginKey);
		String userID = loginUser.getUserID();

		ModifyVO modifyVO = new ModifyVO(userID, null, userName, null, null);

		this.mapper.updateUserName(modifyVO);

		UserVO userVO = this.mapper.selectUserAfterModify(userID);

		return userVO;
	} // modifyUserName

	// ---------------------마이페이지 생일 수정
	@Override
	public UserVO modifyUserBday(HttpSession session, String year, String month, String day) throws Exception {
		log.debug("modifyUserBday(session, dto) invoked.");

		UserVO loginUser = (UserVO) session.getAttribute(UserLoginController.loginKey);
		String userID = loginUser.getUserID();

		// select box로 받은 년/월/일을 Date타입으로 변환
		String userBdayStr = year + "/" + month + "/" + day;

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

		Date userBday = new Date(sdf.parse(userBdayStr).getTime());

		ModifyVO modifyVO = new ModifyVO(userID, null, null, userBday, null);

		assert (modifyVO != null);
		log.info("\t+ userVO :" + modifyVO);

		this.mapper.updateUserBday(modifyVO);

		UserVO userVO = this.mapper.selectUserAfterModify(userID);

		return userVO;
	}// modifyUserInfo

	// ---------------------아이디 찾기
	@Override
	public String findUserID(UserDTO dto) throws Exception {
		log.debug("findUserID({}) invoked.", dto);

		String userID = this.mapper.selectUserID(dto);

		return userID;
	} // findUSerID

	// ---------------------임시비밀번호 발급 및 업데이트
	@Override
	public void findUserPass(UserDTO dto) throws Exception {
		log.debug("findUserPass(findDTO) invoked.");

		// 1. 8자리 난수 임시비밀번호 생성
		Random random = new Random();

		StringBuffer buffer = new StringBuffer();

		while (buffer.length() < 8) {
			int num = random.nextInt(10);

			buffer.append(num);
		} // while

		
		// 2. 난수 사이에 끼워넣을 특수문자열 생성
		StringBuffer buffer2 = new StringBuffer("!@#$%^&*-=?~");

		
		// 3. 난수 사이에 임의의 특수문자 2개 끼워넣기
		//	3-1. 난수 1~4번째 자리 숫자 중 무작위 1개를, 임의의 특수문자 1개로 대체
		buffer.setCharAt(
				((int)(Math.random()*3)+1), 
				buffer2.charAt((int)(Math.random()*buffer.length()-1))
				);
		//	3-2. 난수 5~8번째 자리 숫자 중 무작위 1개를, 임의의 특수문자 1개로 대체
		buffer.setCharAt
		(((int)(Math.random()*4)+4), 
				buffer2.charAt((int)(Math.random()*buffer.length()-1))
				);		

		log.info("tempPass before toString : " + buffer);
		
		// 4. 임시 비밀번호 암호화 (DB저장용)
		String tempPass = buffer.toString();
		log.info("tempPass after toString : " + tempPass);

		String encryptedPass = this.pwEncoder.encode(tempPass);

		
		// 5. select box로 입력받은 년/월/일을 Date타입으로 변환
		String userBdayStr = dto.getYear() + "/" + dto.getMonth() + "/" + dto.getDay();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

		Date userBday = new Date(sdf.parse(userBdayStr).getTime());

		
		// 6. 암호화된 임시비밀번호를 VO에 대입
		ModifyVO modifyVO = new ModifyVO(dto.getUserID(), encryptedPass, dto.getUserName(), userBday, null);

		this.mapper.updateUserPass(modifyVO);

		// 7. 임시 비밀번호 메일 전송
		try {
			MailUtils sendMail = new MailUtils(mailSender);

			sendMail.setSubject("[ Notive ] 임시 비밀번호 안내");
			sendMail.setText(new StringBuffer()
					.append("<table style=\"width: 500px\">")
					.append("<tr>")
					.append("<td style=\"border-top: solid 20px rgb(38, 110, 255); border-bottom: solid 20px rgb(38, 110, 255);\">")
					.append("<h1> Notive 임시 비밀번호 안내 </h1>")
					.append("</td> </tr> <tr> <td> <br><br> ")
					.append("회원님의 임시 비밀번호는 <br>")
					.append(tempPass + "입니다.<br>")
					.append("로그인 후 비밀번호를 변경해주세요!<br><br>")
					.append("<a href='http://localhost:8090/user/login'> 로그인 하기</a>")
					.append("</td></tr></table>")
					.toString());

			sendMail.setFrom("notive.adm@gmail.com", "NOTIVE");
			sendMail.setTo(dto.getUserID());
			sendMail.send();
	

		} catch (Exception e) {
			e.printStackTrace();
		} // try-catch
	} // createNewPass

	// -----------------회원탈퇴
	@Override
	public void withdraw(String userID) throws Exception {
		log.debug("withdrow({}) invoked.", userID);

		this.mapper.updateUserState(userID);
	} // withdraw
	
	
	// -----------------프사 조회
	@Override
	public UserImageVO getUserImage(String userID) throws Exception{
		log.debug("getUserImage({}) invoked.", userID);
		
		UserImageVO vo = mapper.selectUserImgByUserID(userID);
		
		assert(vo != null);
		log.info("vo : {}", vo);
		
		return vo;
	} //getUserImage
	
	
	
	// ----------------프사 수정
	@Override
	public void modifyUserImage(UserImageDTO dto, HttpSession session) throws Exception{
		log.debug("modifyUserImage({}) invoked.", dto);
	
		UserVO user = (UserVO)session.getAttribute(UserLoginController.loginKey);
		String userID = user.getUserID();
		
		// 이전에 등록된 유저 이미지 반환
		UserImageVO image = mapper.selectUserImgByUserID(userID);
		
		// 이미지 수정을 위한 vo 생성
		UserImageVO vo = new UserImageVO(
				dto.getUuid()
				,dto.getUploadPath()
				,dto.getFileName()
				,userID
				);

		// 이전에 등록된 이미지가 없다면, insert 진행
		if (image == null) {
			this.mapper.insertUserImage(vo);
		
		// 이전에 등록된 이미지가 있다면, update 진행
		} else {
		
			this.mapper.updateUserImage(vo);
		} //if-else
	} //modifyUserImage
	
	
	// ----------------프사 삭제(db)
	@Override
	public void removeUserImage(String fileName, String uuid) throws Exception{
		log.debug("removeUserImage({}, {}) invoked.", fileName, uuid);

		File file;
		
		try {
			// 1. 원본파일 삭제
			file = new File("c:/Temp/upload/" + URLDecoder.decode(fileName, "utf8"));			
			
			file.delete();
			
			// 2. 썸네일용 파일 삭제
			String largeFileName = file.getAbsolutePath().replace("s_", "");
			
			log.info("largeFileName : " + largeFileName);
			
			file = new File(largeFileName);
			
			file.delete();

			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();

		} //try-catch
		
		// 3. DB삭제
		this.mapper.deleteUserImage(uuid);
		
	} //removeUserImage
	

} // end class

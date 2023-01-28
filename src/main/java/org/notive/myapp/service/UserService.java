package org.notive.myapp.service;

import java.util.Date;

import javax.servlet.http.HttpSession;

import org.notive.myapp.domain.UserDTO;
import org.notive.myapp.domain.UserImageDTO;
import org.notive.myapp.domain.UserImageVO;
import org.notive.myapp.domain.UserVO;


public interface UserService {

	//--------------회원가입 관련
	// 회원가입 처리
	public abstract void signUp(UserDTO dto, UserImageDTO imgDto) throws Exception;
	
	// 아이디 중복확인
	public abstract int checkID(String userID) throws Exception;
	
	// 회원가입 인증메일 발송
	public abstract String sendAuthMail(String userID) throws Exception;
	
	// 인증번호 업데이트
	public abstract void updateAuthKey(UserDTO dto) throws Exception;
	
	// 인증상태 업데이트
	public abstract void updateAuthStatus(UserDTO dto) throws Exception;

	
	//---------------로그인 관련
	// 로그인
	public abstract UserVO login(UserDTO dto) throws Exception;
	
	// remember-me 사용여부 업데이트 
	public abstract int modifyUserWithRememberMe(
			String userID, String rememberMe, Date rememberMeMaxAge) throws Exception;
	
	// remember-me 로그인 유지
	public abstract UserVO findUserByRememberMe(String rememberMe) throws Exception;
	
	// 로그아웃 (rememberme->null로 변경)
	public abstract void modifyUserWithoutRememberMe(String userID) throws Exception;
	
	
	//------------마이페이지 관련
	// 회원등급 조회(등급코드 테이블 join)
	public abstract String showUserGrade(String userID) throws Exception;
	
	// 비밀번호 변경
	public abstract void modifyUserPass(HttpSession session, UserDTO dto) throws Exception;

	// 이름 변경
	public abstract UserVO modifyUserName(HttpSession session, String userName) throws Exception;
	
	// 생일 변경
	public abstract UserVO modifyUserBday(HttpSession session, String year, String month, String day) throws Exception;
	
	// 아이디 찾기
	public abstract String findUserID(UserDTO dto) throws Exception;
	
	// 비밀번호 찾기(임시비번 발급)
	public abstract void findUserPass(UserDTO dto) throws Exception;
	
	// 회원탈퇴
	public abstract void withdraw(String userID) throws Exception;
	
	
	//--------------프로필이미지 관련
	// 이미지 조회
	public abstract UserImageVO getUserImage(String userID) throws Exception;
	
	// 마이페이지에서 이미지 수정(db등록)
	public abstract void modifyUserImage(UserImageDTO dto, HttpSession session) throws Exception;
	
	// 마이페이지에서 이미지 삭제(db)
	public abstract void removeUserImage(String fileName, String uuid) throws Exception;
	
} //end interface

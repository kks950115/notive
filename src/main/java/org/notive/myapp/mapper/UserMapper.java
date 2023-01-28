package org.notive.myapp.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.notive.myapp.domain.ModifyVO;
import org.notive.myapp.domain.UserDTO;
import org.notive.myapp.domain.UserImageVO;
import org.notive.myapp.domain.UserVO;

public interface UserMapper {
	
	//-------------- 회원가입 관련
	public abstract void createUser(UserVO userVO);	//회원가입
	
	public abstract int checkID(String userID); //아이디 중복 검사

	public abstract void updateAuthKey(Map<String,String> map);	//인증키 업데이트
	
	public abstract void updateAuthStatus(Map<String,String> map); //인증상태 업데이트
	
	
	//-------------- 로그인/로그아웃 관련
	public abstract UserVO login(UserDTO dto); 	//로그인
	
	// 사용자 테이블에 remember-me와 관련된 2개 컬럼 업데이트
	public abstract int updateUserWithRememberMe(
			@Param("userID")String userID, @Param("rememberMe")String rememberMe, @Param("rememberMeMaxAge")Date rememberMeMaxAge);
		
	// rememberMe 값으로 조회해서, userVO만들어 반환
	public abstract UserVO selectUserWithRememberMe(String rememberMe);
	
	// 로그아웃 (remember-me관련된 2개 컬럼 null로 업데이트)
	public abstract int updateUserWithoutRememberMe(String userID);
	
	
	//-------------- 마이페이지 관련 (정보수정/탈퇴)
	public abstract void selectmyPage(String userID);	//마이페이지 조회
	
	public abstract String selectUserGrade(String userID);	//회원등급 조회
	
	public abstract void updateUserPass(ModifyVO modifyVO);	// 비밀번호 수정 (마이페이지>비번수정 & 비밀번호찾기>임시비번 발급)
	
	public abstract void updateUserName(ModifyVO modifyVO); // 이름 수정
	
	public abstract UserVO selectUserAfterModify(String userID); // 이름으로 회원 조회
	
	public abstract void updateUserBday(ModifyVO modifyVO); // 생일 수정

	public abstract String selectUserID(UserDTO dto);	//아이디 찾기
	
	public abstract void updateUserState(String userID);	//회원탈퇴
	
	
	//--------------- 프로필 이미지 관련
	public abstract void insertUserImage(UserImageVO imgVO) ;	// 프사 등록(회원가입시)
	
	public abstract void deleteUserImage(String uuid);	//프사 삭제
	
	public abstract void updateUserImage(UserImageVO imgVO); // 프사 수정(로그인 후)
	
	public abstract UserImageVO selectUserImgByUserID(String userID); // 유저 아이디로 프사 조회
	
	
} //end interface

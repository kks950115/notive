package org.notive.myapp.domain;

import java.util.Date;

import lombok.Value;

@Value
public class UserListVO {
	
	private String  userID ;				// ID
	private String  userName ;				// 이름
	private Date    userBday ;				// 생년월일
	private Date    userSignupDate ;		// 가입날짜
	private String userGrade ;				// 등급
	private String userState ;				// 상태
	
	
//	
//	private String  userId ;				// ID
//	private String  userPw ;				// 비밀번호
//	private String  userName ;				// 이름
//	private Date    userBday ;				// 생년월일
//	private Date    userSignupDate ;		// 가입날짜
//	private String  userImg ;				// 사진
//	private Integer userGrade ;				// 등급
//	private Integer userState ;				// 상태
//	
//	private String userMgtNo ;				// 관리내역코드
//	private String adminId ;				// 관리자 아이디
//	private Integer userMgtCode ;			// 관리내역 코드
//	private Date userMgtdate ;				// 관리일자

} // end class

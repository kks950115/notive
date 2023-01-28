package org.notive.myapp.domain;

import java.util.Date;

import lombok.Value;

@Value
public class UserGetVO {
	
	private String  userID ;				// ID
	private String  userName ;				// 이름
	private Date    userBday ;				// 생년월일
	private Date    userSignupDate ;		// 가입날짜
	private String userGrade ;				// 등급
	private String userState ;				// 상태
//	private String adminID;					// 관리자 아이디
//	private String userMgtCmt ;				// 관리메모
//	private Date userMgtDate ;				// 관리일자

}// end class

package org.notive.myapp.domain;

import java.util.List;

import lombok.Data;


@Data
public class UserDTO {

	private String userID;
	private String userPass;
	private String userPassCur;		//현재 비밀번호
	private String userPassConfirm;	//비밀번호 재입력란
	private String userName;
	
	private String year;
	private String month;
	private String day;
	
	private String userAuthKey;		//이메일 인증키
	
	private boolean rememberMe;
	
	private List<UserImageVO> userImage;
} //end class

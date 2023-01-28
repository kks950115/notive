package org.notive.myapp.domain;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import lombok.Value;


@Value

public class UserVO {

	private String userID;
	private String userPass;	
	private String userName;	
	
	@DateTimeFormat(pattern="yyyy/MM/dd")
	private Date userBday;
	private Date userSignupDate;	
	private MultipartFile userImage;	
	private Integer userGrade;
	private Integer userState;
	
	private String userAuthKey;
	private String userAuthStatus;
	
} //end class
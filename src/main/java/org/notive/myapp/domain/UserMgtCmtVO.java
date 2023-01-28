package org.notive.myapp.domain;

import java.util.Date;

import lombok.Value;

@Value
public class UserMgtCmtVO {
	
	private String userID ;
	private String adminID;
	private String userMgtCode;
	private String userMgtCmt;
	private Date userMgtDate;

} // end class

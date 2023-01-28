package org.notive.myapp.domain;

import java.util.Date;

import lombok.Data;

@Data
public class UserMgtCmtDTO {
	
	private String userID;
	private String userMgtCode;
	private String userMgtCmt;
	private Date userMgtDate ;

} // end class

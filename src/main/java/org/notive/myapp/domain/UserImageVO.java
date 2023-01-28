package org.notive.myapp.domain;

import lombok.Value;


@Value
public class UserImageVO {

	private String uuid;
	private String uploadPath;
	private String fileName;
	
	private String userID;
	
} //end class

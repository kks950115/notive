package org.notive.myapp.domain;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import lombok.Value;


@Value
public class ModifyVO {

	private String userID;
	private String userPass;	
	private String userName;	
	
	@DateTimeFormat(pattern="yyyy/MM/dd")
	private Date userBday;	
	private MultipartFile userImage;		
} //end class

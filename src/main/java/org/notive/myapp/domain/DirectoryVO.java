package org.notive.myapp.domain;

import java.util.Date;

import lombok.Value;


@Value
public class DirectoryVO {

	private Integer dirNo;
	private String dirName;
	private Integer dirTopNo;
	private Integer dirLevel;
	private Date dirCreateDate;
	private Date dirDelDate;
	private Integer groupNo;
	private String userId;
	private Integer dirState;
	private Integer dirGroup;
	
	
	
	
	
}//end class


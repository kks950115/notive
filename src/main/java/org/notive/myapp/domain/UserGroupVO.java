package org.notive.myapp.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;

@Value
public class UserGroupVO {
	
	private Integer group_no;
	private String user_id;
	private String user_group_name;
	
} // end class

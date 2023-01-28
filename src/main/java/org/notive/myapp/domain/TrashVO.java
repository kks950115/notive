package org.notive.myapp.domain;

import java.util.Date;

import lombok.Value;

@Value
public class TrashVO {

	private Integer trash_no;
	private String user_id;
	private Integer sort_code;
	private Integer origin_no;
	private String origin_name;
	private Date delete_date;
	
}

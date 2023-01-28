package org.notive.myapp.domain;

import java.util.Date;

import lombok.Data;

@Data
public class MemoDTO {
	
	
	private Integer memoNo;
	private String memoTitle;
	private String editordata;
	private Date memoWriteDate;
	private Date memoUpdateDate;
	

	private Integer dirNo;
	private Integer groupNo;
	private String userId;	
	private Integer memoState;			//활성여부
	private Integer memoGroup;			//그룹여부
	
	
}//end class


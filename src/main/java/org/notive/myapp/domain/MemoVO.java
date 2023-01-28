package org.notive.myapp.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Value;


@Value
public class MemoVO {

	private Integer memoNo;
	private String memoTitle;
	private String memoContent;
	
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
	private Date memoWriteDate;
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
	private Date memoUpdateDate;
	

	private Integer dirNo;
	private Integer groupNo;
	private String userId;	
	private Integer memoState;			//활성여부
	private Integer memoGroup;			//그룹여부
	
}//end class

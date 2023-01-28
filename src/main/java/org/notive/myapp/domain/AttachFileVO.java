package org.notive.myapp.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Value;

@Value
public class AttachFileVO {

	private Integer fileNo;			//파일번호
	private String fileName;		//원본파일 이름
	private String fileUuid;		//UUID값	  //uuid
	private String filePath;		//업로드경로  //uploadPath
	private Character fileType;		//파일타입: 이미지여부, default 'N'
	private Integer fileSize;		//파일크기

	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
	private Date fileUploadDate;	//업로드날짜
	private Date fileDeleteDate;	//삭제날짜
	
	private Integer dirNo;			//폴더번호
	private Integer groupNo;		//그룹번호
	private String userId;			//아이디
	private Integer fileState;		//파일상태(활성,비활성)
	private Integer fileGroup;		//그룹여부
	
}//end class

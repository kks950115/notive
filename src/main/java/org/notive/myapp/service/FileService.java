package org.notive.myapp.service;

import java.util.List;

import org.notive.myapp.domain.AttachFileVO;

public interface FileService {

	public abstract AttachFileVO getFileVO(Integer fileNo, String userId);
	public abstract List<AttachFileVO> getlistAll(String userId, Integer state);			//해당폴더의 파일목록 가져오기
	public abstract List<AttachFileVO> getlist(Integer dirNo, String userId, Integer state);			//해당폴더의 파일목록 가져오기
	public abstract boolean register(AttachFileVO file);	//새 파일 등록하기
	public abstract boolean remove(AttachFileVO fileVO);			//파일 삭제하기
	public abstract List<AttachFileVO> getSearchList(String userId, String search);		//파일 검색
	
}//end FileService

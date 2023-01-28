package org.notive.myapp.service;

import java.util.List;

import org.notive.myapp.domain.MemoVO;

public interface MemoService {

	public abstract List<MemoVO> getlistAll(String userId, Integer state);					//전체목록 가져오기
	public abstract List<MemoVO> getlist(Integer dirNo, String userId, Integer state);					//전체목록 가져오기
	public abstract MemoVO get(Integer memoNo, String userId);			//메모 상세보기 -> 수정폼으로 이동
	public abstract boolean register(MemoVO memo);										//새 메모 등록하기
	public abstract boolean remove(MemoVO memoVO);		//메모 삭제하기
	public abstract boolean modify(MemoVO memo);										//메모 수정하기
	
	
	public abstract List<MemoVO> getSearchList(String userId, String search);				//검색 목록 가져오기
	
	
}//end interface

package org.notive.myapp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.notive.myapp.domain.MemoVO;

public interface MemoMapper {

	public abstract List<MemoVO> selectAllOfUser(@Param("userId")String userId, @Param("state")Integer state);	// 유저의 모든 메모 리스트
	public abstract List<MemoVO> selectAll(@Param("dirNo")Integer dirNo, @Param("userId")String userId, @Param("state")Integer state);	// 폴더의 메모리스트
	public abstract MemoVO selectMemoVO(@Param("memoNo")Integer memoNo, @Param("userId")String userId);		 		//메모
	public abstract Integer insert(MemoVO memo);												//새로운 메모 추가
	public abstract Integer insertSelectKey(MemoVO memo);										//새로운 메모 추가(번호)
	public abstract Integer update(MemoVO memo);												//메모 수정
	public abstract Integer changeState(@Param("state")Integer state, @Param("memoNo")Integer memoNo, @Param("userId")String userId);				//메모 삭제
	public abstract Integer deleteMemo(Integer MemoNo);		//메모 영구삭제
	
	public abstract List<MemoVO> selectSearchList(@Param("userId")String userId, @Param("search")String search);		//메모 검색
	
}//end interface

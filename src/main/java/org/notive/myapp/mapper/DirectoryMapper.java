package org.notive.myapp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.notive.myapp.domain.DirectoryVO;

public interface DirectoryMapper {
	
	
	public abstract List<DirectoryVO> selectAll(@Param("userId")String userId, @Param("state")Integer state);					//폴더리스트
	public abstract DirectoryVO select(@Param("dirNo")Integer dirNo, @Param("userId")String userId);	 //하나의 폴더 상세정보 
	public abstract List<DirectoryVO> selectSub(@Param("dirTopNo")Integer dirTopNo, @Param("userId")String userId);	 //sub폴더 상세정보 
	public abstract Integer insert(DirectoryVO vo);					//폴더 추가
	public abstract Integer insertSelectKey(DirectoryVO vo);		//폴더 추가(번호)
	public abstract Integer ginsertSelectKey(DirectoryVO vo);		//그룹폴더 추가(번호)
	public abstract DirectoryVO selectDirNo(Integer groupNo);		//dirNo 조회하기
	public abstract Integer update(DirectoryVO vo);					//폴더 수정
	public abstract Integer changeState(@Param("state")Integer state, @Param("dirNo")Integer dirNo, @Param("userId")String userId);	 	//폴더 삭제
	public abstract Integer deleteDirectory(Integer dirNo);			//영구삭제
	

}//end interface

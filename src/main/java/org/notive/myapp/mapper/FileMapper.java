package org.notive.myapp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.notive.myapp.domain.AttachFileVO;

public interface FileMapper {

	
	public abstract Integer insert(AttachFileVO vo);					//파일추가
	public abstract Integer insertSelectKey(AttachFileVO vo);			//파일번호생성,파일추가
	public abstract Integer changeState(@Param("state")Integer state, @Param("fileNo")Integer fileNo, @Param("userId")String userId);						//파일삭제
	public abstract List<AttachFileVO> selectAllFileOfUser(@Param("userId")String userId, @Param("state")Integer state);		// 모든 파일조회
	public abstract List<AttachFileVO> selectAll(@Param("dirNo")Integer dirNo, @Param("userId")String userId, @Param("state")Integer state);		//특정 폴더 번호로 파일 조회
	public abstract AttachFileVO selectFileVO(@Param("fileNo")Integer fileNo, @Param("userId")String userId);
	public abstract Integer deleteFile(Integer fileNo);		//메모 영구삭제
	public abstract List<AttachFileVO> selectSearchList(@Param("userId")String userId, @Param("search")String search);	//파일 검색
	
	
}//end interface

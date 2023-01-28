package org.notive.myapp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.notive.myapp.domain.InvitationVO;

public interface InvitationMapper {

	abstract List<InvitationVO> selectInvitationList(String user_id);				// 초대 목록
	abstract Integer selectCountUserInvitation(@Param("group_no") Integer group_no, @Param("user_id") String user_id); 	// 그룹에 초대된 유저 숫자
	
	abstract Integer insertInvitation(InvitationVO invitation);						// 초대 추가
	abstract Integer insertInvitationSelectKey(InvitationVO invitation);			// 초대 만들기
	abstract Integer deleteInvitation(String group_name);							// 초대 삭제
	
} // end interface

package org.notive.myapp.service;

import java.util.List;

import org.notive.myapp.domain.InvitationVO;

public interface InvitationService {
	
	public abstract List<InvitationVO> getInvitationList(String user_id);	// 초대목록 가지고 오기
	public abstract Integer checkUserInvitation(Integer group_no, String user_id); // 
	
	public abstract Integer createInvitation(InvitationVO invitation);		// 초대 만들기
	public abstract Integer removeInvitation(String group_name);		// 초대 삭제

} // end interface

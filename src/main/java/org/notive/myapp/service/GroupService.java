package org.notive.myapp.service;

import java.util.List;

import org.notive.myapp.domain.GroupVO;
import org.notive.myapp.domain.UserGroupVO;

public interface GroupService {
	public String getGroupName(Integer group_no); 		// 그룹이름 얻기
	public abstract List<UserGroupVO> getUserGrouplist(String user_id);			// 회원 그룹목록 조회	후에 로그인정보에서 아이디 추출해서 사용
	public abstract List<String> getUserListInGroup(Integer group_no);			// 그룹에 속한 유저 목록
	public abstract Integer checkUserInGroup(Integer group_no, String user_id);	// 특정 그룹에 속한 아이디가 맞는지 조회
	
	public abstract Integer createGroup(String user_id, String group_name);	// 그룹 생성
	
	public abstract Integer joinGroup(UserGroupVO userGroup);				// 그룹 참가
	
	public abstract Integer modifyUserGroupName(UserGroupVO userGroup);		// 회원 그룹명 변경
	public abstract Integer removeUserGroup(UserGroupVO userGroup);			// 회원 그룹 삭제
	
} // end interface

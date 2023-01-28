package org.notive.myapp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.notive.myapp.domain.GroupVO;
import org.notive.myapp.domain.UserGroupVO;

public interface GroupMapper {
	
	public abstract String selectGroupName(Integer group_no);			// 그룹 이름 조회
	// 삭제 대기 필요 없을 것 같음
	public abstract List<GroupVO> selectUserGroupList(String user_id);	// 회원의 그룹목록 조회
	// 이걸 사용해야 함
	public abstract List<String> selectUserListInGroup(Integer group_no);	// 그룹의 회원 목록 조회
	public abstract List<UserGroupVO> selectUserGroupNList(String user_id);	// 회원의 그룹목록 조회
	
	public abstract Integer selectCountExistUserInGroup(				// 회원이 그룹 멤버인지 조회
													@Param("group_no")Integer group_no, 
													@Param("user_id") String user_id);	
	
	
	public abstract Integer insertGroup(String group_name);				// 새로운 그룹 생성
	public abstract Integer insertGroupSelectKey(GroupVO group);		// 새로운 그룹 생성 + 그룹 고유번호 리턴
	
	public abstract Integer insertUserGroup(UserGroupVO userGroup);		// 그룹 생성or참가 할 때 사용
	public abstract Integer updateUserGroup(UserGroupVO userGroup);		// 개인 그룹명 변경
	public abstract Integer deleteUserGroup(UserGroupVO userGroup);		// 개인 그룹 삭제
	
	public abstract void deleteGroup();		// 그룹 삭제


} // end interface

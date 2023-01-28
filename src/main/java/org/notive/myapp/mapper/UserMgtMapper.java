package org.notive.myapp.mapper;

import java.util.List;

import org.notive.myapp.domain.Criteria;
import org.notive.myapp.domain.UserGetVO;
import org.notive.myapp.domain.UserListVO;
import org.notive.myapp.domain.UserMgtCmtVO;

public interface UserMgtMapper {
	
	public abstract List<UserListVO> selectUserList(); 		// 회원 목록 조회
	public abstract List<UserListVO> selectUserListWithPaging(Criteria cri);
	public abstract List<UserListVO> selectUserListWithPagingSort(Criteria cri);
	
	public abstract UserGetVO selectUser (String userID) ; 	// 특정 회원 선택
	
	public abstract List<UserMgtCmtVO> selectReply (String userID) ; // 댓글 조회
	
	public abstract int mgtCmtInsert(UserMgtCmtVO usermgtCmtVO);
	
	public abstract int selectTotalCount(Criteria cri);
	
	public abstract int userStateUpdate(UserGetVO userGetVO);
	
	
	
	

} // interface

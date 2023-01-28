package org.notive.myapp.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.notive.myapp.domain.Criteria;
import org.notive.myapp.domain.UserGetVO;
import org.notive.myapp.domain.UserListVO;
import org.notive.myapp.domain.UserMgtCmtDTO;
import org.notive.myapp.domain.UserMgtCmtVO;

public interface UserMgtService {
	
	public abstract List<UserListVO> getUserList() ;		// 회원 목록 조회
	
	public abstract List<UserListVO> getUserListPerPage(Criteria cri);
	public abstract List<UserListVO> getUserListPerPageSort(Criteria cri);
	
//	public abstract UserGetVO get(String userID) ;
//	
//	public abstract List<UserMgtCmtVO> getReply (String userID) ;	 // 댓글 조회
	
	public abstract UserGetVO userGet(String userID) ;		//상세정보 조회
	public abstract List<UserMgtCmtVO> userCmtGet(String userID) ;		//상세정보 조회
	
	public abstract boolean mgtCmtRegister (UserMgtCmtVO userMgtCmtVO) ;		// 관리내역 등록

	public abstract int getTotal(Criteria cri);						// 총 회원목록
	
	public abstract boolean userStateModify (UserGetVO userGetVO);
	
	

} // interface

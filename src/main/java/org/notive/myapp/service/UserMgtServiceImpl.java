package org.notive.myapp.service;

import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpSession;

import org.notive.myapp.domain.AdminVO;
import org.notive.myapp.domain.Criteria;
import org.notive.myapp.domain.UserGetVO;
import org.notive.myapp.domain.UserListVO;
import org.notive.myapp.domain.UserMgtCmtDTO;
import org.notive.myapp.domain.UserMgtCmtVO;
import org.notive.myapp.mapper.UserMgtMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

@Log4j2
@AllArgsConstructor


@Service
public class UserMgtServiceImpl implements UserMgtService {
	
	


	@Setter(onMethod_=@Autowired)
	private UserMgtMapper userMapper ;
	
	public static final String adminLoginKey = "__ADMIN__";
	
	
	@Override
	public List<UserListVO> getUserList() {			// 회원 목록 조회
		log.debug("getList() invoked");
		
		Objects.requireNonNull(userMapper);		
		
		return userMapper.selectUserList();
		
	} // getList	
	
	
	@Override
	public List<UserListVO> getUserListPerPage(Criteria cri) {
		log.debug("getListPerPage({}) invoked", cri);
		
		Objects.requireNonNull(this.userMapper);
		
		return this.userMapper.selectUserListWithPaging(cri);
		
	} // userInfoGet
	
	
	@Override
	public List<UserListVO> getUserListPerPageSort(Criteria cri) {
		Objects.requireNonNull(this.userMapper);
		
		return this.userMapper.selectUserListWithPagingSort(cri);
	} // getTotal
	
	
	
//	@Override
//	public List<UserMgtCmtVO> getReply() {
//		log.debug("getReply(userID) invoked");
//		
//		Objects.requireNonNull(userMapper);
//		
//		return userMapper.getReply();
//	}
//	
//	
//	@Override
//	public UserGetVO get (String userID) {
//		
//		Objects.requireNonNull(this.userMapper);
//		
//		return this.userMapper.get(userID);
//		
//	}

	
	@Override
	public UserGetVO userGet(String userID) {
		log.debug("userGet({}) invoked", userID);		
		
		UserGetVO userGetVO = this.userMapper.selectUser(userID);
		
		return this.userMapper.selectUser(userID);
	}
	
	@Override
	public List<UserMgtCmtVO> userCmtGet(String userID) {
		log.debug("userCmtGet({}) invoked", userID);
		List<UserMgtCmtVO> userMgtCmtVO = this.userMapper.selectReply(userID);
		
		return this.userMapper.selectReply(userID);
		
	}
	

	@Override
	public boolean mgtCmtRegister(UserMgtCmtVO userMgtCmtVO) {
	
		log.debug("register(usermgtCmtVO) invoked"); 
		
		Objects.requireNonNull(this.userMapper);
		
		return this.userMapper.mgtCmtInsert(userMgtCmtVO) == 1 ;
		
	}


	@Override
	public int getTotal(Criteria cri) {
		
		Objects.requireNonNull(this.userMapper);
		
		return this.userMapper.selectTotalCount(cri);
		
	}


	@Override
	public boolean userStateModify(UserGetVO userGetVO) {
		log.debug("modify(usermgtCmtVO) invoked");
		
		Objects.requireNonNull(this.userMapper);
		
		return (this.userMapper.userStateUpdate(userGetVO) == 1);
	}


	
	
	
	




	
	
	
	
	

} // end class

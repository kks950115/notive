package org.notive.myapp.service;

import java.util.Date;
import java.util.List;

import org.notive.myapp.domain.ChatDTO;
import org.notive.myapp.domain.ChatVO;
import org.notive.myapp.domain.GroupVO;
import org.notive.myapp.domain.UserGroupVO;
import org.notive.myapp.mapper.ChatMapper;
import org.notive.myapp.mapper.GroupMapper;
import org.notive.myapp.mapper.InvitationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

@NoArgsConstructor
@Log4j2

@Service
public class GroupServiceImpl implements GroupService {
	
	@Setter(onMethod_= {@Autowired})
	private GroupMapper groupMapper;
	@Setter(onMethod_= {@Autowired})
	private InvitationMapper invitationMapper;
	@Setter(onMethod_= {@Autowired})
	private ChatMapper chatMapper;
	
	

	// 그룹의 이름 가져오기
	@Override
	public String getGroupName(Integer group_no) {
		log.debug("getGroupName({}) invoked.", group_no);
		
		return this.groupMapper.selectGroupName(group_no);
	} // getGroupName

	// 회원의 그룹 목록 가져오기
	@Override
	public List<UserGroupVO> getUserGrouplist(String user_id) {
		log.debug("getGrouplist({}) invoked.", user_id);
		
		return this.groupMapper.selectUserGroupNList(user_id);
	}

	// 그룹생성
	// 사용자에게 입력받으면 그 이름으로 생성
	// --1. tbl_group에 insert 
	// --2. tbl_user_group에 insert
	// --3. tbl_chat에 insert
	// --4. tbl_chat_unread_management에 insert
	// GroupMapper의 세 개의 메소드가 모두 실행되어야 아래 이 메소드가 완전히 수행된다. @Transactional 적용
	@Transactional
	@Override
	public Integer createGroup(String user_id, String group_name) {
		log.debug("createGroup({}, {}) invoked.", user_id, group_name);
		
		// GroupVO 생성
		GroupVO group = new GroupVO(null, group_name);
		
		// 그룹 생성하고
		this.groupMapper.insertGroupSelectKey(group);
		
		log.info("\t+ group_no : {} ", group.getGroup_no());
		
		// userGroupVO 생성
		UserGroupVO userGroup = new UserGroupVO(group.getGroup_no(), user_id, group.getGroup_name());
		
		// userGroup insert
		this.groupMapper.insertUserGroup(userGroup);
		
		// 채팅방 생성 => 그룹 생성시 자동생성
		this.chatMapper.insertChat(new ChatDTO(group.getGroup_no(), group.getGroup_name(), "채팅방이 생성되었습니다.", new Date(), 0));
		
		// 그룹의 모든 채팅번호 가져오기
		List<ChatVO> chatList = this.chatMapper.selectChatListInGroup(group.getGroup_no());
		// 마지막 채팅번호 반환
		Integer lastestChatNo = chatList.get(chatList.size()-1).getChat_no();
		
		// 채팅방 읽음 관리 테이블에 기본값 생성
		this.chatMapper.insertUserReadNo(group.getGroup_no(), user_id, lastestChatNo);
		
		return group.getGroup_no();
	}

	// 그룹참가
	// 이미 생성된 그룹에 참여
	// --1. 초대목록에서 삭제
	// --2. 채팅방 읽음 관리 테이블에 유저 추가
	// --3. 유저그룹 추가
	@Transactional
	@Override
	public Integer joinGroup(UserGroupVO userGroup) {
		log.debug("joinGroup({}) invoked.", userGroup);
		
		// --1. 초대목록에서 삭제
		this.invitationMapper.deleteInvitation(userGroup.getUser_group_name());
		
		// --2. 채팅방 읽음관리 테이블에 신규유저 추가
		
		// 그룹의 모든 채팅번호 가져오기
		List<ChatVO> chatList = this.chatMapper.selectChatListInGroup(userGroup.getGroup_no());
		// 마지막 채팅번호 반환
		Integer lastestChatNo = chatList.get(chatList.size()-1).getChat_no();
		// 유저가 마지막 읽은 채팅을 현재 그룹의 가장 최신 채팅번호로 설정
		this.chatMapper.insertUserReadNo(userGroup.getGroup_no(), userGroup.getUser_id(), lastestChatNo);
		
		// --3. 유저그룹 추가
		// userGroup insert
		return this.groupMapper.insertUserGroup(userGroup);
	}

	// 그룹명 변경
	@Override
	public Integer modifyUserGroupName(UserGroupVO userGroup) {
		log.debug("changeUserGroupName({}) invoked.", userGroup);
		
		return this.groupMapper.updateUserGroup(userGroup);
	}

	// 유저그룹 삭제
	// --1. 유저그룹 삭제
	// --2. 그룹에 소속회원 없으면 그룹삭제
	@Transactional
	@Override
	public Integer removeUserGroup(UserGroupVO userGroup) {
		log.debug("removeUserGroup({}) invoked.", userGroup);
		
		// 유저그룹 삭제
		int result = this.groupMapper.deleteUserGroup(userGroup);
		
		// 그룹 소속회원 있는지 검사하고 없으면 삭제
		this.groupMapper.deleteGroup();
		
		return result;
	}

	// 그룹에 속한 아이디인지 조회
	@Override
	public Integer checkUserInGroup(Integer group_no, String user_id) {
		log.debug("checkUserInGroup({}, {}) invoked.", group_no, user_id);
		
		return this.groupMapper.selectCountExistUserInGroup(group_no, user_id);
	}

	// 하나의 그룹에 속한 유저 리스트 조회
	@Override
	public List<String> getUserListInGroup(Integer group_no) {
		log.debug("getUserListInGroup({}) invoked.", group_no);
		
		return this.groupMapper.selectUserListInGroup(group_no);
	}



} // end class

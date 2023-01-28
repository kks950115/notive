package org.notive.myapp.service;

import java.util.List;

import org.notive.myapp.domain.ChatDTO;
import org.notive.myapp.domain.ChatVO;

public interface ChatService {

	public abstract List<ChatVO> getAllChatList(String user_id);	// 모든 채팅 리스트
	public abstract List<ChatVO> getChatListInGroup(Integer room_no);	// 하나의 채팅방의 채팅 리스트
	public abstract Integer[] getChatRommList(String user_id);	// 모든 채팅방 리스트
	public abstract Integer getCountUnreadChat(Integer room_no, Integer chat_no);	// 읽지 않은 채팅 갯수
	
	public abstract Integer modifyUnReadCount(Integer room_no, Integer chat_lastread_no);	// 읽은 채팅 부분 unread_count -1
	public abstract Integer createChat(ChatDTO chatDTO);	// 채팅 추가
	
	//================================ chat_unread_management ===================================//

	public abstract Integer getLastReadNo (Integer room_no, String user_id);	// 채팅방 유저의 마지막 읽은 채팅번호 반환
	
	public abstract Integer modifyLastReadNo (Integer room_no, String user_id, Integer chat_lastread_no); // 마지막 읽은 채팅번호 업데이트
	public abstract Integer createUserReadNo (Integer room_no, String user_id, Integer chat_lastread_no); // 그룹에 유저 가입시 테이블에 추가
	public abstract Integer deleteUserReadNo (Integer room_no, String user_id); // 그룹에서 유저 탈퇴시 테이블에서 삭제
	
}

package org.notive.myapp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.notive.myapp.domain.ChatDTO;
import org.notive.myapp.domain.ChatVO;

public interface ChatMapper {
	
	public abstract List<ChatVO> selectAllChatList(String user_id);		// 모든 채팅 리스트 
	public abstract Integer[] selectChatRoomList(String user_id);		// 모든 채팅방 리스트 
	public abstract List<ChatVO> selectChatListInGroup(Integer room_no);	// 하나의 채팅방의 모든 채팅리스트
	public abstract Integer selectCountUnreadChatFromLastRead(@Param("room_no") Integer room_no, @Param("chat_no") Integer chat_no); // 읽지 않은 채팅의 갯수
	
	public abstract Integer updateUnReadCount (@Param("room_no") Integer room_no, @Param("chat_lastread_no") Integer chat_lastread_no);	// 채팅 테이블의 유저가 읽은 채팅들 count -1
	public abstract Integer insertChat(ChatDTO chatDTO);				// 채팅 DB 업데이트
	
	//================================ chat_unread_management ===================================//
	
	public abstract Integer selectLastReadNo (@Param("room_no") Integer room_no, @Param("user_id") String user_id);	// 마지막 읽은 채팅번호 찾기
	
	public abstract Integer updateLastReadNo (@Param("room_no") Integer room_no, @Param("user_id") String user_id, @Param("chat_lastread_no") Integer chat_lastread_no);	// 마지막 읽은 채팅번호 업데이트
	public abstract Integer insertUserReadNo (@Param("room_no") Integer room_no, @Param("user_id") String user_id, @Param("chat_lastread_no") Integer chat_lastread_no);	// 채팅방 유저 가입시
	public abstract Integer deleteUserReadNo (@Param("room_no") Integer room_no, @Param("user_id") String user_id);	// 채팅방 유저 탈퇴시

}

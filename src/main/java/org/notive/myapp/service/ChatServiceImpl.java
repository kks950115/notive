package org.notive.myapp.service;

import java.util.List;

import org.notive.myapp.domain.ChatDTO;
import org.notive.myapp.domain.ChatVO;
import org.notive.myapp.mapper.ChatMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

@NoArgsConstructor
@Log4j2

@Service
public class ChatServiceImpl implements ChatService {
	
	@Setter(onMethod_ = @Autowired)
	private ChatMapper chatMapper;

	@Override
	public List<ChatVO> getAllChatList(String user_id) {
		
		return this.chatMapper.selectAllChatList(user_id);
	}

	@Override
	public Integer[] getChatRommList(String user_id) {
		
		return this.chatMapper.selectChatRoomList(user_id);
	}

	@Override
	public List<ChatVO> getChatListInGroup(Integer room_no) {

		return this.chatMapper.selectChatListInGroup(room_no);
	}
	
	@Override
	public Integer getCountUnreadChat(Integer room_no, Integer chat_no) {
		
		return this.chatMapper.selectCountUnreadChatFromLastRead(room_no, chat_no);
	}
	
	@Override
	public Integer modifyUnReadCount(Integer room_no, Integer chat_lastread_no) {
		
		return this.chatMapper.updateUnReadCount(room_no, chat_lastread_no);
	}
	
	@Override
	public Integer createChat(ChatDTO chatDTO) {
		
		return this.chatMapper.insertChat(chatDTO);
	}

	@Override
	public Integer getLastReadNo(Integer room_no, String user_id) {
		
		return this.chatMapper.selectLastReadNo(room_no, user_id);
	}

	@Override
	public Integer modifyLastReadNo(Integer room_no, String user_id, Integer chat_lastread_no) {
		
		return this.chatMapper.updateLastReadNo(room_no, user_id, chat_lastread_no);
	}

	@Override
	public Integer createUserReadNo(Integer room_no, String user_id, Integer chat_lastread_no) {
		
		return this.chatMapper.insertUserReadNo(room_no, user_id, chat_lastread_no);
	}

	@Override
	public Integer deleteUserReadNo(Integer room_no, String user_id) {
		
		return this.chatMapper.deleteUserReadNo(room_no, user_id);
	}





}

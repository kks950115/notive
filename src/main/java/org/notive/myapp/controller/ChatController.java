package org.notive.myapp.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.notive.myapp.domain.ChatVO;
import org.notive.myapp.domain.UserGroupVO;
import org.notive.myapp.service.ChatService;
import org.notive.myapp.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.JsonIOException;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

@NoArgsConstructor
@Log4j2

@Controller
public class ChatController {
	
	@Setter(onMethod_ = @Autowired)
	private ChatService chatService;
    
    @Autowired
    private GroupService groupService;
	
//	@GetMapping("/chat")
//	public void chat(Model model) {
//		
//	} // chat
	
    
    // 채팅방 입장
    // 채팅목록 불러오기
	@ResponseBody
    @RequestMapping(value="{room_no}/message")
    public List<ChatVO> chatList(@PathVariable Integer room_no, String user_id) throws JsonIOException, IOException {

		log.info(room_no);
		// 해당 채팅방의 모든 채팅 리스트
		List<ChatVO> chatList = this.chatService.getChatListInGroup(room_no);
		
		// 모든 채팅리스트 중 가장 최신의 채팅 번호 
		Integer lastestChatNo = chatList.get(chatList.size()-1).getChat_no();
		log.info("최신채팅" + lastestChatNo);
		
		// 유저가 읽은 마지막 채팅 번호
		Integer lastReadNo = this.chatService.getLastReadNo(room_no, user_id);
		log.info("마지막채팅" + lastReadNo);
		
		// 마지막 읽은 채팅 != 최신 채팅
		if(lastReadNo != lastestChatNo) {
			
			// 마지막 읽은 채팅 이후 채팅부터 최신채팅까지 모든 unread_count -1 => 읽지 않은 부분 읽음 처리
			this.chatService.modifyUnReadCount(room_no, lastReadNo);

			
			// 유저가 읽은 마지막 채팅번호 업데이트 
			this.chatService.modifyLastReadNo(room_no, user_id, lastestChatNo);
			
		} // if
        
        return this.chatService.getChatListInGroup(room_no);
    }
	
	// 채팅방 리스트 불러오기
	@ResponseBody
	@GetMapping("/chatRoomList")
	public List<Map<String, Object>> chatRoomList(String user_id) {
		
		// 반환할 리스트
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		
		// 유저의 그룹 리스트
		List<UserGroupVO> userGroupList = this.groupService.getUserGrouplist(user_id);
		
		// 유저의 그룹 리스트의 정보를 바탕으로 Map 객체 생성
		// room_no, group_name, unread_count
		for(UserGroupVO e : userGroupList) {
			Map<String, Object> map = new HashMap<>();
			
			map.put("room_no", e.getGroup_no());
			map.put("group_name", e.getUser_group_name());
			
			// 유저가 읽은 마지막 채팅 번호
			Integer lastReadNo = this.chatService.getLastReadNo(e.getGroup_no(), user_id);
			
			// 유저가 읽지 않은 채팅의 갯수
			Integer unreadCount = this.chatService.getCountUnreadChat(e.getGroup_no(), lastReadNo);
			
			map.put("unread_count", unreadCount);
			
			// 리스트에 추가
			result.add(map);
		}
		
		log.info("roomList!!!" + result.toString());

		return result;
	}
	
	@ResponseBody
	@GetMapping("/chatSession/")
	public ArrayList<String> chatSession(){
		
		
		return null;
	}
	
	@ResponseBody
	@GetMapping("/modifyUnread")
	public Integer modifyUnread(Integer room_no, String user_id) {
        // 입력한 사용자의 마지막 읽은 채팅 번호 업데이트
		List<ChatVO> chatList = this.chatService.getChatListInGroup(room_no);
		
		Integer lastestChatNo = chatList.get(chatList.size()-1).getChat_no();
		
		return this.chatService.modifyLastReadNo(room_no, user_id, lastestChatNo);
	}
	
	// 읽지 않은 채팅 숫자 반환
	@ResponseBody
	@GetMapping("/chatUnreadCount")
	public Integer chatUnreadCount(Integer room_no, String user_id) {
		
		// 해당 채팅방의 모든 채팅 리스트
		List<ChatVO> chatList = this.chatService.getChatListInGroup(room_no);
		
		// 모든 채팅리스트 중 가장 최신의 읽지않음 수
		Integer lastestUnreadCount = chatList.get(chatList.size()-1).getChat_unread_count();
		log.info("읽지않음" + lastestUnreadCount);
		
		return lastestUnreadCount;
	}
	
	@ResponseBody
	@GetMapping("/chatUnreadReload")
	public List<Integer> chatUnreadReload(String user_id) {
		List<Integer> result = new ArrayList<>();
		
		// 유저의 그룹 리스트
		List<UserGroupVO> userGroupList = this.groupService.getUserGrouplist(user_id);
		
		for(UserGroupVO e : userGroupList) {
			
			// 유저가 읽은 마지막 채팅 번호
			Integer lastReadNo = this.chatService.getLastReadNo(e.getGroup_no(), user_id);
			
			// 유저가 읽지 않은 채팅의 갯수
			Integer unreadCount = this.chatService.getCountUnreadChat(e.getGroup_no(), lastReadNo);
			
			result.add(unreadCount);
		}
		
		return result;
	}
} // end class

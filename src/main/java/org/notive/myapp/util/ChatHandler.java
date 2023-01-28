package org.notive.myapp.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.notive.myapp.domain.ChatDTO;
import org.notive.myapp.domain.ChatVO;
import org.notive.myapp.service.ChatService;
import org.notive.myapp.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;


@NoArgsConstructor
@Log4j2
public class ChatHandler extends TextWebSocketHandler{
	
	
	
	ObjectMapper objectMapper = new ObjectMapper();
	
	@Setter(onMethod_ = @Autowired)
	private ChatService chatService;
	@Setter(onMethod_ = @Autowired)
	private GroupService groupService;
	
	// 채팅방의 목록 <방번호, 방의 유저목록>
	private Map<Integer, ArrayList<WebSocketSession>> roomList = new HashMap<>();
	
	// 세션 리스트 
	private Map<WebSocketSession, Integer> sessionList = new ConcurrentHashMap<>();

	// 즉 채팅방에 누군가가 접속 후
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		log.debug("afterConnectionEstablished({}) invoked.", session);

		// 접속한 세션 아이디
		log.info("\t+ connected sessionId : {} ", session.getId());
		
	} // afterConnectionEstablished

	// 채팅 전송시
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		log.debug("WebSocketSession({}, {}) invoked.", session, message);
		
		String msg = message.getPayload();
		
		// 받은 메시지를 객체에 저장
		// chatDTO = [room_no, user_id, chat_content, chat_date, chat_unread_count];
		ChatDTO chatDTO = objectMapper.readValue(msg, ChatDTO.class);
		log.info(chatDTO);
		
		// Calendar 타입의 변수 선언
        Calendar chat_date_calendar = Calendar.getInstance();
        
        // 메시지의 입력된 시간으로 초기화
        chat_date_calendar.setTime(chatDTO.getChat_date());
        
        // 타임스탬프의 숫자 형태로 변환(세션에 보낼 때 날짜에 공백이 찍히면 안되기 때문에)
        Long chat_date = chat_date_calendar.getTimeInMillis();
		
        
        // 채팅 세션 목록에 채팅방이 존재하지 않고, 처음 들어왔고, DB에서의 채팅방이 있을 때(이거 없애봄)
        // 채팅방 없을 때
        if(roomList.get(chatDTO.getRoom_no()) == null && chatDTO.getChat_content().equals("ENTER-CHAT")) {
            
            // 채팅방에 들어갈 session들
            ArrayList<WebSocketSession> sessionTwo = new ArrayList<>();
            // session 추가
            sessionTwo.add(session);
            // sessionList에 추가
            sessionList.put(session, chatDTO.getRoom_no());
            // RoomList에 추가
            roomList.put(chatDTO.getRoom_no(), sessionTwo);
            // 확인용
            System.out.println("채팅방 생성");
            
            List<ChatVO> list = this.chatService.getChatListInGroup(chatDTO.getRoom_no());
            List<Integer> li = new ArrayList<>();
            for(ChatVO e : list) {
            	li.add(e.getChat_unread_count());
            }
            log.info(li);
        	
            // 메세지에 이름, 내용을 담는다.
            TextMessage textMessage = new TextMessage(chatDTO.getUser_id() + "|" + chatDTO.getChat_content() + "|" + chatDTO.getChat_date() + "|" + chatDTO.getChat_unread_count() + "|" + li);

            // 해당 채팅방의 session에 뿌려준다.
            for(WebSocketSession sess : roomList.get(chatDTO.getRoom_no())) {
                sess.sendMessage(textMessage);
            } // for
            
        }
        
        // 채팅방이 존재 할 때
        else if(roomList.get(chatDTO.getRoom_no()) != null && chatDTO.getChat_content().equals("ENTER-CHAT")) {
            
        	// RoomList에서 해당 방번호를 가진 방이 있는지 확인.
        	roomList.get(chatDTO.getRoom_no()).add(session);
        	// sessionList에 추가
        	sessionList.put(session, chatDTO.getRoom_no());
        	// 확인용
        	System.out.println("채팅방 입장");
        	
            List<ChatVO> list = this.chatService.getChatListInGroup(chatDTO.getRoom_no());
            List<Integer> li = new ArrayList<>();
            
            for(ChatVO e : list) {
            	li.add(e.getChat_unread_count());
            }
            log.info(li);
        	
            // 메세지에 이름, 내용을 담는다.
            TextMessage textMessage = new TextMessage(chatDTO.getUser_id() + "|" + chatDTO.getChat_content() + "|" + chatDTO.getChat_date() + "|" + chatDTO.getChat_unread_count() + "|" + li);

            // 해당 채팅방의 session에 뿌려준다.
            for(WebSocketSession sess : roomList.get(chatDTO.getRoom_no())) {
                sess.sendMessage(textMessage);
            } // for
            
        }
        
        // 채팅 메세지 입력 시
        else if(roomList.get(chatDTO.getRoom_no()) != null && !chatDTO.getChat_content().equals("ENTER-CHAT")) {
            

            // 현재 session 수
            int sessionCount = roomList.get(chatDTO.getRoom_no()).size();
            log.info("\t + roomList : {}", roomList);
            log.info("\t + roomList : {}", roomList.get(chatDTO));
            log.info("\t + sessionCunt : {} ", sessionCount);
 
            
            // 그룹원 숫자
            int userCountInGroup = this.groupService.getUserListInGroup(chatDTO.getRoom_no()).size();
            log.info("\t + userCountInGroup : {} ", userCountInGroup);
            
            // 총 그룹원 숫자에서 현재 채팅방에 없는 그룹원을 뺀 값을 unread_count로 추가
            // 안읽은 사람수 = 총 그룹원 - 현재 채팅방에 있는 그룹원
            chatDTO.setChat_unread_count(userCountInGroup - sessionCount);
            
            log.info("\t + chat_unread_count : {} ", chatDTO.getChat_unread_count());
            
            // 메세지에 이름, 내용을 담는다.
            TextMessage textMessage = new TextMessage(chatDTO.getUser_id() + "|" + chatDTO.getChat_content() + "|" + chat_date + "|" + chatDTO.getChat_unread_count() + "|" + null);
            
            // 해당 채팅방의 session에 뿌려준다.
            for(WebSocketSession sess : roomList.get(chatDTO.getRoom_no())) {
            	sess.sendMessage(textMessage);
            } // for
            
            // DB에 저장한다.
            chatService.createChat(chatDTO);
            
            // 입력한 사용자의 마지막 읽은 채팅 번호 업데이트
    		List<ChatVO> chatList = this.chatService.getChatListInGroup(chatDTO.getRoom_no());
    		
    		Integer lastestChatNo = chatList.get(chatList.size()-1).getChat_no();
    		
			this.chatService.modifyLastReadNo(chatDTO.getRoom_no(), chatDTO.getUser_id(), lastestChatNo);

        } // if-else
        
	} // handleTextMessage

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		log.debug("afterConnectionClosed({}, {}) invoked.", session, status);

		// 세션 리스트에서 제거
		
		if(sessionList.get(session) != null) {
			roomList.get(sessionList.get(session)).remove(session);	// 방에서 세션 지움
			sessionList.remove(session);
			log.info(sessionList + "제거 성공");
		}// 세션리스트에서 세션 지움

	} // afterConnectionClosed

} // end class

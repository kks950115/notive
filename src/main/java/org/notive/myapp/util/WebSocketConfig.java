package org.notive.myapp.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@NoArgsConstructor

// Spring Bean Configuration File(XML)에서 설정하지 않고, 자바설정으로 웹소켓을 설정하는 경우에 사용
@EnableWebSocket
@Configuration
public class WebSocketConfig implements WebSocketConfigurer {
	

	@Bean("chatHandler")
	public ChatHandler chatHandler() {
		log.debug("chatHandler() invoked.");
		
		return new ChatHandler();
	} // chatHandler
	
	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		log.debug("registerWebSocketHandlers({}) invoked.", registry);
		
		registry.addHandler(chatHandler(),"/chat");
//		registry.addHandler(chatHandler(),"/chat").addInterceptors(new HttpSessionHandshakeInterceptor());

	} // registerWebSocketHandlers

} // end class

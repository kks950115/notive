package org.notive.myapp.domain;

import java.util.Date;

import lombok.NoArgsConstructor;
import lombok.Value;

@Value
public class ChatVO {
	
	private Integer room_no;
	private Integer chat_no;
	private String user_id;
	private String chat_content;
	private Date chat_date;
	private Integer chat_unread_count;
	
}

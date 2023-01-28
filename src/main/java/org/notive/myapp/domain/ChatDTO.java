package org.notive.myapp.domain;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ChatDTO {
	
	private Integer room_no;
	private String user_id;
	private String chat_content;
	private Date chat_date;
	private Integer chat_unread_count;
	
}

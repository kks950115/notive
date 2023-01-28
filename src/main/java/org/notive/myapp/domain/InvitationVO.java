package org.notive.myapp.domain;

import lombok.Value;

@Value
public class InvitationVO {

	private Integer invit_no;
	private Integer group_no;
	private String group_name;
	private String invit_id;
	private String user_id;
}

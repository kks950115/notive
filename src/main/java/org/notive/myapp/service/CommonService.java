package org.notive.myapp.service;

public interface CommonService {

	public Integer checkGroupName(String group_name);	// 그룹이름 중복 체크
	public Integer checkUserId(String user_id);			// 아이디 중복 체크
	
} // end interface

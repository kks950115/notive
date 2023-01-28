package org.notive.myapp.service;

import org.notive.myapp.mapper.CommonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

@NoArgsConstructor
@Log4j2

@Service
public class CommonServiceImpl implements CommonService {

	@Setter(onMethod_ = @Autowired)
	CommonMapper mapper;

	// 그룹이름이 존재하는지 확인
	@Override
	public Integer checkGroupName(String group_name) {
		log.debug("checkGroupName({}) invoked.", group_name);
		
		return this.mapper.selectCountValue("tbl_group", "group_name", group_name);
	} // checkGroupName

	// 아이디가 존재하는지 확인
	@Override
	public Integer checkUserId(String user_id) {
		log.debug("checkGroupName({}) invoked.", user_id);
		
		return this.mapper.selectCountValue("tbl_user", "user_id", user_id);
	}
	
} // end class

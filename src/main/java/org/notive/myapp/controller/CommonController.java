package org.notive.myapp.controller;

import org.notive.myapp.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

@Log4j2
@NoArgsConstructor

@RequestMapping("/common/")
@Controller
public class CommonController {
	
	@Setter(onMethod_ = @Autowired)
	CommonService commonService;
	
	// 그룹이름 중복체크 
	// 체크 후 결과값 반환
	@PostMapping("groupNameCheck")
	@ResponseBody
	public Integer groupNameCheck(String user_group_name) {
		log.debug("userIdCheck({}) invoked.", user_group_name);
		
		int result = this.commonService.checkGroupName(user_group_name);
		
		return result;

	} // groupNameCheck
}

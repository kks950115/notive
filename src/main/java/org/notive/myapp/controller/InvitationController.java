package org.notive.myapp.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.notive.myapp.domain.InvitationVO;
import org.notive.myapp.domain.UserGroupVO;
import org.notive.myapp.service.GroupService;
import org.notive.myapp.service.InvitationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

@Log4j2
@NoArgsConstructor

@RequestMapping("/invitation/")
@Controller
public class InvitationController {
	
	@Setter(onMethod_ = @Autowired)
	InvitationService invitationService;
	@Setter(onMethod_ = @Autowired)
	GroupService groupService;
	
	// 회원의 초대받은 그룹 목록 표시
	@ResponseBody
	@PostMapping("show")
	public List<InvitationVO> showList(@RequestParam("user_id") String user_id){
		
		List<InvitationVO> result = this.invitationService.getInvitationList(user_id);
		
		return result;
	} // show
	
	
	// 초대목록에 모든 회원을 초대
	@ResponseBody
	@PostMapping("invite")
	public String invite(@RequestBody List<Map<String, Object>> list){
		List<InvitationVO> invitationList = new ArrayList<>();
		
		for(Map<String, Object> elist : list) {
			Integer group_no = Integer.parseInt((String) elist.get("group_no"));
			String group_name = groupService.getGroupName(group_no);
			String invit_id = (String) elist.get("invit_id");
			String user_id = (String) elist.get("user_id");
			
			invitationList.add(new InvitationVO(null, group_no, group_name, invit_id, user_id));
		};
		
		log.info(invitationList);
		
		for(InvitationVO element : invitationList) {
			this.invitationService.createInvitation(element);
		}
		
		return "초대 성공!";

	} // invite
	
	@ResponseBody
	@PostMapping("accept")
	public void accept(UserGroupVO userGroup) {
		groupService.joinGroup(userGroup);
		
	}
	
	// 그룹 초대 거절
	@ResponseBody
	@PostMapping("refuse")
	public Integer refuse(@RequestParam("group_name") String group_name) {
		
		return invitationService.removeInvitation(group_name);
		 
	}

} // end class

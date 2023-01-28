package org.notive.myapp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.notive.myapp.domain.GroupVO;
import org.notive.myapp.domain.UserGroupVO;
import org.notive.myapp.service.CommonService;
import org.notive.myapp.service.GroupService;
import org.notive.myapp.service.InvitationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

@Log4j2
@NoArgsConstructor

@RequestMapping("/main/group/")
@Controller
public class GroupController {
	
	@Setter(onMethod_=@Autowired)
	GroupService groupService;
	@Setter(onMethod_=@Autowired)
	CommonService commonService;
	@Setter(onMethod_=@Autowired)
	InvitationService invitationService;
	
	
	//=========================================================================================================================//
	// Request Mapping table 설계
	//=========================================================================================================================//
	// (1) /main/group/folder			GET		String get(group)					=> 그룹폴더 화면으로 이동
	// (2) /main/group/create			POST	String create(group_name)			=> 새 그룹 
	// (4) /main/group/groupNameCheck	POST	Integer groupNameCheck(groupName)	=> 그룹생성시 그룹명 체크
	// (3) /board/modify				POST	RedirectView modify(board, rttrs)	=> new RedirectView("/board/list)
	// (5) /board/remove				POST	RedirectView remove(bno, rttrs)		=> new RedirectView("/board/list)
	// (6) /board/register				GET		void register()						=> 게시글 생성 화면으로 이동
	//=========================================================================================================================//
	
	// 그룹폴더 선택하기
	@GetMapping("folder")
	public String folder(GroupVO group) {
		log.debug("fordler({}) invoked.", group);
		log.info(group);
		
		return "/section/gfolder";
	} // folder
	
	// 그룹의 유저목록 출력
	@ResponseBody
	@GetMapping("getUserList")
	public List<String> getUserList(Integer group_no){
		log.debug("getUserList({}) invoked.", group_no);
		
		return this.groupService.getUserListInGroup(group_no);
	} // getUserList
	
	// 그룹 생성하기
	@ResponseBody
	@PostMapping("create")
	public Integer create(String user_id, String user_group_name) {
		log.debug("create({}, {}) invoked.", user_id, user_group_name);
		
		int result = this.groupService.createGroup(user_id, user_group_name);
		
		return result;

	} // create
	
	// 그룹이름 변경
	@ResponseBody
	@PostMapping("changeGroupName")
	public void changeGroupName(UserGroupVO userGroup) {
		log.debug("changeGroupName({}) invoked.", userGroup);
		
		this.groupService.modifyUserGroupName(userGroup);

	} // create
	
	// 그룹에서 나가기
	@ResponseBody
	@PostMapping("withdraw")
	public void withdraw(UserGroupVO userGroup) {
		log.debug("withdraw({}) invoked.", userGroup);
		
		this.groupService.removeUserGroup(userGroup);


	} // create
	
	// 그룹 초대 목록에 추가할 ID 검증
	@PostMapping("chkAddId")
	@ResponseBody
	public Map<String, Integer> chkAddId(@RequestBody Map<String, Object> params) {
		log.debug("chkAddId({}) invoked.", params);
		
		// 받은 데이터에서 group_no, user_id 값 추출
		Integer group_no = Integer.parseInt((String) params.get("group_no"));
		String user_id = (String)params.get("user_id");
		
		log.info("\t+ group_no : {}", group_no);
		log.info("\t+ user_id : {}", user_id);
		
		// 유효성 검사 결과 담을 Map
		Map<String, Integer> map = new HashMap<>();
		
		// 유효성 검증 결과 Map 객체에 저장
		map.put("chkId", commonService.checkUserId(user_id));	// 1일 때 검증 통과
		map.put("chkInGroup", groupService.checkUserInGroup(group_no, user_id));	// 0일 때 검증 통과
		map.put("chkInInvitation", invitationService.checkUserInvitation(group_no, user_id));	// 0일 떄 검증 통과
		
		return map;

	} // chkAddId
	
	
} // end class
